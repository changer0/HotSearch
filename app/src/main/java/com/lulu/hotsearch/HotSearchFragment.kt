package com.lulu.hotsearch

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.lulu.basic.skin.SkinKVStorage.getSkinId
import com.lulu.basic.skin.SkinKVStorage.setSkinId
import com.lulu.basic.skin.SkinManager
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.bean.HotSearchConfigBean
import com.lulu.basic.skin.SkinPackageBean
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.manager.HotSearchConfigManager.saveCurType
import com.lulu.basic.skin.SwitchSkinUtil
import com.lulu.hotsearch.view.HotSearchView
import com.lulu.hotsearch.view.HotSearchView.OnFabClickListener
import com.qq.reader.bookstore.BaseBookStoreFragment
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.loader.ObserverEntity

/**
 * Author: zhanglulu
 * Time: 2021/2/14
 */
@Route(path = Constant.WB_HOT_SEARCH)
class HotSearchFragment : BaseBookStoreFragment<HotSearchView, HotSearchViewModel>() {
    override fun onCreateBookStoreView(): HotSearchView {
        return HotSearchView(mContext)
    }

    override fun onCreateBookStoreViewModel(enterBundle: Bundle): Class<HotSearchViewModel> {
        return HotSearchViewModel::class.java
    }

    override fun onLaunchSuccess(
        container: View,
        enterBundle: Bundle,
        savedInstanceState: Bundle?
    ) {
        mBookStoreView.actionBarContainer.visibility = View.VISIBLE
        innerLoadData(enterBundle, false)
        mBookStoreView.setOnFabClickListener(object : OnFabClickListener {
            override fun onClick(view: View, bean: HotSearchConfigBean) {
                mEnterBundle.putString(
                    Constant.HOT_SEARCH_TYPE,
                    bean.type
                )
                innerLoadData(mEnterBundle, true)
            }
        })
        initSwitchSkinDialog()
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
        mBookStoreView.refreshActionBar(type)
        loadData(LoadSignal.LOAD_SIGNAL_INIT)
        mBookStoreView.hideFABMenu()
    }

    override fun onDataInit(entity: ObserverEntity) {
        super.onDataInit(entity)
        mBookStoreView.recyclerView.smoothScrollToPosition(0)
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
        mBookStoreView.titleRightTime.visibility = View.VISIBLE
        val timeStr =
            DateFormat.format("kk:mm:ss", bean.time_stamp.toLong())
        mBookStoreView.titleRightTime.text = getString(R.string.update_time, timeStr)
    }

    /**
     * 配置换肤弹窗 TODO 后续直接替换为页面
     */
    private fun initSwitchSkinDialog() {
        mBookStoreView.rightImage.setOnClickListener { v: View? ->

            showProgress(R.string.loading)
            //请求皮肤配置信息
            SwitchSkinUtil.requestSkinConfig(this@HotSearchFragment,object : SwitchSkinUtil.RequestListener {
                override fun onSuccess(beanList: List<SkinPackageBean>) {
                    val tempList = ArrayList(beanList)
                    val defaultBean = SkinPackageBean()
                    defaultBean.title = "科技黑(默认)"
                    defaultBean.name = "default"
                    tempList.add(0, defaultBean)
                    val names =
                        arrayOfNulls<String>(tempList.size)
                    var checkItem = 0
                    for (i in tempList.indices) {
                        val packageBean = tempList[i]
                        names[i] = packageBean.title
                        if (TextUtils.equals(getSkinId(), packageBean.name)) {
                            checkItem = i
                        }
                    }
                    hideProgress()
                    showSwitchSkinDialog(names, checkItem, tempList)
                }

                override fun onFailure(e: Throwable) {
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
            if (TextUtils.equals(bean.name, "default")) {
                SkinManager.get().restoreDefaultTheme()
            } else {
                showProgress(R.string.loading)
                SkinManager.get().tryDownloadAndInstall(bean) {
                    hideProgress()
                }
            }
            setSkinId(bean.name)
            dialog.cancel()
        }.setNegativeButton(R.string.cancel, null).create().show()
    }

}