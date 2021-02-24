package com.lulu.hotsearch

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.alibaba.android.arouter.facade.annotation.Route
import com.lulu.basic.net.CoroutineScopeManager
import com.lulu.basic.skin.SkinKVStorage.getSkinId
import com.lulu.basic.skin.SkinKVStorage.setSkinId
import com.lulu.basic.skin.SkinManager.Companion.get
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.bean.HotSearchConfigBean
import com.lulu.hotsearch.bean.SkinPackageBean
import com.lulu.hotsearch.db.DBManager
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.manager.HotSearchConfigManager.saveCurType
import com.lulu.hotsearch.utils.SwitchSkinUtil
import com.lulu.hotsearch.utils.SwitchSkinUtil.requestSkinConfig
import com.lulu.hotsearch.view.HotSearchView
import com.lulu.hotsearch.view.HotSearchView.OnFabClickListener
import com.qq.reader.bookstore.BaseBookStoreFragment
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.loader.ObserverEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        configSwitchSkinDialog()
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
    private fun configSwitchSkinDialog() {
        mBookStoreView.rightImage.setOnClickListener { v: View? ->

          CoroutineScopeManager.getScope(this).launch {
              DBManager.get().skinPackageDao().getById("1234").observe(this@HotSearchFragment,
                  Observer<SkinPackageBean> {
                      if (it != null) {
                          ToastUtil.showShortToast("name: ${it.name} id: ${it.id}")
                      } else {
                          ToastUtil.showShortToast("空")
                      }

                  })
              val skinPackage = SkinPackageBean()
              skinPackage.id = "1234"
              skinPackage.name = "这是名字"
              DBManager.get().skinPackageDao().insertSkinPackageSuspend(skinPackage)
          }

//            requestSkinConfig(this@HotSearchFragment,object : SwitchSkinUtil.LoadListener {
//                override fun invoke(beanList: List<SkinPackageBean>?) {
//                    if (beanList == null) {
//                        return
//                    }
//                    val tempList = ArrayList(beanList)
//                    val defaultBean = SkinPackageBean()
//                    defaultBean.name = "默认"
//                    defaultBean.id = "default"
//                    tempList.add(0, defaultBean)
//                    val names =
//                        arrayOfNulls<String>(tempList.size)
//                    var checkItem = 0
//                    for (i in tempList.indices) {
//                        val packageBean = tempList[i]
//                        names[i] = packageBean.name
//                        if (TextUtils.equals(getSkinId(), packageBean.id)) {
//                            checkItem = i
//                        }
//                    }
//                    showSwitchSkinDialog(names, checkItem, tempList)
//                }
//
//            })
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
                get().restoreDefaultTheme()
            } else {
                get().tryDownloadAndInstall(bean.id, bean.downloadUrl)
            }
            setSkinId(bean.id)
            dialog.cancel()
        }.setNegativeButton(R.string.cancel, null).create().show()
    }

}