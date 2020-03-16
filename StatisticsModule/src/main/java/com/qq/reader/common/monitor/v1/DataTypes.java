package com.qq.reader.common.monitor.v1;

/**
 * Created by liuxiaoyang on 2017/10/18.
 */

public class DataTypes {
    public static final String VIEW_MORE = "more";
    // 通用按钮
    public static final String VIEW_JUMP = "jump";
    // 跳转书库按钮
    public static final String VIEW_BOOKSTORE = "store";
    //超值包
    public static final String VIEW_PACKAGE = "package";

    // 详情页
    //下载按钮
    public static final String VIEW_DOWNLOAD = "download";
    //免费试读按钮
    public static final String VIEW_READ = "read";
    //加入书架按钮
    public static final String VIEW_ADD_SHELF = "addshelf";

    public static final String VIEW_CHARGE = "charge";//充值
    public static final String DIALOG_CHARGE = "charge";
    public static final String VIEW_CHANGE = "change";//换一换
    public static final String VIEW_CLEAR = "clear";//清空

    public static final String VIEW_CHANGE_BUY = "chargebuy";//充值并购买

    public static final String DATA_BOOK = "bid";
    public static final String DATA_ARTICLE = "articleid";
    public static final String DATA_AUTHOR = "authorid";
    public static final String DATA_AD = "aid";
    // 栏目作为一个可点击的元素时  在不知道是广告还是栏目是，统一用column
    public static final String DATA_COLUMN = "column";

    public static final String PAY_EDU_DIALOG_GET = "get";
    public static final String PAY_EDU_DIALOG_CLOSE = "close";
    public static final String DIALOG_BUY = "buy";
    public static final String DIALOG_QUICK_BUY = "quickbuy";

    // 编辑
    public static final String DATA_EDITOR = "editorId";
    // 入口
    public static final String ENTRY = "entry";
    // 专题 话题
    public static final String DATA_TOPIC = "topicId";
    // 分类 后台一般返回 actionId，上报时统一写死用categoryId
    public static final String DATA_CATGORY = "categoryId";
    // 榜单
    public static final String DATA_RANK = "rankId";
    // 设置基因
    public static final String VIEW_SET_GEN = "gene";

    //顶部tab
    public static final String DATA_TAB = "tab";

    //经典左侧tab对应的item (栏目id)
    public static final String DATA_TAB_ITEM_CLASSIC = DATA_COLUMN;
    //名人堂左侧tab对应的item
    public static final String DATA_TAB_ITEM_AUTHOR = DATA_COLUMN;

    //名人堂右侧tab对应的item
    public static final String DATA_AUTHOR_ITEM_AUTHOR = DATA_AUTHOR;

    //导入本地书阅读尾页搜索
    public static final String VIEW_SEARCH = "search";

    //导入本地书阅读尾页目录
    public static final String VIEW_CATALOG = "catalog";

    //偏好 1男 2女 0出版
    public static final String PREFER = "prefer";

    //标签
    public static final String DATA_LABEL = "label";
    //搜索
    public static final String DATA_SEARCH = "search";
    //搜索历史关键词
    public static final String DATA_HISTORY_KEY = "history_key";
    //筛选
    public static final String VIEW_SCREEN = "screen";
    //筛选菜单栏收起时的状态
    public static final String VIEW_TOP_SCREEN = "top_screen";
    //书封
    public static final  String BOOK_COVER = "bookcover";
    //弹窗
    public static final  String VIEW_POP = "pop";

    //弹窗登录
    public static final  String VIEW_POP_LOGIN = "pop_login";

    //菜单栏查看福利按钮
    public static final  String VIEW_POP_INTEGRAL = "pop_integral";

    //积分
    public static final  String DATA_INTEGRAL = "integral";
    //未登录
    public static final  String VIEW_UNLOGIN = "unlogin";
    //书籍详情页 沉迷度
    public static final  String VIEW_FAVOURITE = "favourite";
    //书籍详情页 继续阅读按钮
    public static final  String VIEW_CONTINUE = "k_read";
    public static final  String VIEW_NEXT_CHAPTER = "l_read";

    //搜索页，搜索热词
    public static final String DATA_KEY = "key";

    //阅读页30s激励
    public static final String READ_PAGE_AWARD = "30_integral";
    //拼团
    public static final String DATA_ASSEMBLE = "assemble";
    //拼团标志
    public static final String VIEW_ASSEMBLE_FLAG = "assemble_top_flag";
    //拼团书库
    public static final String VIEW_ASSEMBLE_STORE = "assemble_store";
    //拼团活动
    public static final String VIEW_ASSEMBLE_ACTIVE = "assemble_active";
    //免费试读
    public static final String VIEW_FREE_READ = "freeread";
    //信息流反馈
    public static final String VIEW_FEED_BACK = "feedback";
    //彩蛋
    public static final String VIEW_EGG = "egg";
}
