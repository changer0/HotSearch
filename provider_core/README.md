# 新版书城加载框架 DataProvider


## 特点

- 强制使用 GSON 进行数据解析
- 强制使用 RecyclerView 进行列表加载
- 支持缓存 GET、POST 请求，以及多种缓存模式
- 支持使用 LiveData 实现数据回调
- 采用链式调用方式方便使用

请求示例：

``` kotlin
DataProvider.with(SampleResponseBean::class.java)
    .url(url).get()
    .viewBindItemBuilder(SampleViewBindItemBuilder())
    .cacheConfig(cacheMode, SampleGetExpiredTime())
    .load()
    .observe(this, this)
```

## 类图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201107212401.png)

## 时序图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201107214219.png)

## 核心类

**DataProviderConfig**

> DataProvider 配置类，需要在 Application 中进行配置！

- 配置 Provider 所需的 Application
- 配置 Provider 所需的网络请求接口（Provider 本身并不支持网络请求，需要用户自行提供接口）
- 配置 Provider 缓存路径

**DataProvider**

> 数据加载中间层

能力:

- 构建请求信息
- 数据加载（网络请求、数据缓存等）
- 响应解析
- 构建 ViewBindItem

 **BaseViewBindItem**

> View 绑定层

职责:

- View 和 Data 绑定
- UI 相关逻辑处理，点击事件等

## 开放接口说明

**ILoader**

用于加载 Provider 的开放接口，用户可通过实现该接口自由实现请求逻辑。目前框架内默认提供支持缓存的 SimpleProviderLoader

**IParser**

数据解析器，用于将 json 数据转为 bean 的工具。目前框架内默认提供 Gson 解析器

**IViewBindItemBuilder**

ViewBindItem 构建器，用于业务层构建 ViewBindItem，需要用户自行实现


## 使用方式

### 1. 初始化配置

进行初始化配置，DataProvider 本身并不提供网络请求能力，需要用户自行提供，需在 Application 中进行初始化：

``` kotlin
class MyApp: Application() {
    private val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build()
    override fun onCreate() {
        super.onCreate()
        initDataProvider()
    }

    /**
     * DataProvider 初始化
     */
    private fun initDataProvider() {
        val builder = DataProviderConfig.Builder(this) { params ->
            val request = Request.Builder().url(params.url).get().build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body()?.byteStream()
        }
        builder.setDebug(true)
        DataProviderConfig.init(builder)
    }
}
```

### 2. 实现 BaseViewBindItem

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

### 3. 构建 DataProvider 请求数据

通过 DataProvider 链式调用配置所需参数，示例如下：

``` kotlin

class SampleActivity : ReaderBaseListProviderActivity(), Observer<ObserverEntity> {
    
    //...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadInitData()
    }

    private fun loadData(index: Int) {
        val url = String.format(SERVER_URL, index)
        Log.d(TAG, "loadData: url: $url")

        DataProvider.with(SampleResponseBean::class.java)
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

**方法说明：**

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