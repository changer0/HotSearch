package com.qq.reader.provider.simple;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.inter.IFiller;
import com.qq.reader.provider.inter.INetQuestParams;
import com.qq.reader.provider.loader.SimpleProviderLoader;
import com.qq.reader.provider.parser.SimpleGSONParser;

/**
 * DataProvider 构造器
 */
public abstract class SimpleDataProviderCreator<P> implements INetQuestParams, IFiller<P> {
    private final DataProvider<P> provider;
    private final SimpleProviderLoader<P> loader;

    public SimpleDataProviderCreator(Class<P> responseClass) {

        loader = new SimpleProviderLoader();
        provider = new DataProvider.Builder<P>()
                .setNetQuestParams(this)
                .setFiller(this)
                .setParser(new SimpleGSONParser<P>())
                .setLoader(loader)
                .build(responseClass);
    }

    public DataProvider<P> getProvider() {
        return provider;
    }

    public SimpleProviderLoader<P> getLoader() {
        return loader;
    }
}
