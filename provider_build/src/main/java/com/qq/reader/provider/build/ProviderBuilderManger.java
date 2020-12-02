package com.qq.reader.provider.build;

public class ProviderBuilderManger {

    private final IProviderBuilderFactory providerBuilderFactory;

    private ProviderBuilderManger(ClassLoader classLoader) {
        providerBuilderFactory = ClassLoaderUtils.newInstance(classLoader,
                ProviderBuilderConstants.BUILDER_FACTORY_IMPL_CLASS_NAME, IProviderBuilderFactory.class);
    }

    private static ProviderBuilderManger instance;

    public static ProviderBuilderManger getInstance(ClassLoader classLoader) {
        if (instance == null) {
            synchronized (ProviderBuilderManger.class) {
                if (instance == null) {
                    instance = new ProviderBuilderManger(classLoader);
                }
            }
        }
        return instance;
    }

    public IProviderBuilder getProviderBuilder(String type) {
        IProviderBuilder providerBuilder = providerBuilderFactory.getProviderBuilder(type);
        if (providerBuilder == null) {
            throw new NullPointerException("请检查该类型： " + type + " 是否标注到 IProviderBuilder 实现类中");
        }
        return providerBuilder;
    }
}
