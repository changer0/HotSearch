package com.yuewen.reader.bookstore;

import android.content.Context;
import android.content.Intent;

/**
 * 书城启动器
 * @author zhanglulu
 */
public class BookStoreActivityLauncher {
    public static final String BOOK_STORE_FRAGMENT_PATH  = "BOOK_STORE_FRAGMENT_PATH";
    public static final String BOOK_STORE_FRAGMENT_PARAMS  = "BOOK_STORE_FRAGMENT_PARAMS";
    public static final String BOOK_STORE_VIEW_MODEL = "BOOK_STORE_VIEW_MODEL";

    /**
     * 启动书城二级页
     * @param context 上下文
     * @param path Router 路径
     * @param launchParams 启动参数
     */
    public static void launch(Context context,String path, LaunchParams launchParams) {
        Intent intent = new Intent(context, CommonBookStoreActivity.class);
        intent.putExtra(BookStoreActivityLauncher.BOOK_STORE_FRAGMENT_PATH, path);
        intent.putExtra(BookStoreActivityLauncher.BOOK_STORE_FRAGMENT_PARAMS, launchParams);
        context.startActivity(intent);
    }


}
