package com.qq.reader.adv;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;

import com.qq.reader.common.json.GsonUtil;
import com.qq.reader.common.utils.CommonConstant;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.qq.reader.core.readertask.tasks.ReaderDownloadTask;

import org.json.JSONException;
import org.json.JSONObject;

import static com.qq.reader.adv.Advertisement.AdvExtinfo.ADVEXTINFO_SHOW_TIME;

public class Advertisement {
    // private final String PLUGIN_IMAGE_PATH = Constant.ADV_PATH;

    // 102444
    // public static final String TYPE_SHOW_ON_FEEDHEAD = "102422";// 精选界面广告
    // <option value="1">单书文字</option> <option value="2">单书数据</option> <option
    // * value="3">分类标签</option> <option value="4">搜索</option> <option
    // * value="5">通栏广告</option>
    public static final int ADVEXTINFO_UITYPE_ONEBOOK = 1;
    public static final int ADVEXTINFO_UITYPE_DATABOOK = 2;
    public static final int ADVEXTINFO_UITYPE_CATE = 3;
    public static final int ADVEXTINFO_UITYPE_SEARCH = 4;
    public static final int ADVEXTINFO_UITYPE_ONEPIC = 5;

    private long mAdvId;
    /**
     * 广告类型
     */
    private String mAdvType;
    /**
     * 过期时间
     */
    private long mAdvOutofDate;

    /**
     * 开始时间
     */
    private long mAdvStartDate;

    /**
     * 广告状态 0:disable;1:enable
     */
    private int mAdvState = 1;// 0 disable;1 enable
    private String mAdvCategory;// 栏目
    private String mAdvTitle;
    private String mAdvDescription;
    private String mCount;
    private String mAdvLinkUrl;
    private String mAdvJason;// 在线阅读的json
    private String mAdvImageUrl;
    private boolean showFlag;
    private boolean mIsHardCover;// 是否是精排图书广告

    private String mVersion;// 5、0加入版本号
    private String mAdvExtInfo = "";// 广告其他信息
    private AdvExtinfo mAdvExtInfoObj;
    private int mAdvValueType; // 广告子类型
    private long mLastShowTime; // 广告上次展示时间

    private JumpAction mBindAction;// 跳转信息
    private Bundle mJumpBundle;// 跳转信息

//    private String mQurl = null;

    public Advertisement(long advId, String advType) {
        mAdvId = advId;
        mAdvType = advType;
    }

//    public void setQurl(String qurl) {
//        mQurl = qurl;
//    }
//
//    public String getQurl() {
//        return mQurl;
//    }

    // public Advertisement setIsDownloadImage(boolean isDownloadImage) {
    // mIsDownloadImage = isDownloadImage;
    // return this;
    // }

    public void setShowFlag(boolean flag) {
        showFlag = flag;
    }

    public boolean getShowFlag() {
        return showFlag;
    }

    public String getAdvCategory() {
        return mAdvCategory;
    }

    public Advertisement setAdvCategory(String advCategory) {
        this.mAdvCategory = advCategory;
        return this;
    }

    // public String getAdvCount() {
    // return mCount;
    // }

    public Advertisement setAdvCount(String count) {
        this.mCount = count;
        // 5.0暂时改动，去掉count
        setVersion(count);
        return this;
    }

    public String getAdvCount() {
        return mCount;
    }

    public String getAdvJason() {
        return mAdvJason;
    }

    public Advertisement setAdvJason(String advJason) {
        this.mAdvJason = advJason;
        return this;
    }

    public long getAdvId() {
        return mAdvId;
    }

    public Advertisement setAdvId(long advId) {
        mAdvId = advId;
        return this;
    }

    public String getTextColor() {
        if(mAdvExtInfoObj!=null){
            return mAdvExtInfoObj.color;
        }

        return "";
    }

    public String getAdvTitle() {
        return mAdvTitle;
    }

    public Advertisement setAdvTitle(String advTitle) {
        mAdvTitle = advTitle;
        return this;
    }

    public String getAdvType() {
        return mAdvType;
    }

    public String getImageURI() {
        return mAdvImageUrl;
    }

