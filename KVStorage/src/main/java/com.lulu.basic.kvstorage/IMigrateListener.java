package com.lulu.basic.kvstorage;

public interface IMigrateListener {
    /**
     * 完成
     *
     * @param spName
     */
    void onComplete(String spName);

    /**
     * 如果以迁移,再次迁移调用失败
     *
     * @param spName
     */
    void onFail(String spName);

    /**
     * 开始迁移
     *
     * @param spName
     */
    void onStart(String spName);
}
