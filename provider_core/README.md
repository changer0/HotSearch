# 新版书城加载框架 DataProvider


## 特点

- 强制使用 GSON 进行数据解析
- 强制使用 RecyclerView 进行列表加载
- 支持缓存 GET、POST 请求，以及多种缓存模式
- 支持两个协议并发请求（RxJava）
- 支持使用 LiveData 实现数据回调

## 类图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201021092504.png)

## 时序图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201021100524.png)

## 核心类介绍

**DataProviderConfig**

> DataProvider 配置类，需要在 Application 中进行配置！

- 配置 Provider 所需的 Application
- 配置 Provider 所需的网络请求接口（Provider 本身并不支持网络请求，需要使用方自行提供接口）

**BaseDataProvider**

> 数据加载中间层

能力:

- 构建请求 URL、请求体以及请求类型等基本信息
- 通过 GSON 进行数据解析
- 填充列表数据
- 返回协议中的时间戳，用于检查数据的有效性（即缓存的实现）

 **BaseViewBindItem**

> View 绑定层

职责:

- View 和 Data 绑定
- UI 相关逻辑处理，点击事件等

**DataProviderLoader**

> 数据加载类

- 加载 DataProvider
- 支持多 DataProvider 并发请求

## 使用方式

### 初始化配置

进行初始化配置，DataProvider 本身并不提供网络请求能力，需要使用方自行提供

``` kotlin
class MyApp: Application() {
    private val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build()
    override fun onCreate() {
        super.onCreate()
        DataProviderConfig.init(this, true) { params ->
            val request = Request.Builder()
                .url(params.url)
                .get().build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body()?.byteStream()
        }
    }
}
```
### 实现 BaseDataProvider

- 添加一个继承自 BaseDataProvider 的类，指定请求 Bean 和响应 Bean 泛型
- 重写 composeUrl() 用于拼接协议地址
- 重写 fillData() 用于构建 ViewBindItem，添加到 mViewBindItems 中

NormalListDataProvider.kt

``` kotlin
class NormalListDataProvider(requestBean: NormalRequestDataBean)
    : BaseDataProvider<NormalRequestDataBean, NormalResponseDataBean>(requestBean, NormalResponseDataBean::class.java) {
    override fun fillData() {
        //填充数据
        mViewBindItems = ViewBindItemBuilder.buildViewBindItem(mData)
    }

    override fun composeUrl(p0: NormalRequestDataBean?): String {
        //拼接 URL
        return "https://gitee.com/luluzhang/publish-json/raw/master/list_json.json"
    }

    override fun getExpiredTime(): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }
}
```
ViewBindItemBuilder.kt

``` kotlin
object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: NormalResponseDataBean): List<BaseViewBindItem<out BaseDataBean, BaseViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<NormalResponseDataBean.ItemData, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<NormalResponseDataBean.ItemData, BaseViewHolder>? = null
            when (item.style) {
                0 -> {
                    bindViewItem = ViewBindItemStyle0()
                }
                1 -> {
                    bindViewItem = ViewBindItemStyle1()
                }
            }
            bindViewItem?.let {
                it.data = item
                viewBindItemList.add(it)
            }
        }
        return viewBindItemList
    }
}
```

### 实现 BaseViewBindItem

- 根据需求添加继承自 BaseViewBindItem 的类，指定绑定当前 View 的数据 Bean 泛型和 RecyclerView.Holder 泛型
- 重写 getResLayoutId() 方法，返回当前 Item 的布局文件
- 重写 attachView() 方法，用于将 Data 绑定到 View

ViewBindItemStyle0.kt
``` kotlin
class ViewBindItemStyle0 : BaseViewBindItem<ListResponseDataBean.ListData, BaseViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_data_item_style0
    }

    override fun bindView(holder: BaseViewHolder, activity: Activity): Boolean {
        val text1 = holder.getView<TextView>(R.id.text1)
        text1.text = "${mItemData.name}  ${mItemData.content}"
        text1.setOnClickListener {
            Toast.makeText(activity, "当前 Activity : ${activity.javaClass.simpleName}", Toast.LENGTH_SHORT).show()
        }
        val text2 = holder.getView<TextView>(R.id.text2)
        text2.text = "ViewBindItemStyle0"
        return true
    }
}
```

### 加载 DataProvider

- 使用 DataProviderLoader 加载 Provider
- 实现自己想要的 RecyclerView.Adapter，并调用 ViewBindItem.attachView 方法进行 View 绑定

ListActivity.kt

``` kotlin
class NormalListActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: NormalListDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = NormalListDataProvider(NormalRequestDataBean())
        provider.liveData.observe(this, Observer {
            if (it.isSuccess) {
                val dataItems = it.provider.dataItems
                if (mRecyclerViewState == STATE_ENTER_INIT) {
                    mAdapter.setNewData(dataItems)
                    hideLoadingView()
                } else if (mRecyclerViewState == STATE_UP_LOAD_MORE) {
                    mAdapter.addData(dataItems)
                }
                mAdapter.loadMoreComplete()
            } else {
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
        DataProviderLoader.getInstance().loadData(provider)
    }

    override fun onLoadMoreRequested() {
        super.onLoadMoreRequested()
        DataProviderLoader.getInstance().loadData(provider)
    }
}
```

## 缓存模式说明

CacheMode 共分为四种类型：

|缓存类型|说明|
|--|--|
|CACHE_MODE_NOT_USE_CACHE|不使用缓存|
|CACHE_MODE_USE_CACHE_WHEN_NET_ERROR|只有当网络数据失败时使用缓存|
|CACHE_MODE_USE_CACHE_PRIORITY|优先使用缓存: 本地有缓存且未过期则用缓存 -> 过期则使用网络数据 -> 网络失败使用过期数据|
|CACHE_MODE_USE_CACHE_NOT_EXPIRED|使用缓存，但不使用过期数据|

## 混淆配置

``` java
-keep class * extends com.qq.reader.provider.bean.BaseBean {*;}
```


## 其他注意事项

- 编写数据 Bean 类的内部类务必也要继承 BaseDataBean