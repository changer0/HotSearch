package com.example.providermoduledemo.sample;

import java.util.ArrayList;
import java.util.List;

import converter.IConverter;

public class SampleConverter implements IConverter<SampleResultBean, SampleConvertResponseBean> {
    @Override
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
