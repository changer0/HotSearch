# Zebra 组件使用文档 

## 1. 初始化配置

进行初始化配置，Zebra 本身并不提供网络请求能力，需要用户自行提供，**需在 Application 中进行初始化**：

``` kotlin
class MyApp: Application() {
    private val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build()
    override fun onCreate() {
        super.onCreate()
        initZebra()
    }

    /**
     * Zebra 初始化
     */
    private fun initZebra() {
        val builder = ZebraConfig.Builder(this) { params ->
            val request = Request.Builder().url(params.url).get().build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body()?.byteStream()
        }
        builder.setDebug(true)
        ZebraConfig.init(builder)
    }
}
```

## 2. 实现 BaseViewBindItem

- 根据需求添加继承自 BaseViewBindItem 的类，指定绑定当前 View 的数据 Bean 泛型和 RecyclerView.Holder 泛型
- 重写 getResLayoutId() 方法，返回当前 Item 的布局文件
- 重写 bindView() 方法，用于将 Data 绑定到 View

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
            //...
        }
        val text2 = holder.getView<TextView>(R.id.text2)
        text2.text = "ViewBindItemStyle0"
        return true
    }
}
```

## 3. 构建 Zebra 请求数据

通过 Zebra 链式调用配置所需参数，示例如下：

``` kotlin

class SampleActivity : ReaderBaseListProviderActivity(), Observer<ObserverEntity> {
    
    //...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData(1)
    }

    private fun loadData(index: Int) {
        val url = String.format(SERVER_URL, index)
        Log.d(TAG, "loadData: url: $url")

        Zebra.with(SampleResponseBean::class.java)
            .url(url)
            .viewBindItemBuilder(SampleViewBindItemBuilder())
            .cacheConfig(cacheMode, SampleGetExpiredTime())
            .load()
            .observe(this, this)
    }

    // Observer 的回调数据
    override fun onChanged(entity: ObserverEntity) {
        if (entity.isSuccess) {
            val dataItems = entity.provider.viewBindItems
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
    }
}

```

SampleViewBindItemBuilder

``` kotlin
class SampleViewBindItemBuilder : IViewBindItemBuilder<SampleResponseBean> {
    override fun buildViewBindItem(data: SampleResponseBean): 
                        List<BaseViewBindItem<*, out RecyclerView.ViewHolder>> {

        val viewBindItemList = mutableListOf<BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder>>()

        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder>? = null
            when (item.style) {
                0 -> bindViewItem = ViewModelBindItemStyle0()
                1 -> bindViewItem = ViewModelBindItemStyle1()
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

## 方法说明

|方法|说明|是否必须|
|--|--|--|
|with|指定响应类型（首先调用）|是|
|url|协议地址|是|
|get|使用 GET 请求（默认请求方式）|否|
|post|使用 POST 请求，需传入请求类型、请求内容|否|
|needGzip|是否 gzip 压缩（默认 false）|否|
|loader|指定数据加载器|否|
|parser|指定数据解析器|否|
|viewBindItemBuilder|指定 ViewBindItem 构建器|是|
|cacheConfig|缓存配置，包括缓存类型、数据过期时间|否|
|load|加载数据|是|
|observe|数据监听，需传入 LifecycleOwner 与 Observer|是|

## 缓存模式说明

框架提供的 loader 提供四种缓存类型：

|缓存类型|说明|
|--|--|
|CACHE_MODE_NOT_USE_CACHE|不使用缓存|
|CACHE_MODE_USE_CACHE_WHEN_NET_ERROR|只有当网络数据失败时使用缓存|
|CACHE_MODE_USE_CACHE_PRIORITY|优先使用缓存: 本地有缓存且未过期则用缓存 -> 过期则使用网络数据 -> 网络失败使用过期数据|
|CACHE_MODE_USE_CACHE_NOT_EXPIRED|使用缓存，但不使用过期数据|

## 其他注意事项

- 编写数据 Bean 时需要将其避免混淆！