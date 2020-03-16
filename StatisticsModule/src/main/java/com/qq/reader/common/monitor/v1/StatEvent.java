package com.qq.reader.common.monitor.v1;

import android.text.TextUtils;

import com.qq.reader.common.gsonbean.BaseBean;
import com.qq.reader.common.monitor.RDM;
import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.qq.reader.core.readertask.tasks.ReaderShortTask;
import com.tencent.mars.xlog.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liuxiaoyang on 2017/10/18.
 * 新版上报事件
 */

public class StatEvent {

    // 上传字段Key
    private static final String KEY_PREFERENCE = "pre";
    private static final String KEY_PAGENAME = "pn";
    private static final String KEY_PAGE_DATA_ID = "pdid";
    private static final String KEY_PAGE_DATA_TYPE = "pdt";
    private static final String KEY_COLUMN = "cl";
    private static final String KEY_DATA_ID = "did";
    private static final String KEY_DATA_TYPE = "dtype";
    private static final String KEY_DATA_CARD_POSITION = "cpos";
    private static final String KEY_DATA_POSITION = "pos";
    private static final String KEY_DATA_ALGROITHM = "alg";
    private static final String KEY_DATA_ORIGIN = "origin";
    // 用于区分数据是否是同一个栏目
    private static final String KEY_COL_DIS = "dis";

    public static class PageInfo extends BaseBean {

        public PageInfo(String name){
            this.name = name;
        }

        public PageInfo(String name,String dataId){
            this.name = name;
            this.dataId = dataId;
        }

        private String name;
        private String dataId;
        private String colId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getColId() {
            return colId;
        }

        public void setColId(String colId) {
            this.colId = colId;
        }

        @Override
        public String toString() {
            return "PageInfo{" +
                    "name='" + name + '\'' +
                    ", dataId='" + dataId + '\'' +
                    '}';
        }
    }

    /**
     * 方便上传数据结构
     */
    public static class DataInfo extends BaseBean {

        public void setType(String type) {
            this.type = type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        private String type;
        private String id;
        private String alg;
        private int position;
    }

    public static class Builder {

        public Builder(String _eventName, String _pageName) {
            this.eventName = _eventName;
            this.pageName = _pageName;
            extraMap = new HashMap<String, String>();
        }

        public Builder(String _eventName, PageInfo pageInfo) {
            this.eventName = _eventName;
            if (pageInfo != null) {
                this.pageName = pageInfo.name;
                this.pageDataID = pageInfo.dataId;
                this.colId = pageInfo.colId;
            }
            extraMap = new HashMap<String, String>();
        }

        public StatEvent build() {
            return new StatEvent(this);
        }

        public Builder setColId(String colId) {
            this.colId = colId;
            return this;
        }

        public Builder setDataInfo(DataInfo dataInfo) {
            setDataID(dataInfo.id);
            setDataType(dataInfo.type);
            setDataPosition(dataInfo.position);
            setDataAlg(dataInfo.alg);
            return this;
        }

        /**
         * @param extraMap 重复key后来覆盖之前的
         * @return
         */
        public Builder setExtraMap(Map<String, String> extraMap) {
            if (extraMap != null) {
                this.extraMap.putAll(extraMap);
            }
            return this;
        }


        /**
         * 自定义参数或后续增加的
         *
         * @param key   重复key后来覆盖之前的
         * @param value value
         * @return Builder
         */
        public Builder addExtra(String key, String value) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                this.extraMap.put(key, value);
            }
            return this;
        }

        /**
         * 额外参数1 固定key ext1
         *
         * @param value value
         * @return Builder
         */
        public Builder setExtra1(String value) {
            if (!TextUtils.isEmpty(value)) {
                this.extraMap.put("ext1", value);
            }
            return this;
        }

        /**
         * 额外参数2 固定key ext2
         *
         * @param value value
         * @return Builder
         */
        public Builder setExtra2(String value) {
            if (!TextUtils.isEmpty(value)) {
                this.extraMap.put("ext2", value);
            }
            return this;
        }

        public Builder setDataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setPageDataType(String pageDataType) {
            this.pageDataType = pageDataType;
            return this;
        }

        /**
         * 页面数据ID，例如书籍详情传书籍bid
         *
         * @param pageDataID 数据ID
         * @return Builder
         */
        public Builder setPageDataID(String pageDataID) {
            this.pageDataID = pageDataID;
            return this;
        }

        /**
         * 点击元素/曝光元素 数据ID NullAble
         *
         * @param dataID 数据ID
         * @return Builder
         */
        public Builder setDataID(String dataID) {
            this.dataID = dataID;
            return this;
        }

