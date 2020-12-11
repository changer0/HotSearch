package com.qq.reader.provider.page;

/**
 * ProviderGenerator 管理类，子类由注解处理器自动生成
 */
public interface IPageFactory {
    IPage getPage(String type);
}
