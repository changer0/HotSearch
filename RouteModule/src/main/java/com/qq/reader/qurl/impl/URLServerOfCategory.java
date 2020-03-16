package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfCategory extends URLServer {
    private final String SERVER_ACTION_INDEX = "index";
    private final String SERVER_ACTION_LIST = "list";

    public URLServerOfCategory(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_INDEX);
        matchServerActionPool.add(SERVER_ACTION_LIST);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_INDEX:
                goStackTab();
                return true;
            case SERVER_ACTION_LIST:
                //uniteqqreader://nativepage/category/list?actionId=123&actionTag=name
                goCategoryList();
                return true;
        }
        return false;

    }

    public void goCategoryList() {
        if (getParameterMap() != null) {
            String actionId = getParameterMap().get("actionId");
            String actionTag = getParameterMap().get("actionTag");
            String fromTitle = getParameterMap().get("title");

            //通过免费、包月中搜索，进入二三级分类
//            String searchFrom = getParameterMap().get("searchfrom");
//            if (searchFrom != null) {
//                //影视专区
//                if (searchFrom.equalsIgnoreCase("film")) {
//                    JumpActivityUtil.goNewClassifyPage(getBindActivity(),
//                            null,
//                            Utility.getStringById(R.string.title_movie_book_stack),
//                            null);
//                    new ClickEvent.Builder(PageNames.PAGE_MORE_AREA)
//                            .setDataType(DataTypes.VIEW_BOOKSTORE).build().commit();
//                } else {
//                    JumpActivityUtil.goClassify_Detail_FROM_SEARCH(getBindActivity(),
//                            actionTag,
//                            actionId,
//                            getJumpActivityParameter(),
//                            searchFrom);
//                }
//            } else {
//                JumpActivityUtil.goNewClassifyPage(getBindActivity()
//                        , actionTag, actionId, fromTitle, getJumpActivityParameter());
//            }

            JumpActivityUtil.goNewClassifyPage(getBindActivity(),
                    actionTag,
                    actionId,
                    fromTitle,
                    getJumpActivityParameter());
        }
    }

    public void goStackTab() {
        if (getParameterMap() != null) {
            JumpActivityUtil.goBook_Stacks(getBindActivity(), getJumpActivityParameter());
        }
    }


}
