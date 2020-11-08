
# ViewModel 构建 ViewBindItem 设计文档

> 通过 View - ViewModel 的构建方式，可以大大增加布局的复用性，以及业务逻辑的解耦

使用示例：

``` kotlin
class SampleViewBindItem : BaseViewBindModelItem<SampleResponseBean.Item>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style
    }

    override fun onBindViewModel(
        data: SampleResponseBean.Item?,
        viewModelMap: MutableMap<Int, IViewModel>
    ) {
        val bookList = data?.bookList!!
        viewModelMap[R.id.title] = ItemTitleViewModel(data.title)
        viewModelMap[R.id.singleBook0] = SingleBookViewModel(bookList[0].name, bookList[0].content)
        viewModelMap[R.id.singleBook1] = SingleBookViewModel(bookList[1].name, bookList[1].content)
        viewModelMap[R.id.singleBook2] = SingleBookViewModel(bookList[2].name, bookList[2].content)
    }
}
```

## 类图设计

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201108230118.png)

## 代码设计

主要添加了 BaseViewBindModelItem，通过 viewModelMap 建立 View 和 ViewModel 的关系。

``` kotlin
public abstract class BaseViewBindModelItem
        <Bean>
        extends BaseViewBindItem<Bean, BaseViewHolder> {

    private static final String TAG = "BaseViewBindModelItem";

    @NonNull
    private final Map<Integer, IViewModel> viewModelMap = new ConcurrentHashMap<>();

    /**设置数据时触发*/
    @Override
    public void setData(Bean dataBean) {
        super.setData(dataBean);
        try {
            onBindViewModel(dataBean, viewModelMap);
        } catch (Exception e) {
            Logger.e("BaseViewBindModelItem", "onBindViewModel 失败：" + e);
        }
    }

    @Override
    public boolean bindView(@NonNull BaseViewHolder holder, @NonNull Activity activity) {

        for (Map.Entry<Integer, IViewModel> viewModelEntry : viewModelMap.entrySet()) {
            View view = holder.getView(viewModelEntry.getKey());
            if (!(view instanceof IView)) {
                Logger.e(TAG, "资源文件中的 View，必须实现 IView 接口！！！");
                continue;
            }
            IViewModel value = viewModelEntry.getValue();
            if (value == null) {
                Logger.e(TAG, "当前 ViewModel 为空：" + value);
                continue;
            }
            ((IView) view).setModel(viewModelEntry.getValue());
        }
        return true;
    }

    /**初始化 Model*/
    public abstract void onBindViewModel(Bean data, @NonNull Map<Integer, IViewModel> viewModelMap);
}
```