//package com.example.providermoduledemo.build;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.providermoduledemo.PageLoadSignal;
//import com.example.providermoduledemo.sample.SampleCommonSecondPageFragment;
//import com.example.providermoduledemo.sample.SampleConvertParser;
//import com.example.providermoduledemo.sample.SampleGetExpiredTime;
//import com.example.providermoduledemo.sample.SampleResultBean;
//import com.example.providermoduledemo.sample.SampleViewBindItemBuilder;
//import com.qq.reader.provider.DataProvider;
//import com.qq.reader.provider.ProviderLiveData;
//import com.qq.reader.provider.cache.CacheMode;
//import com.yuewen.zebra.building.IFragmentParam;
//import com.yuewen.zebra.building.IPage;
///**
// * 男生 Provider 构建类 （举例说明）
// */
//public class BoyPage implements IPage {
//    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";
//    @Override
//    public ProviderLiveData loadPageData(Bundle params) {
//        int index = params.getInt(PageBuilderParams.PAGE_INDEX);
//        if (index <= 0) index = 1;
//        String url = String.format(SERVER_URL, index);
//        return DataProvider.with(SampleResultBean.class)
//                .url(url)
//                .parser(new SampleConvertParser())
//                .viewBindItemBuilder(new SampleViewBindItemBuilder())
//                .cacheConfig(CacheMode.CACHE_MODE_NOT_USE_CACHE, new SampleGetExpiredTime())
//               .load(params.getString(PageLoadSignal.LOAD_STATE));
//    }
//
//    @Override
//    public IFragmentParam getFragmentParam() {
//        return new FragmentParam.Builder().setEnableLoadMore(false)
//                .setEnablePullDownRefresh(false)
//                .setTitleName("男生页面")
//                .setStartIndex(1)
//                .build();
//    }
//
//    @Override
//    public Class<? extends Fragment> getFragment() {
//        return SampleCommonSecondPageFragment.class;
//    }
//}
