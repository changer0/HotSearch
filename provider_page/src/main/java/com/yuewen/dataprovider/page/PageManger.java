package com.yuewen.dataprovider.page;

import com.qq.reader.provider.page.PageBuilderConstants;

public class PageManger {

    private final IPageFactory providerBuilderFactory;

    private PageManger(ClassLoader classLoader) {
        providerBuilderFactory = ClassLoaderUtils.newInstance(classLoader,
                PageBuilderConstants.BUILDER_FACTORY_IMPL_CLASS_NAME, IPageFactory.class);
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

    public IPage getPage(String type) {
        IPage page = providerBuilderFactory.getPage(type);
        if (page == null) {
            throw new NullPointerException("请检查该类型： " + type + " 是否标注到 IPage 的实现类中");
        }
        return page;
    }
}
