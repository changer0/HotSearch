package com.qq.reader.provider.simple;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.bean.BaseBean;
import com.qq.reader.provider.inter.IFiller;
import com.qq.reader.provider.inter.INetQuestParams;
import com.qq.reader.provider.loader.SimpleProviderLoader;
import com.qq.reader.provider.parser.SimpleGSONParser;

/**
 * DataProvider 构造器
 */
public abstract class SimpleDataProviderCreator<Q extends BaseBean, P extends BaseBean> implements INetQuestParams, IFiller<P> {


    private final DataProvider<Q, P> provider;
    private final SimpleProviderLoader loader;

    public SimpleDataProviderCreator(Q requestBean, Class<P> responseClass) {

        loader = new SimpleProviderLoader();
        provider = new DataProvider.Builder<Q, P>()
                .setNetQuestParams(this)
                .setFiller(this)
                .setParser(new SimpleGSONParser<P>())
                .setLoader(loader)
                .build(requestBean, responseClass);
    }

    public DataProvider<Q, P> getProvider() {
        return provider;
    }

    public SimpleProviderLoader getLoader() {
        return loader;
    }
}