        /**
         * 用于区分栏目字段
         *
         * @param colDis 建议传入时间戳 ？
         */
        public Builder setColDis(long colDis) {
            this.colDis = colDis;
            return this;
        }

        public Builder setDataAlg(String dataAlg) {
            this.dataAlg = dataAlg;
            return this;
        }

        public Builder setDataOrigin(String origin) {
            this.dataOrigin = origin;
            return this;
        }

        public Builder setDataPosition(int dataPosition) {
            this.dataPosition = dataPosition;
            return this;
        }
        public Builder setDataCardPosition(int dataCardPosition) {
            this.dataCardPosition = dataCardPosition;
            return this;
        }

        /**
         * 此 id 不用于上报，仅仅用于方便开发找到对应埋点的位置
         */
        public Builder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
        String eventName;
        String pageName;
        String pageDataID;
        String pageDataType;
        String colId;
        String dataType;
        String dataID;
        int dataPosition = -1;
        int dataCardPosition = -1;
        String dataAlg;
        String dataOrigin;
        long colDis;
        Map<String, String> extraMap;
        String eventId;
    }

    public void commit() {
        commit(true, -1, -1);
    }

    /**
     * @param isOk   事件是否成功
     * @param size   事件网络消耗
     * @param elapse 事件耗时
     */
    public void commit(boolean isOk, long elapse, long size) {
        commit(isOk, elapse, size, false, false);
    }

    /**
     * @param isOk         事件是否成功
     * @param size         事件网络消耗
     * @param elapse       事件耗时
     * @param needCheckNet 是否需要检测当前有网络
     *                     ，无网络的话不记录此次事件
     * @param isRealTime   是否需要实时上报
     */
    public void commit(final boolean isOk, final long elapse,final long size
            ,final boolean needCheckNet,final boolean isRealTime) {
        if (!TextUtils.isEmpty(eventName)) {
            Log.d("StatEvent", toString());
            ReaderTaskHandler.getInstance().addTask(new ReaderShortTask() {
                @Override
                public void run() {
                    super.run();
                    //登录票据刷新
                    RDM.onUserAction(eventName, isOk, elapse, size, extraMap
                            , needCheckNet, isRealTime);
                }
            });
        } else {
            Log.e("StatEvent", "commit eventName null ");
        }
    }

    protected StatEvent(Builder builder) {
        eventName = builder.eventName;
        extraMap = builder.extraMap;
        eventId = builder.eventId;
        //用户偏好 1男 2女 3出版
        extraMap.put(KEY_PREFERENCE, String.valueOf(CommonConfig
                .getWebUserLike()));
        extraMap.put(KEY_PAGENAME, builder.pageName);
        checkAndAdd(KEY_PAGE_DATA_ID, builder.pageDataID, extraMap);
        checkAndAdd(KEY_PAGE_DATA_TYPE, builder.pageDataType, extraMap);
        checkAndAdd(KEY_COLUMN, builder.colId, extraMap);
        checkAndAdd(KEY_DATA_ID, builder.dataID, extraMap);
        checkAndAdd(KEY_DATA_TYPE, builder.dataType, extraMap);
//        Log.d("StatEvent","builder.dataPosition " + builder.dataPosition);
        if (builder.dataPosition >= 0) {
            extraMap.put(KEY_DATA_POSITION, String.valueOf(builder.dataPosition));
        }
        if (builder.dataCardPosition >= 0) {
            extraMap.put(KEY_DATA_CARD_POSITION, String.valueOf(builder.dataCardPosition));
        }
        checkAndAdd(KEY_DATA_ALGROITHM, builder.dataAlg, extraMap);
        checkAndAdd(KEY_DATA_ORIGIN, builder.dataOrigin, extraMap);
        if (builder.colDis > 0) {
            extraMap.put(KEY_COL_DIS, String.valueOf(builder.colDis));
        }
    }

    private void checkAndAdd(String key, String value, Map map) {
        if (!TextUtils.isEmpty(value)) {
            map.put(key, value);
        }
    }

    String eventName;
    Map<String, String> extraMap;
    String eventId;


    @Override
    public String toString() {

        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(eventId)) {
            sb.append("eventId=").append(eventId).append("&");
        }
        sb.append("eventName=").append(eventName);
        for (Iterator iterator = extraMap.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append("&");
            sb.append(entry.getKey().toString()).append("=")
                    .append(null == entry.getValue() ? "" :
                            entry.getValue().toString());
        }
        return sb.toString();
    }
}
