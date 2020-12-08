package com.qq.reader.provider.build;

public class PageBuilderManger {

    private final IPageBuilderFactory providerBuilderFactory;

    private PageBuilderManger(ClassLoader classLoader) {
        providerBuilderFactory = ClassLoaderUtils.newInstance(classLoader,
                ProviderBuilderConstants.BUILDER_FACTORY_IMPL_CLASS_NAME, IPageBuilderFactory.class);
    }

    private static PageBuilderManger instance;

    public static PageBuilderManger getInstance(ClassLoader classLoader) {
        if (instance == null) {
            synchronized (PageBuilderManger.class) {
                if (instance == null) {
                    instance = new PageBuilderManger(classLoader);
                }
            }
        }
        return instance;
    }

    public IPageBuilder getPageBuilder(String type) {
        IPageBuilder providerBuilder = providerBuilderFactory.getPageBuilder(type);
        if (providerBuilder == null) {
            throw new NullPointerException("请检查该类型： " + type + " 是否标注到 IPageBuilder 实现类中");
        }
        return providerBuilder;
    }
}