    public Advertisement setAdvImageUrl(String advImageUrl) {
        mAdvImageUrl = advImageUrl;
        return this;
    }

    public String getAdvLinkUrl() {
        return mAdvLinkUrl;
    }

    public Advertisement setAdvLinkUrl(String advLinkUrl) {
        if (advLinkUrl != null && advLinkUrl.length() > 0) {
            mAdvLinkUrl = advLinkUrl;
        }
        return this;
    }

    public String getAdvDescription() {
        return mAdvDescription;
    }

    public Advertisement setAdvDescription(String mAdvDescription) {
        this.mAdvDescription = mAdvDescription;
        return this;
    }

    public long getAdvOutofDate() {
        return mAdvOutofDate;
    }

    public Advertisement setAdvOutofDate(long advOutofDate) {
        if (advOutofDate < 0) {
            mAdvOutofDate = 0;
        } else {
            mAdvOutofDate = advOutofDate;
        }
        return this;
    }

    public long getAdvStartOfDate() {
        return mAdvStartDate;
    }

    public Advertisement setAdvStartOfDate(long advStartOfDate) {
        if (advStartOfDate < 0) {
            mAdvStartDate = 0;
        } else {
            mAdvStartDate = advStartOfDate;
        }
        return this;
    }

    public int getAdvState() {
        return mAdvState;
    }

    public Advertisement setAdvState(int mAdvState) {
        this.mAdvState = mAdvState;
        return this;
    }

    public String getAdvExtInfo() {
        if (mAdvExtInfo != null) {
            return mAdvExtInfo;
        }
        return "";
    }

    public void setAdvExtInfo(String mAdvExtInfo) {
        this.mAdvExtInfo = mAdvExtInfo;
        // 刷新数据体
        if (mAdvExtInfoObj == null) {
            mAdvExtInfoObj = new AdvExtinfo();
        }
        mAdvExtInfoObj.parseExtInfo(mAdvExtInfo);
    }


    public AdvExtinfo getAdvExtInfoObj(){
        return mAdvExtInfoObj;
    }


