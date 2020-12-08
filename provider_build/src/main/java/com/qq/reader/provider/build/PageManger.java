package com.qq.reader.provider.build;

public class PageManger {

    private final IPageFactory providerBuilderFactory;

    private PageManger(ClassLoader classLoader) {
        providerBuilderFactory = ClassLoaderUtils.newInstance(classLoader,
                ProviderBuilderConstants.BUILDER_FACTORY_IMPL_CLASS_NAME, IPageFactory.class);
    }

    private static PageManger instance;

    public static PageManger getInstance(ClassLoader classLoader) {
        if (instance == null) {
            synchronized (PageManger.class) {
                if (instance == null) {
                    instance = new PageManger(classLoader);
                }
            }
        }
        return instance;
    }

    public IPage getPageBuilder(String type) {
        IPage providerBuilder = providerBuilderFactory.getPage(type);
        if (providerBuilder == null) {
            throw new NullPointerException("请检查该类型： " + type + " 是否标注到 IPageBuilder 实现类中");
        }
        return providerBuilder;
    }
}
