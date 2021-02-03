# Zebra 组件设计文档 

## 特点

- 强制使用 GSON 进行数据解析
- 强制使用 RecyclerView 进行列表加载
- 支持缓存 GET、POST 请求，以及多种缓存模式
- 支持使用 LiveData 实现数据回调
- 采用链式调用方式方便使用

请求示例：

``` kotlin
Zebra.with(SampleResponseBean::class.java)
    .url(url).get()
    .viewBindItemBuilder(SampleViewBindItemBuilder())
    .cacheConfig(cacheMode, SampleGetExpiredTime())
    .load()
    .observe(this, this)
```

## 类图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20210203114658.png)
## 时序图

![](https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20201107214219.png)

## 核心类

**ZebraConfig**

> Zebra 配置类，需要在 Application 中进行配置！

- 配置 Zebra 所需的 Application
- 配置 Zebra 所需的网络请求接口（Zebra 本身并不支持网络请求，需要用户自行提供接口）
- 配置 Zebra 缓存路径

**Zebra**

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

用于加载 Zebra 的开放接口，用户可通过实现该接口自由实现请求逻辑。目前框架内默认提供支持缓存的 SimpleLoader

**IParser**

数据解析器，用于将 json 数据转为 bean 的工具。目前框架内默认提供 Gson 解析器

**IViewBindItemBuilder**

ViewBindItem 构建器，用于业务层构建 ViewBindItem，需要用户自行实现
