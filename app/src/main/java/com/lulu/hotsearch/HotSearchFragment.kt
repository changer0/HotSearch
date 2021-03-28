package com.lulu.hotsearch

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.lulu.baseutil.Init
import com.lulu.basic.skin.*
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.bean.HotSearchConfigBean
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.manager.HotSearchConfigManager.saveCurType
import com.lulu.hotsearch.view.HotSearchView
import com.lulu.hotsearch.view.HotSearchView.OnFabClickListener
import com.qq.reader.bookstore.BasePageFragment
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.loader.ObserverEntity
import java.io.File

/**
 * Author: zhanglulu
 * Time: 2021/2/14
 */
@Route(path = Constant.WB_HOT_SEARCH)
class HotSearchFragment : BasePageFragment<HotSearchView, HotSearchViewModel>() {
    override fun onCreatePageView(): HotSearchView {
        return HotSearchView(mContext)
    }

    override fun onCreatePageViewModel(enterBundle: Bundle): Class<HotSearchViewModel> {
        return HotSearchViewModel::class.java
    }

    override fun onLaunchSuccess(
        container: View,
        enterBundle: Bundle,
        savedInstanceState: Bundle?
    ) {
        mPageView.actionBarContainer.visibility = View.VISIBLE
        innerLoadData(enterBundle, false)
        mPageView.setOnFabClickListener(object : OnFabClickListener {
            override fun onClick(view: View, bean: HotSearchConfigBean.HotSearchListBean) {
                mEnterBundle.putString(
                    Constant.HOT_SEARCH_TYPE,
                    bean.type
                )
                innerLoadData(mEnterBundle, true)
            }
        })
        initSwitchSkinDialog()

        //加载重试
        mPageView.dataErrorView.setOnClickListener {
            innerLoadData(mEnterBundle, true)
        }

        PluginTest.testPlugin(mPageView.rightImage, activity)
    }

    private fun innerLoadData(enterBundle: Bundle, showProgress: Boolean) {
        if (showProgress) {
            showProgress(R.string.loading)
        }
        val type = enterBundle.getString(
            Constant.HOT_SEARCH_TYPE,
            Constant.HOT_SEARCH_WB
        )
        saveCurType(type)
        mPageView.refreshActionBar(type)
        loadData(LoadSignal.LOAD_SIGNAL_INIT)
        mPageView.hideFABMenu()
    }

    override fun onDataInit(entity: ObserverEntity) {
        super.onDataInit(entity)
        mPageView.recyclerView.smoothScrollToPosition(0)
        configUpdateTime(entity.zebra.getData())
        hideProgress()
    }

    /**
     * 配置更新时间
     * @param bean
     */
    private fun configUpdateTime(bean: HotSearchBean?) {
        if (bean == null) {
            return
        }
        //添加更新时间
        mPageView.titleRightTime.visibility = View.VISIBLE
        val timeStr =
            DateFormat.format("kk:mm:ss", bean.time_stamp.toLong())
        mPageView.titleRightTime.text = getString(R.string.update_time, timeStr)
    }

    /**
     * 配置换肤弹窗 TODO 后续直接替换为页面
     */
    private fun initSwitchSkinDialog() {
        mPageView.rightImage.setOnClickListener { v: View? ->

            showProgress(R.string.loading)
            //请求皮肤配置信息
            SwitchSkinUtil.requestSkinConfig(this@HotSearchFragment,object : SwitchSkinUtil.RequestListener {
                override fun onSuccess(beanList: List<SkinPackageBean>) {
                    val tempList = ArrayList(beanList)
                    val defaultBean = SkinPackageBean()
                    defaultBean.name = "科技黑(默认)"
                    defaultBean.id = "default"
                    tempList.add(0, defaultBean)
                    val names =
                        arrayOfNulls<String>(tempList.size)
                    var checkItem = 0
                    for (i in tempList.indices) {
                        val packageBean = tempList[i]
                        if (packageBean.isUpdate) {
                            names[i] = "${packageBean.name}(有更新)"
                        } else {
                            names[i] = packageBean.name
                        }
                        if (TextUtils.equals(SkinKVStorage.getSkinId(), packageBean.id)) {
                            checkItem = i
                        }

                    }
                    hideProgress()
                    showSwitchSkinDialog(names, checkItem, tempList)
                }

                override fun onFailure(e: Throwable) {
                    hideProgress()
                    ToastUtil.showShortToast(resources.getString(R.string.net_error))
                }
            })
        }
    }

    private fun showSwitchSkinDialog(
        names: Array<String?>,
        checkItem: Int,
        packageBeans: List<SkinPackageBean>
    ) {
        AlertDialog.Builder(mContext).setSingleChoiceItems(
            names,
            checkItem
        ) { dialog: DialogInterface, which: Int ->
            val bean = packageBeans[which]
            if (TextUtils.equals(bean.id, "default")) {
                SkinKVStorage.setSkinId("default")
                SkinManager.get().restoreDefaultTheme()
            } else {
                showProgress(R.string.loading)
                SkinManager.get().tryDownloadAndInstall(bean, object : ISkinSwitchListener {
                    override fun onSuccess() {
                        SkinKVStorage.setSkinId(bean.id)
                        hideProgress()
                    }

                    override fun onFailure(e: Throwable) {
                        hideProgress()
                    }
                })
            }
            dialog.cancel()
        }.setNegativeButton(R.string.cancel, null).create().show()
    }
}