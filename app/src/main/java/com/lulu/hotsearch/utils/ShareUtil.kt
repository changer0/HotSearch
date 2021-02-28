package com.lulu.hotsearch.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import com.lulu.baseutil.Init
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.share.GoShareBuilder
import com.lulu.hotsearch.share.ShareContentType
import com.lulu.hotsearch.share.ShareImageLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


/**
 * 分享工具
 * https://www.jb51.net/article/112057.htm
 */
object ShareUtil {

    fun showShareSelectDialog(context: Context, url: String, view: View) {
        val items = arrayOf("分享文字链接", "分享图片")
        AlertDialog.Builder(context)
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> {
                            shareToText(context, url)
                        }
                        1 -> {
                            shareToImage(view)
                        }
                    }
                }
                .create().show()
    }


    fun shareToText(context: Context, url: String) {
        GoShareBuilder(context)
                .setText(url)
                .setContentType(ShareContentType.TEXT)
                .goShare()
    }

    fun shareToImage(view: View) {
        GlobalScope.launch {
           val path = withContext(Dispatchers.IO) { obtainShareBitmapPath(view) }
            if (path == null) {
                ToastUtil.showShortToast("屏幕截取失败, 请重启应用重试!!!")
                return@launch
            }
            GoShareBuilder(view.context)
                    .setContentType(ShareContentType.IMAGE)
                    .setFileUri(Uri.parse(path))
                    .goShare()
        }
    }

    /**
     * 获取分享图片的地址
     */
    private fun obtainShareBitmapPath(view: View):String? {
        //https://www.jianshu.com/p/d0ef41470586
        val originBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(originBitmap)
        view.draw(canvas)

        val shareContainerView = ShareImageLayout(view.context)
        shareContainerView.setImageByBitmap(originBitmap)
        val destBitmap = shareContainerView.createImage()
        var fos: FileOutputStream? = null
        try {
            val fileName = "${Init.ROOT_CACHE_PATH}share${File.separator}${System.currentTimeMillis()}.jpg"
            val file = File(fileName)

            if (!file.parentFile.exists()) {
                if (!file.parentFile.mkdirs()) {
                    return null
                }
            }
            fos = FileOutputStream(file)
            destBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
            return file.path
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.close()
            originBitmap?.recycle()
            destBitmap?.recycle()
        }
        return null
    }
}