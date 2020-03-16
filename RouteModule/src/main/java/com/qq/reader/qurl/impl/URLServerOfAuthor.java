package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.dispatch.IEventListener;
import com.qq.reader.dispatch.R;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;
import com.qq.reader.utils.Utility;

import java.util.List;
import java.util.Map;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfAuthor extends URLServer implements IEventListener {

    private final String SERVER_ACTION_INDEX = "index";
    private final String SERVER_ACTION_AUTHOR_PRODUCT = "product";
    private final String SERVER_ACTION_AUTHOR_MAINPAGE = "mainpage";
    private static final String SERVER_ACTION_AUTHOR_ALL_BOOKS = "allbooks";

    private static final String KEY_AUTHOR_NAME = "name";
    private static final String KEY_AUTHOR_ID = "authorId";
    private static final String KEY_AUTHOR_AVATAR_URL = "iconUrl";
    
    //根据本书的bid找到该作者
    private static final String KEY_AUTHOR_BID = "bid";
    

    public URLServerOfAuthor(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_INDEX);
        matchServerActionPool.add(SERVER_ACTION_AUTHOR_PRODUCT);
        matchServerActionPool.add(SERVER_ACTION_AUTHOR_MAINPAGE);
        matchServerActionPool.add(SERVER_ACTION_AUTHOR_ALL_BOOKS);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_INDEX:
                goHallOfFame();
                return true;
            case SERVER_ACTION_AUTHOR_PRODUCT:
                goAuthorProduct();
                return true;
            case SERVER_ACTION_AUTHOR_MAINPAGE:
                goAuthorMainPage();
                return true;
            case SERVER_ACTION_AUTHOR_ALL_BOOKS:
                goAuthorAllBooks();
                return true;
        }
        return false;
    }

    private void goAuthorProduct() {
        if (FlavorUtils.isCommonUI()) {
            Map<String, String> map = getParameterMap();
            String lName = map.get(KEY_AUTHOR_NAME);
            String bid = map.get(KEY_AUTHOR_BID);
            String authorId = map.get(KEY_AUTHOR_ID);
            JumpActivityUtil.goAuthorAllBooksInCategory(getFromActivity(),authorId, lName);
        } else if (FlavorUtils.isOPPO()) {
            Map<String, String> map = getParameterMap();
            String lName = map.get(KEY_AUTHOR_NAME);
            JumpActivityUtil.goNormalAuthProduct(getBindActivity(), lName, 12, getJumpActivityParameter());
        }
    }


    public void goHallOfFame() {
        JumpActivityUtil.goHallOfFame(getBindActivity(),
                null, 0, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void goAuthorMainPage() {
        Map<String, String> map = getParameterMap();
        String authorId = map.get(KEY_AUTHOR_ID);
        String authorName = map.get(KEY_AUTHOR_NAME);
        String iconUrl = map.get(KEY_AUTHOR_AVATAR_URL);
        JumpActivityUtil.goAuthorMainPage(getBindActivity(), authorId, authorName, iconUrl, getJumpActivityParameter());
    }


    /**
     * OPPO 305 新增qurl 
     * 跳转到该作者全部作品页面
     */
    public void goAuthorAllBooks() {
        Map<String, String> map = getParameterMap();
        String authorId = map.get(KEY_AUTHOR_ID);
        JumpActivityUtil.goAuthorAllBooksInCategory(getFromActivity(), authorId, Utility.getStringById(R.string.all_write_book));
    }

    @Override
    public void doFunction(Bundle b) {
        
    }

    @Override
    public Activity getFromActivity() {
        return getBindActivity();
    }
}
