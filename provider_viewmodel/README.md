## 支持 View-Model 的书城架构 使用文档

> 以实现一个左图右文的 ViewBindItem 为例

## 实现步骤

### 1. 编写 ViewModel

添加一个 LIRTViewModel 类，实现 IViewModel 接口，添加 View 所需数据结构：

```kotlin
/*** 左图右文 ViewMode*/
class LIRTViewModel(public var leftImgUrl: String? = null, public var rightText: String? = null) : IViewModel
```

### 2. 编写 View

添加一个继承自 View（或其子类）的 LIRTView 类，实现 IView 接口，并指定 LIRTViewModel 泛型，实现 setModel 方法：

```kotlin
/** 左图右文 View*/
class LIRTView : ConstraintLayout, IView<LIRTViewModel>{
    constructor(context: Context) : super(context) {init(context)}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {init(context) }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle ) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_model_single_book, this, true)
    }

    override fun setModel(model: LIRTViewModel) {
        rightText.text = "${model.rightText}"
        Glide.with(context).load(model.leftImgUrl).into(leftImg)
        leftImg.setOnClickListener {Toast.makeText(context, model.leftImgUrl, Toast.LENGTH_SHORT).show()}
    }
}
```

### 3. 编写 ViewBindItem

添加一个 ViewBindItemLIRTGroupStyle0 类，继承自 BaseViewBindModelItem，并制定数据类型泛型，重写 onBindViewModel 方法，绑定 ViewModel：

```kotlin

/*** 左图右文 组合样式 0*/
class ViewBindItemLIRTGroupStyle0 : BaseViewBindModelItem<SampleResponseBean.Item>() {

    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    override fun onBindViewModel(
        data: SampleResponseBean.Item?,
        viewModelMap: MutableMap<Int, IViewModel>
    ) {
        val bookList = data?.bookList!!
        viewModelMap[R.id.title] = TitleViewModel(data.title)
        val size = bookList.size
        if (size < 1) {
            return
        }
        viewModelMap[R.id.singleBook0] = LIRTViewModel(bookList[0].leftImgUrl, bookList[0].rightText)
        if (size < 2) {
            return
        }
        viewModelMap[R.id.singleBook1] = LIRTViewModel(bookList[1].leftImgUrl, bookList[1].rightText)
        if (size < 3) {
            return
        }
        viewModelMap[R.id.singleBook2] = LIRTViewModel(bookList[2].leftImgUrl, bookList[2].rightText)
    }
}
```

### 4. 编写 ViewBindItemBuilder

添加一个 SampleViewBindItemBuilder 实现 IViewBindItemBuilder 接口，并指定数据类型泛型，实现 buildViewBindItem 方法，组装 ViewBindItem 集合：

```kotlin
/*** ViewBindItemBuilder 构建示例*/
class SampleViewBindItemBuilder : IViewBindItemBuilder<SampleResponseBean> {
    
    override fun buildViewBindItem(data: SampleResponseBean) : List<BaseViewBindItem<*, out RecyclerView.ViewHolder>> {

        val viewBindItemList = mutableListOf<BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder>>()

        for (dataItem in data.list!!) {
            var bindViewItem : BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder>? = null
            when (dataItem.style) {
                0 -> bindViewItem = ViewBindItemLIRTGroupStyle0()
                //根据组合样式不同，继续添加
            }
            bindViewItem?.let {
                it.data = dataItem
                viewBindItemList.add(it)
            }
        }
        return viewBindItemList
    }
}
```

### 5. 通过 DataPrvider 实现请求

```kotlin
DataProvider.with(SampleResponseBean::class.java)
    .url(url)
    .viewBindItemBuilder(SampleViewBindItemBuilder())
    .cacheConfig(cacheMode, SampleGetExpiredTime())
    .load()
    .observe(this, this)
```
DataProvider 使用细节请参考：[provider_core 使用文档](../provider_core/README.md)

## 效果图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201112211149.png)