package com.lulu.basic.skin

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import com.lulu.baseutil.Init
import com.lulu.basic.utils.ToastUtil
import com.lulu.component.download.DownloadManager
import com.lulu.component.download.SimpleDownloadListener
import com.lulu.skin.ISkinUpdateListener
import com.lulu.skin.SkinEngine
import com.lulu.skin.SkinUtil
import java.io.File


/**
 * 换肤管理类
 */
private const val TAG = "SkinManager"
public class SkinManager {
    companion object {
        @JvmStatic
        public fun get(): SkinManager {
            return Holder.instance
        }

        @JvmStatic
        public val SKIN_PATH = Init.ROOT_PATH + "skin/"
    }

    object Holder {
        val instance = SkinManager()
    }

    public fun addSkinUpdateListener(listener: ISkinUpdateListener) {
        SkinEngine.get().addSkinUpdateListener(listener)
    }

    public fun removeSkinUpdateListener(listener: ISkinUpdateListener) {
        SkinEngine.get().removeSkinUpdateListener(listener)
    }

    /**
     * 皮肤初始化
     */
    public fun init() {
        val skinPath = SkinKVStorage.getSkinPath()
        if (!TextUtils.isEmpty(skinPath)) {
            SkinEngine.get().loadSkin(Init.context, skinPath!!)
        }
    }

    /**
     * 切换皮肤
     */
    public fun switchSkin(skinPath: String, finished: (() -> Unit)? = null) {
        if (TextUtils.isEmpty(skinPath)) {
            return
        }
        SkinEngine.get().loadSkin(Init.context, skinPath) {
            SkinKVStorage.setSkinPath(skinPath)
            finished?.invoke()
        }
    }

    /**
     * 恢复默认主题
     */
    public fun restoreDefaultTheme() {
        SkinKVStorage.setSkinPath(null)
        SkinEngine.get().restoreDefaultTheme()
    }

    public fun isExternalSkin(): Boolean {
        return SkinEngine.get().isExternalSkin
    }

    public fun getColor(resName: String): Int {
        return SkinEngine.get()
            .getColor(Init.context, resName, SkinUtil.getSysResId(Init.context, resName, "color"))
    }

    public fun getDrawable(resName: String): Drawable? {
        return SkinEngine.get().getDrawable(
            Init.context,
            resName,
            SkinUtil.getSysResId(Init.context, resName, "drawable")
        )
    }

    public fun getColorStateList(resName: String): ColorStateList? {
        return SkinEngine.get().getColorStateList(
            Init.context,
            resName,
            SkinUtil.getSysResId(Init.context, resName, "color")
        )
    }

    /**
     * 尝试下载并安装
     */
    public fun tryDownloadAndInstall(skinPackageBean: SkinPackageBean, finished: (() -> Unit)? = null) {
        val path = SKIN_PATH + "${skinPackageBean.id}.apk"
        val file = File(path)
        if (file.exists()) {
            if (skinPackageBean.isUpdate) {
                file.delete()
            } else {
                switchSkin(path) {
                    skinPackageBean.isUpdate = false
                    SwitchSkinUtil.updateSkinPackageBean(skinPackageBean)
                    finished?.invoke()
                }
                return
            }
        }
        DownloadManager.get(Init.context).add(skinPackageBean.downloadUrl, path, true, object : SimpleDownloadListener() {
            override fun onFailed(id: Int, msg: String?) {
                ToastUtil.showShortToast("下载失败: $msg")
            }

            override fun onSuccess(id: Int, averageSpeed: String) {
                switchSkin(path) {
                    //将本地升级标识重置
                    if (skinPackageBean.isUpdate) {
                        skinPackageBean.isUpdate = false
                        SwitchSkinUtil.updateSkinPackageBean(skinPackageBean)
                    }
                    finished?.invoke()
                }
            }

            override fun onProgressChanged(
                id: Int,
                offset: Long,
                totalLength: Long,
                speed: String?
            ) {
                super.onProgressChanged(id, offset, totalLength, speed)
                Log.d(TAG, "onProgressChanged() called with: id = $id, offset = $offset, totalLength = $totalLength, speed = $speed")
            }
        })
    }


    //----------------------------------------------------------------------------------------------
    // 皮肤测试代码
    private val testSkinFilePath = SKIN_PATH + "skin_purple.apk"

    /**
     * 下载测试
     */
    public fun testDownload() {

        val file = File(testSkinFilePath)
        if (file.exists()) {
            file.delete()
        }
        DownloadManager.get(Init.context).add(
            "https://gitee.com/luluzhang/HotSearchConfigProject/raw/master/skin/skin_purple.apk",
            testSkinFilePath, true, object : SimpleDownloadListener() {
                override fun onFailed(id: Int, msg: String?) {
                    ToastUtil.showShortToast("下载失败: $msg")
                }

                override fun onSuccess(id: Int, averageSpeed: String) {
                    ToastUtil.showShortToast("下载成功")
                }
            }
        )
    }

    /**
     * 测试安装皮肤包
     */
    public fun testInstall() {
        if (isExternalSkin()) {
            restoreDefaultTheme()
        } else {
            switchSkin(testSkinFilePath)
        }
    }

}