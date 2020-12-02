package com.qq.reader.provider.build;

/**
 * ProviderGenerator 管理类，子类由注解处理器自动生成
 */
public interface IProviderBuilderFactory {
    IProviderBuilder getProviderBuilder(String type);
}
