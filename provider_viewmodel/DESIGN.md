
## 方案一

> 使用 List 添加方式

分支：**feature/refactor**

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201029092452.png)

BaseViewBindModelItem.kt

```
/**
 * @author zhanglulu on 2020/10/23.
 * for 支持使用 IModel IView 形式构建 ViewBindItem
 */
public abstract class BaseViewBindModelItem
        <Bean extends BaseDataBean, Holder extends RecyclerView.ViewHolder>
        extends BaseViewBindItem<Bean, Holder>{

    private List<VM> mViewModelList;

    @Override
    public boolean bindView(@NonNull Holder holder, @NonNull Activity activity) throws Exception {
        try {
            if (mViewModelList == null) {
                mViewModelList = new ArrayList<>();
                buildViewModelList(holder, activity, mViewModelList);
            }
            for (VM vm : mViewModelList) {
                if (vm.view == null || vm.mode == null) {
                    Logger.e("BaseViewBindModelItem", "bindView 异常 vm.view == null || vm.mode == null");
                    continue;
                }
                vm.view.setModel(vm.mode);
            }
        } catch (Exception e) {
            Logger.e("BaseViewBindModelItem", "bindView 异常，请定位：" + this.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**构建 ViewModel 集合*/
    public abstract void buildViewModelList(@NonNull Holder holder, @NonNull Activity activity, @NonNull List<VM> viewModelList);

    /**描述 View Model 对应关系*/
    public static class VM {
        private final IView view;
        private final IModel mode;
        public VM(IView view, IModel mode) {
            this.view = view;
            this.mode = mode;
        }
    }
}

```

ViewModelBindItemStyle0.kt 使用：

```
class ViewModelBindItemStyle0 : BaseViewBindModelItem<ViewModelResponseDataBean.ItemData, BaseViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    override fun buildViewModelList(
        holder: BaseViewHolder,
        activity: Activity,
        viewModelList: MutableList<VM>
    ) {
        for (book in mItemData.bookList!!.withIndex()) {
            val view = holder.getView<SingleBookView>(Utils.getResIdByString("singleBook${book.index}", R.id::class.java))
            val model = SingleBookModel(book.value.name, book.value.content)
            viewModelList.add(VM(view, model))
        }
        //添加标题的 VM
        viewModelList.add(VM(holder.getView<ItemTitleView>(R.id.title), ItemTitleModel(mItemData.title)))
    }
}
```

缺点：使用起来相对麻烦些，而且不直观

## 方案二

> 通过 AnnotationProcessor （注解处理器）和 JavaPoet 实现 View-Model 的绑定

分支：**feature/refactor2**

**方案类图设计：**

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201029093258.png)

**模块依赖关系**

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201029093848.png)

**使用示例**

```
class ViewModelBindItemStyle0 : BaseViewBindModelItem<ViewModelResponseDataBean.ItemData>() {

    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    @BindView(R.id.singleBook0)
    public lateinit var model0: SingleBookModel

    @BindView(R.id.singleBook1)
    public lateinit var model1: SingleBookModel


    @BindView(R.id.singleBook2)
    public lateinit var model2: SingleBookModel

    @BindView(R.id.title)
    public lateinit var titleModel: ItemTitleModel

    override fun onInitModel(data: ViewModelResponseDataBean.ItemData?) {
        val bookList = data?.bookList!!
        titleModel = ItemTitleModel(data.title)
        //忽略容错等细节逻辑
        model0 = SingleBookModel(bookList[0].name, bookList[0].content)
        model1 = SingleBookModel(bookList[1].name, bookList[1].content)
        model2 = SingleBookModel(bookList[2].name, bookList[2].content)
    }
}
```


优点：
1. 对比方案一，使用起来方便，绑定关系直观
2. 使用注解处理器和 JavaPoet 进行编译时处理注解对运行时性能基本无影响

缺点：

1. Google 打算将资源 id 改成非 final的，这就会导致不可使用注解绑定资源 Id 的方式

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/5349f93331ba2d0a5f94eea7c3dc543.png)

（可以改为传入字符串，不过。。。有些丑陋，跟各位老铁商量下）

2. 由于只拿到布局 Id，所以就会直接导致需要当前的基类（BaseViewBindModelItem）需要可以通过 Holder 拿到对应的 View，由于此时的基类还是泛型，所以这就使得我们必须将 BaseViewBindModelItem 放在上层（即 app 层），由使用方实现（不过影响也不会太大，可以当做是对书城框架的二次开发）


【后续补充】

时间有限，后面将会补充对应详细的使用文档、设计文档。以及优化注解绑定的容错逻辑处理（例如，将注解绑定在了不该绑定的位置，需要作出相应的提醒以及报错）

【附：Demo 地址】

Provider Demo：http://gitlab.inner.yuewen.local/zhanglulu/ProviderModuleDemo

Provider 设计文档：http://gitlab.inner.yuewen.local/zhanglulu/ProviderModuleDemo/-/tree/feature/refactor2/provider_core

注解处理器学习 Demo：https://github.com/changer0/AnnotationProcessorDemo

【附：参考文章】

https://blog.csdn.net/qq_15827013/article/details/103722462

https://juejin.im/post/6844903456629587976