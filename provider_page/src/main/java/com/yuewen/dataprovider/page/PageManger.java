package com.yuewen.dataprovider.page;


/**
 * 获取 PageManger
 */
public class PageManger {

    private static IPageFactory pageFactory;

    /**
     * 获取 Page
     * @param type
     * @return
     */
    public static IPage getPage(String type) {
        if (pageFactory == null) {
            pageFactory = ReflectUtils.getPageFactory();
        }
        if (pageFactory == null) {
            throw new NullPointerException("检查传入的 type 是否已经声明或检查注解配置是否正确!!");
        }
        return pageFactory.getPage(type);
    }

}