    @Override
    public boolean equals(Object o) {
        try {
            return this.mAdvId == ((Advertisement) o).mAdvId;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO zdy 注释掉ImageLoader的缓存图片方法
//    @Override
    public String getImagePath() {
        return mAdvImageUrl;
//        return DisCacheDispatch.getImageFilePath(DisCacheDispatch.TYPE_OF_ADV,
//                mAdvImageUrl, null);
    }

    public synchronized void downloadImageWithUrl(final Context context) {
        ReaderDownloadTask downloadTask = new ReaderDownloadTask(context,
                getImagePath(), mAdvImageUrl);
        ReaderTaskHandler.getInstance().addTask(downloadTask);
    }

    public boolean isHardCover() {
        return mIsHardCover;
    }

    public void setHardCover(boolean hardCover) {
        this.mIsHardCover = hardCover;
    }

    /**
     * <option value="1">单书文字</option> <option value="2">单书数据</option> <option
     * value="3">分类标签</option> <option value="4">搜索</option> <option
     * value="5">通栏广告</option>
     *
     * @return
     */
    public int getAdvExtInfocategory() {

        return mAdvExtInfoObj.category;
    }

    public long getAdvExtInfoNumber() {
        return mAdvExtInfoObj.number;
    }

    public boolean getAdvExtInfoJumpNeedLogin() {
        if (mAdvExtInfoObj != null) {
            return  mAdvExtInfoObj.mJumpNeedLogin;
        } else {
            return false;
        }
    }
    /**
     * PopNativeDialog type
     */
    public int getAdvExtInfoWebPopDialogType() {
        if (getAdvExtInfoWebPopDialogGravity() == Gravity.BOTTOM){
            return CommonConstant.TYPE_NATIVE;
        }else if (getAdvValueType() == 13){
            //链接类型为13表示是h5资源包
            return CommonConstant.TYPE_OFFLINEPACKAGE;
        }else {
            return CommonConstant.TYPE_WEB;
        }
        
    }
    /**
     * PopNativeDialog gravity
     */
    public int getAdvExtInfoWebPopDialogGravity() {
        if (mAdvExtInfoObj != null) {
            switch (mAdvExtInfoObj.mPopNativeDialogGravity){
                case 0:
                    return Gravity.BOTTOM;
                case 1:
                    return Gravity.CENTER;
                default:
                    return Gravity.BOTTOM;
            }
        }else {
            return Gravity.BOTTOM;
        }
    }
    
    public String getAdvExtInfoNumberDesc() {
        return mAdvExtInfoObj.number_desc;
    }

    public int getAdvExtInfoShowLimit(){
        return mAdvExtInfoObj.show_limit;
    }


    public String getAdvDisplayLable() {
        return mAdvExtInfoObj.displayLabel;
    }
    public int getAdvDisplayInterval() {
        return mAdvExtInfoObj.mDisplayInterval;
    }
    // 点击跳转action
    public JumpAction getAction() {
        if (mBindAction == null) {
            mBindAction = new JumpAction();
            Bundle b = mBindAction.getActionParams();

            // b.putString(Constant.LOCAL_STORE_IN_TITLE, mTitle);
            b.putString("KEY_ACTION", mAdvExtInfoObj.actionType);
            b.putString("KEY_ACTIONTAG", mAdvExtInfoObj.actionTag);
            b.putString("KEY_ACTIONID", mAdvExtInfoObj.actionID + "");
            b.putLong("URL_BUILD_PERE_BOOK_ID", mAdvExtInfoObj.actionID);
            b.putString("com.qq.reader.WebContent", getAdvLinkUrl());
//			b.putString(NativeAction.KEY_PRE_URL_TYPE, NativeAction
//					.getPreUrlTypeWithActionType(mAdvExtInfoObj.actionType));
//            b.putString(NativeAction.KEY_JUMP_PAGENAME, NativeAction.getJumpPageNameWithServerAction(mAdvExtInfoObj.actionType));
        }

        return mBindAction;

        // // 以下三个为特殊参数
        // b.putString(Constant.WEBCONTENT, mUrl);// webpage
        // // 类型时候需要，其他类型只需要actionId
        // b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, mActiontag);// actionTag 只在
        // // category（分类列表）的时候有用
        // b.putString(NativeAction.KEY_PRE_URL_TYPE,
        // NativeAction.PRE_URL_TYPE_LISTDISPATCH);// 在 category（分类列表）的时候有用
        //
        // b.putLong(NativeAction.URL_BUILD_PERE_BOOK_ID, mActionid);// detail 类型时候
        // actionId
        // // 就是bookId
        // setStatisic(json, b);
    }

    public Bundle getJumpBundle(){
        if (mJumpBundle == null) {
            mJumpBundle = new Bundle();

            // b.putString(Constant.LOCAL_STORE_IN_TITLE, mTitle);
            mJumpBundle.putString("KEY_ACTION", mAdvExtInfoObj.actionType);
            mJumpBundle.putString("KEY_ACTIONTAG", mAdvExtInfoObj.actionTag);
            mJumpBundle.putString("KEY_ACTIONID", mAdvExtInfoObj.actionID + "");
            mJumpBundle.putLong("URL_BUILD_PERE_BOOK_ID", mAdvExtInfoObj.actionID);
            mJumpBundle.putString("com.qq.reader.WebContent", getAdvLinkUrl());
//            b.putString(NativeAction.KEY_JUMP_PAGENAME, NativeAction.getJumpPageNameWithServerAction(mAdvExtInfoObj.actionType));
        }

        return mJumpBundle;
    }



    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String aVersion) {
        this.mVersion = aVersion;
    }

    public static class AdvExtinfo {

        public static final String ADVEXTINFO_CATEGORY = "uitype";
        public static final String ADVEXTINFO_NUMBUR = "data";
        public static final String ADVEXTINFO_NUMBUR_DESC = "data_desc";
        public static final String ADVEXTINFO_ACTION = "action";
        public static final String ADVEXTINFO_ACTIONTAG = "actionTag";
        public static final String ADVEXTINFO_ACTIONID = "actionId";
        public static final String ADVEXTINFO_NEED_LOGIN = "forceLogin";

        /**
         * 100126广告类型，native or web or offlinepackage
         */
        public static final String ADVEXTINFO_POPNATIVEDIALOGTYPE = "popnativedialog_type";
        /**
         * 100126广告弹框样式 ，bottom，center
         */
        public static final String ADVEXTINFO_POPNATIVEDIALOGGRAVITY = "showPosition";

        /**
         * 100126广告弹框频率 ，0 每日一次 1仅弹一次 3每次都弹
         */
        public static final String ADVEXTINFO_SHOW_LIMIT = "show_limit";

        /**
         * 素材 Id
         */
        private static final String ADVEXTINFO_MATERIAL = "material";
        /**
         * 素材类型
         */
        private static final String ADVEXTINFO_MATERIAL_SOURCE = "materialSource";
        /**
         * 广告优先级表示，同一广告位有多条广告时，取优先级最高的
         * 109 新增 by p_bbinlliu
         */
        public static final String ADVEXTINFO_PRIORITY = "priority";

        /**
         * 广告显示标签
         */
        public static final String ADVEXTINFO_DISPLAY_LABLE = "label";

        public static final String ADVEXTINFO_DISPLAY_INTERVAL = "chapterLength";

        public static final String ADVEXTINFO_SHOW_TIME = "showTime"; //自定义属性
        public static final String ADVEXTINFO_STYLE = "style"; //自定义属性

//        public static final String ADVEXTINFO_HUAWEINEWBANNER="huaweiNewBanner";//华为banner字段

        public static  final int ADV_DISPLAY_INTERVAL_DEFAULT = 7;

        public static final String ADVEXTINFO_BOOKSHELF_BG_URL = "backgroundUrl";
        public static final String ADVEXTINFO_BOOKSHELF_ICON_URL = "iconUrl";
        public static final String ADVEXTINFO_BOOKSHELF_DAY_NIGHT = "dayAndNight";

        //书架顶部使用
        String backgroundUrl = "";
        String dayAndNight = "";
        String iconUrl = "";


        //广告优先级
        int priority;
        //素材 ID
        long materialId;
        //素材类型
        String materialType;
        public static String MATERIAL_TYPE_ALG = "algorithm";

        int category;
        long number;
        String number_desc;
        String actionType;
        String actionTag;
        long actionID;
        boolean mJumpNeedLogin = false;//跳转是否需要强制登录

        public String getActionType() {
            return actionType;
        }

        int show_limit;

        String color;

        /**
         * 100126弹窗位置， 0：底部，1：中间
         * 同时底部样式均为
         * 中间样式均为
         * 真是呵呵哒
         */
        int mPopNativeDialogGravity;

        String displayLabel;

        int mDisplayInterval = ADV_DISPLAY_INTERVAL_DEFAULT;

//        ExtJson extJson;
//
//        public ExtJson getExtJson() {
//            return extJson;
//        }
//
//        public void setExtJson(ExtJson extJson) {
//            this.extJson = extJson;
//        }

        public AdvExtinfo parseExtInfo(String aExtInfo) {

            JSONObject extInfo;
            try {
                extInfo = new JSONObject(aExtInfo);

                try {

                    actionType = extInfo.getString(ADVEXTINFO_ACTION);
                    actionTag = extInfo.getString(ADVEXTINFO_ACTIONTAG);
                } catch (JSONException e) {
                    // TODO 忽略

                }

                try {
                    actionID = Long.valueOf(extInfo
                            .getString(ADVEXTINFO_ACTIONID));
                } catch (NumberFormatException e) {
                    // TODO 忽略

                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                }

                try {
                    category = Integer.valueOf(extInfo.getString(ADVEXTINFO_CATEGORY));
                } catch (NumberFormatException e) {
                    // TODO 忽略

                } catch (JSONException e) {
                    // TODO 忽略

                }

                number_desc = extInfo.optString(ADVEXTINFO_NUMBUR_DESC);

                try {
                    number = Long.valueOf(extInfo.optString(ADVEXTINFO_NUMBUR));
                }catch (Exception e){

                }

                mJumpNeedLogin = extInfo.optInt(ADVEXTINFO_NEED_LOGIN, 0) > 0 ? true : false;

                mPopNativeDialogGravity = extInfo.optInt(ADVEXTINFO_POPNATIVEDIALOGGRAVITY);

                show_limit = extInfo.optInt(ADVEXTINFO_SHOW_LIMIT,0);

                displayLabel = extInfo.optString(ADVEXTINFO_DISPLAY_LABLE);

                mDisplayInterval = extInfo.optInt(ADVEXTINFO_DISPLAY_INTERVAL,ADV_DISPLAY_INTERVAL_DEFAULT);

                color = extInfo.optString(ADVEXTINFO_STYLE);

//                if(extInfo.optString(ADVEXTINFO_HUAWEINEWBANNER)!=null&&!"".equals(extInfo.optString(ADVEXTINFO_HUAWEINEWBANNER))){
//                    extJson= GsonUtil.parseJsonWithGson(extInfo.optString(ADVEXTINFO_HUAWEINEWBANNER),ExtJson.class);
//                }

                mPopNativeDialogGravity = extInfo.optInt(ADVEXTINFO_POPNATIVEDIALOGGRAVITY);

                priority = extInfo.optInt(ADVEXTINFO_PRIORITY, 0);

                materialId = extInfo.optLong(ADVEXTINFO_MATERIAL);
                materialType = extInfo.optString(ADVEXTINFO_MATERIAL_SOURCE);
                backgroundUrl = extInfo.optString(ADVEXTINFO_BOOKSHELF_BG_URL);
                dayAndNight = extInfo.optString(ADVEXTINFO_BOOKSHELF_DAY_NIGHT);
                iconUrl = extInfo.optString(ADVEXTINFO_BOOKSHELF_ICON_URL);

            } catch (JSONException e1) {
                // TODO 忽略

            }

            return this;
        }
    }

    public String getBackGroundUrl() {
        if (mAdvExtInfoObj != null) {
            return mAdvExtInfoObj.backgroundUrl;
        }
        return "";
    }

    public String getDayAndNight() {
        if (mAdvExtInfoObj != null) {
            return mAdvExtInfoObj.dayAndNight;
        }
        return "";
    }

    public String getIconUrl() {
        if (mAdvExtInfoObj != null) {
            return mAdvExtInfoObj.iconUrl;
        }
        return "";
    }

    public Advertisement setAdvValueType(int adValueType) {
        this.mAdvValueType = adValueType;
        return this;
    }

    public int getAdvValueType() {
        return mAdvValueType;
    }

    public long getLastShowTime() {
        return mLastShowTime;
    }

    public void setLastShowTime(long lastShowTime) {
        this.mLastShowTime = lastShowTime;
    }

    //---------------------start
    //弹窗逻辑添加
    private String showLimit = "0"; // 显示控制 0:每日一次,1:仅弹一次,2:每次都弹,3:每1小时一次,4:每2小时一次,5:每3小时一次,6:每4小时一次
    public String getShowLimit() {
        return showLimit;
    }

    public void setShowLimit(String showLimit) {
        this.showLimit = showLimit;
    }

//    public String getShowTime() {
//        if(mAdvExtInfoObj!=null){
//            return mAdvExtInfoObj.showTime;
//        }
//
//        return "";
//    }

    public int getPriority() {
        if (mAdvExtInfoObj != null) {
            return mAdvExtInfoObj.priority;
        }
        return 0;
    }

    public long getMaterialId() {
        if (mAdvExtInfoObj != null) {
            return mAdvExtInfoObj.materialId;
        }
        return 0;
    }
    public String getMaterialType() {
        if (mAdvExtInfoObj != null) {
            return mAdvExtInfoObj.materialType;
        }
        return "";
    }

//    public boolean setShowTime(long showTime) {
//        JSONObject object = null;
//        try {
//            if(TextUtils.isEmpty(mAdvExtInfo)){
//                object = new JSONObject();
//            }else {
//                object = new JSONObject(mAdvExtInfo);
//            }
//            object.put(ADVEXTINFO_SHOW_TIME,showTime);
//            setAdvExtInfo(object.toString());
//            return true;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    //--------------------end

}
