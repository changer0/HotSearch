package com.example.providermoduledemo.sample;

import com.qq.reader.zebra.parser.IParser;
import com.qq.reader.zebra.utils.GSONUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglulu
 * @date : 2020/12/14 11:02 AM
 */
public class SampleConvertParser implements IParser<SampleResultBean> {
    @Override
    public SampleResultBean parseData(String jsonStr, Class<SampleResultBean> clazz) {
        SampleConvertResponseBean responseBean = GSONUtil.parseJsonWithGSON(jsonStr, SampleConvertResponseBean.class);
        return convert(responseBean);
    }
    public SampleResultBean convert(SampleConvertResponseBean responseBean) {
        SampleResultBean sampleResultBean = new SampleResultBean();
        if (responseBean == null) {
            return sampleResultBean;
        }
        sampleResultBean.setTime(responseBean.getTime());
        List<SampleConvertResponseBean.Item> responseItemList = responseBean.getList();
        if (responseItemList == null) {
            return sampleResultBean;
        }
        List<SampleResultBean.Item> resultItemList = new ArrayList<>();
        for (SampleConvertResponseBean.Item responseItem : responseItemList) {
            SampleResultBean.Item resultItem = new SampleResultBean.Item();
            resultItem.setStyle(responseItem.getStyle());
            resultItem.setTitle(responseItem.getTitle());
            List<SampleConvertResponseBean.Item.Element> responseBookList = responseItem.getBookList();
            if (responseBookList != null) {
                List<SampleResultBean.Item.Element> resultBookList = new ArrayList<>();
                for (SampleConvertResponseBean.Item.Element responseBook : responseBookList) {
                    SampleResultBean.Item.Element resultBook = new SampleResultBean.Item.Element();
                    resultBook.setLeftImgUrl(responseBook.getImageUrl());
                    resultBook.setRightText(responseBook.getText());
                    resultBookList.add(resultBook);
                }
                resultItem.setBookList(resultBookList);
            }
            resultItemList.add(resultItem);
        }
        sampleResultBean.setList(resultItemList);
        return sampleResultBean;
    }
}
