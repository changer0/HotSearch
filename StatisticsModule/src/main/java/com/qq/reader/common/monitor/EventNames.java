package com.qq.reader.common.monitor;

/**
 * Created by liuxiaoyang on 2017/5/18.
 * <p>
 * 所有统计名称放到这里
 */

public class EventNames {

    // 点击曝光通用事件名称
    public static final String EVENT_EXPOSURE = "shown";
    public static final String EVENT_CLICK = "clicked";

    /**
     * A/B test  统计
     */
    public static final String ABTEST_A = "abtest_A";
    public static final String ABTEST_B = "abtest_B";

    public static final String EVENT_QM_SUCCESS = "QM";//QM上报成功
    public static final String EVENT_QM_START = "QM_START";//每次QM上报
    public static final String EVENT_QM_ERROR = "QM_ERROR";//上报失败

    // 信息流曝光点击算法上报
    public static final String EVENT_FEED_EXPOSURE = "event_feed_exposure";
    public static final String EVENT_FEED_CLICK = "event_feed_click";

    public static final String EVENT_M88 = "event_M88";

    // TODO delete
    public static final String EVENT_C79 = "event_C79";

    /**********************************以下是新灯塔事件ID*************************************/

    /**
     * 书架相关
     */
    public static final String EVENT_XA001 = "event_XA001";//书架tab点击
    public static final String EVENT_XA002 = "event_XA002";//书架页面曝光
    public static final String EVENT_XA003 = "event_XA003";//书架左上角头像点击
    public static final String EVENT_XA004 = "event_XA004";//书架新手礼包曝光
    public static final String EVENT_XA005 = "event_XA005";//书架新手礼包点击
    public static final String EVENT_XA006 = "event_XA006";//书架新手向导card曝光
    public static final String EVENT_XA007 = "event_XA007";//书架新手向导card点击
    public static final String EVENT_XA008 = "event_XA008";//书架页-签到button的曝光
    public static final String EVENT_XA009 = "event_XA009";//书架页-签到button的点击
    public static final String EVENT_XA010 = "event_XA010";//书架页-漏签(补签)button的曝光
    public static final String EVENT_XA011 = "event_XA011";//书架页-漏签(补签)button的点击
    public static final String EVENT_XA012 = "event_XA012";//书架页-漏签弹窗曝光
    public static final String EVENT_XA013 = "event_XA013";//书架页-漏签弹窗“X书币补签”按钮的点击量
    public static final String EVENT_XA014 = "event_XA014";//书架页-抽奖button的曝光
    public static final String EVENT_XA015 = "event_XA015";//书架页-抽奖button的点击
    public static final String EVENT_XA016 = "event_XA016";//书架页-领奖button的曝光
    public static final String EVENT_XA017 = "event_XA017";//书架页-领奖button的点击
    public static final String EVENT_XA018 = "event_XA018";//书架页-签到成功弹窗的曝光
    public static final String EVENT_XA019 = "event_XA019";//书架页-签到成功弹窗礼物区曝点击
    public static final String EVENT_XA020 = "event_XA020";//书架页-阅读时长栏的点击
    public static final String EVENT_XA021 = "event_XA021";//书架页-阅读时长栏可领取礼包（兑换书券）按钮的曝光
    public static final String EVENT_XA022 = "event_XA022";//书架页-阅读时长栏可领取礼包（兑换书券）按钮的点击
    public static final String EVENT_XA023 = "event_XA023";//书架页"导入书籍"入口点击
    public static final String EVENT_XA024 = "event_XA024";//书架页"导入书籍"入口-导入本地书点击
    public static final String EVENT_XA025 = "event_XA025";//书架页"分组找书"入口点击
    public static final String EVENT_XA026 = "event_XA026";//书架-搜索按钮的点击
    public static final String EVENT_XA027 = "event_XA027";//书架页-长按书籍呼出菜单
    public static final String EVENT_XA028 = "event_XA028";//书架页-长按书籍菜单页面-删除的点击
    public static final String EVENT_XA029 = "event_XA029";//书架页-长按书籍菜单页面-分组至的点击
    public static final String EVENT_XA030 = "event_XA030";//书架页-长按书籍菜单页面-置顶的点击
    public static final String EVENT_XA031 = "event_XA031";//书架页-长按书籍菜单页面-分享的点击
    public static final String EVENT_XA032 = "event_XA032";//书架页-长按书籍菜单页面-私密阅读的点击
    public static final String EVENT_XA033 = "event_XA033";//书架页-“文字广告位”曝光
    public static final String EVENT_XA034 = "event_XA034";//书架页-“文字广告位”点击
    public static final String EVENT_XA035 = "event_XA035";//公益详情页面,分享按钮> 分享到 微信 的点击
    public static final String EVENT_XA036 = "event_XA036";//公益详情页面,分享按钮> 分享到 朋友圈的点击
    public static final String EVENT_XA037 = "event_XA037";//公益详情页面,分享按钮> 分享到 QQ的点击
    public static final String EVENT_XA038 = "event_XA038";//公益详情页面,分享按钮> 分享到 QQ空间的点击
    public static final String EVENT_XA039 = "event_XA039";//公益详情页面,分享按钮> 分享到 微博的点击
    public static final String EVENT_XA040 = "event_XA040";//书架页-点立即签到后登录的用户
    public static final String EVENT_XA041 = "event_XA041";//书架页本地书籍点击阅读
    public static final String EVENT_XA042 = "event_XA042";//书架页书城书籍点击阅读
    public static final String EVENT_XA043 = "event_XA043";//连载更新提醒”开启”点击
    public static final String EVENT_XA044 = "event_XA044";//连载更新提醒“关闭”点击
    public static final String EVENT_XA050 = "event_XA050";//书券树按钮曝光
    public static final String EVENT_XA051 = "event_XA051";//书券树按钮点击
    public static final String EVENT_XA052 = "event_XA052";//书架页书籍点击阅读（41+42）
    public static final String EVENT_XA053 = "event_XA053";//书架，分组页面书籍点击


    public static final String EVENT_XE001 = "event_XE001";//我的tab点击
    public static final String EVENT_XE002 = "event_XE002";//我的页面的曝光
    public static final String EVENT_XE003 = "event_XE003";//我的-主页icon的点击
    public static final String EVENT_XE004 = "event_XE004";//我的-账户icon的点击
    public static final String EVENT_XE005 = "event_XE005";//我的-充值icon的点击
    public static final String EVENT_XE006 = "event_XE006";//我的-包月icon的点击
    public static final String EVENT_XE007 = "event_XE007";//我的-兑换icon的点击
    public static final String EVENT_XE008 = "event_XE008";//我的-消息icon的点击
    public static final String EVENT_XE009 = "event_XE009";//我的-“会员”文字的点击   华为
    public static final String EVENT_XE010 = "event_XE010";//我的-我的超值包栏点击
    public static final String EVENT_XE011 = "event_XE011";//我的-今日任务栏的点击
    public static final String EVENT_XE012 = "event_XE012";//我的-浏览历史栏的点击
    public static final String EVENT_XE013 = "event_XE013";//我的-我的收藏栏的点击
    public static final String EVENT_XE014 = "event_XE014";//我的-我的基因栏的点击
    public static final String EVENT_XE015 = "event_XE015";//我的-设置栏的点击
    public static final String EVENT_XE016 = "event_XE016";//我的-活动专区栏的点击
    public static final String EVENT_XE017 = "event_XE017";//我的-我的等级栏的点击
    public static final String EVENT_XE018 = "event_XE018";//我的-检查更新栏的点击
    public static final String EVENT_XE019 = "event_XE019";//包月特权页的曝光
    public static final String EVENT_XE020 = "event_XE020";//包月特权页-开通包月按钮的点击
    public static final String EVENT_XE021 = "event_XE021";//包月特权页-包月畅读的点击
    public static final String EVENT_XE022 = "event_XE022";//包月特权页-签到特权的点击
    public static final String EVENT_XE023 = "event_XE023";//包月特权页-成长加速的点击
    public static final String EVENT_XE024 = "event_XE024";//我的-我的基因页的曝光
    public static final String EVENT_XE025 = "event_XE025";//我的-我的基因-调整我的基因button的点击
    public static final String EVENT_XE026 = "event_XE026";//我的-我的基因-分享给朋友的点击
    public static final String EVENT_XE027 = "event_XE027";//我的-我的基因-调整我的基因-保存修改的点击
    public static final String EVENT_XE028 = "event_XE028";//我的-我的超值包页面的曝光
    public static final String EVENT_XE029 = "event_XE029";//我的-我的超值包-超值包页面的点击
    public static final String EVENT_XE030 = "event_XE030";//我的-我的超值包-超值包详情页-立即订阅按钮的点击
    public static final String EVENT_XE031 = "event_XE031";//我的-我的帐户页面的曝光
    public static final String EVENT_XE032 = "event_XE032";//我的-帐户-书币充值button的点击
    public static final String EVENT_XE033 = "event_XE033";//我的-帐户-包月开通button的曝光
    public static final String EVENT_XE034 = "event_XE034";//我的-帐户-包月开通button的点击
    public static final String EVENT_XE035 = "event_XE035";//我的-我的主页页面的曝光
    public static final String EVENT_XE036 = "event_XE036";//我的主页-领取礼包的点击
    public static final String EVENT_XE037 = "event_XE037";//我的等级-包月开通按钮的点击
    public static final String EVENT_XE038 = "event_XE038";//我的等级-每月礼包领取按钮的点击
    public static final String EVENT_XE039 = "event_XE039";//活动专区"最新"tab点击
    public static final String EVENT_XE040 = "event_XE040";//活动专区"最新"页面曝光
    public static final String EVENT_XE041 = "event_XE041";//活动专区"新手"tab点击
    public static final String EVENT_XE042 = "event_XE042";//活动专区"新手"页面曝光
    public static final String EVENT_XE043 = "event_XE043";//今日任务页的曝光
    public static final String EVENT_XE044 = "event_XE044";//今日任务页开通包月按钮的点击
    public static final String EVENT_XE045 = "event_XE045";//今日任务-追更-去完成的点击
    public static final String EVENT_XE046 = "event_XE046";//今日任务-连续阅读-去完成的点击
    public static final String EVENT_XE047 = "event_XE047";//我的页面，阅读日活动入口button曝光
    public static final String EVENT_XE048 = "event_XE048";//我的页面，阅读日活动入口button点击

    public static final String EVENT_XE049 = "event_XE049";//我的-我的笔记栏的点击
    public static final String EVENT_XE069 = "event_XE069";//我的-我的笔记栏的点击//飞读上新加的，已和林青确认用这个，不用event_XE049
    public static final String EVENT_XE050 = "event_XE050";//我的-我的笔记页面的曝光
    public static final String EVENT_XE051 = "event_XE051";//我的-设置页面的曝光
    public static final String EVENT_XE070 = "event_XE070";//我的-我的偏好栏的点击
    public static final String EVENT_XE071 = "event_XE071";//我的-我的偏好选项弹窗的曝光
    public static final String EVENT_XE072 = "event_XE072";//我的-我的偏好选项的点击
    public static final String EVENT_XE108 = "event_XE108";//我的-用户当前偏好的曝光

    /**
     * 墨水屏模式事件统计
     */
    public static final String EVENT_XB550 = "event_XB550";//开启墨水屏应用弹窗的曝光
    public static final String EVENT_XB551 = "event_XB551";//开启墨水屏应用弹窗的“不用了”按钮点击
    public static final String EVENT_XB552 = "event_XB552";//开启墨水屏应用弹窗的“立即开启”按钮点击
    public static final String EVENT_XB553 = "event_XB553";//阅读页墨水屏按钮的曝光
    public static final String EVENT_XB554 = "event_XB554";//阅读页墨水屏按钮的点击  扩展参数：开启1， 关闭0
    public static final String EVENT_XE052 = "event_XE052";//我的-设置-墨水屏模式启动按钮的曝光
    public static final String EVENT_XE053 = "event_XE053";//我的-设置-墨水屏模式启动按钮的点击
    public static final String EVENT_XG012 = "event_XG012";//用户使用墨水屏模式

    public static final String EVENT_XE054 = "event_XE054";//我的-设置-我的插件入口的点击
    public static final String EVENT_XE055 = "event_XE055";//我的-设置-我的插件页面的曝光
    public static final String EVENT_XE056 = "event_XE056";//我的-设置-个性主题入口的点击
    public static final String EVENT_XE057 = "event_XE057";//我的-设置-个性主题页面的曝光
    public static final String EVENT_XE058 = "event_XE058";//我的-帮助与反馈入口的点击
    public static final String EVENT_XE059 = "event_XE059";//我的-帮助与反馈页面的曝光

    public static final String EVENT_XE060 = "event_XE060";//我的-设置-通知管理入口的点击
    public static final String EVENT_XE061 = "event_XE061";//我的-设置-通知管理页面的曝光
    public static final String EVENT_XE062 = "event_XE062";//我的-设置-通知管理-福利通知开启按钮的点击
    public static final String EVENT_XE063 = "event_XE063";//我的-设置-通知管理-到期通知开启按钮的点击

    public static final String EVENT_XE202 = "event_XE202";//我的页设置-通知管理的点击
    public static final String EVENT_XE203 = "event_XE203";//我的页设置-绑定手机的点击


    /********************   精选页  **************************/
    public static final String EVENT_XJ001 = "event_XJ001";//精选tab点击
    public static final String EVENT_XJ002 = "event_XJ002";//精选页面曝光
    public static final String EVENT_XJ003 = "event_XJ003";//精选按钮点击刷新
    public static final String EVENT_XJ004 = "event_XJ004";//精选页上方banner曝光
    public static final String EVENT_XJ005 = "event_XJ005";//精选页上方banner点击
    public static final String EVENT_XJ006 = "event_XJ006";//精选页第1个icon点击
    public static final String EVENT_XJ007 = "event_XJ007";//精选页第2个icon点击
    public static final String EVENT_XJ008 = "event_XJ008";//精选页第3个icon点击
    public static final String EVENT_XJ009 = "event_XJ009";//精选页第4个icon点击
    public static final String EVENT_XJ010 = "event_XJ010";//精选页第5个icon点击
    public static final String EVENT_XJ011 = "event_XJ011";//精选页>新手快捷入口 card 曝光
    public static final String EVENT_XJ012 = "event_XJ012";//精选页>新手快捷入口card > 第1个按钮的点击
    public static final String EVENT_XJ013 = "event_XJ013";//精选页>新手快捷入口card > 第2个按钮的点击
    public static final String EVENT_XJ014 = "event_XJ014";//精选页>新手快捷入口card > 第3个按钮的点击
    public static final String EVENT_XJ015 = "event_XJ015";//(华为)信息流，固定入口下方文字链广告位曝光
    public static final String EVENT_XJ016 = "event_XJ016";//(华为)信息流，固定下方文字链广告位点击
    public static final String EVENT_XJ020 = "event_XJ020";//书券树按钮曝光
    public static final String EVENT_XJ021 = "event_XJ021";//书券树按钮点击
    public static final String EVENT_XJ022 = "event_XJ022";//精选信息流上拉加载
    public static final String EVENT_XJ023 = "event_XJ023";//精选下拉刷新
    public static final String EVENT_XJ024 = "event_XJ024";//精选点返回键
    public static final String EVENT_XJ025 = "event_XJ025";//精选页上方搜索按钮的点击
//    public static final String EVENT_XJ026 = "event_XJ026";//信息流下方快捷找书按钮的曝光
//    public static final String EVENT_XJ027 = "event_XJ027";//信息流下方快捷找书按钮的点击
    public static final String EVENT_B016 = "event_B016"; // 信息流 公告位 点击


    /********************   阅读页  常规、菜单、阅读日、阅读任务   **************************/
    public static final String EVENT_XB001 = "event_XB001";//阅读页-书城书页面的曝光
    public static final String EVENT_XB002 = "event_XB002";//阅读页-导入书页面的曝光
    public static final String EVENT_XB003 = "event_XB003";//阅读页-阅读日收获奖励入口曝光
    public static final String EVENT_XB004 = "event_XB004";//阅读页-阅读日收获奖励入口点击
    public static final String EVENT_XB005 = "event_XB005";//呼出阅读页上下菜单
    public static final String EVENT_XB006 = "event_XB006";//阅读页菜单顶栏“返回”按钮点击
    public static final String EVENT_XB007 = "event_XB007";//阅读页菜单顶栏“购买”按钮点击
    public static final String EVENT_XB008 = "event_XB008";//阅读页菜单顶栏“听书”按钮点击
    public static final String EVENT_XB009 = "event_XB009";//阅读页菜单顶栏“投票”按钮点击
    public static final String EVENT_XB010 = "event_XB010";//阅读页菜单，投票按钮 >【投推荐票】按钮点击
    public static final String EVENT_XB011 = "event_XB011";//阅读页菜单，投票按钮 >【打赏】按钮点击
    public static final String EVENT_XB012 = "event_XB012";//阅读页菜单，投票按钮 >【投月票】按钮点击
    public static final String EVENT_XB013 = "event_XB013";//阅读页菜单顶栏“更多”按钮点击
    public static final String EVENT_XB014 = "event_XB014";//阅读页菜单顶栏-->更多-->书签
    public static final String EVENT_XB015 = "event_XB015";//阅读页菜单顶栏-->更多-->全文搜索
    public static final String EVENT_XB016 = "event_XB016";//阅读页菜单顶栏-->更多-->书籍详情
    public static final String EVENT_XB017 = "event_XB017";//阅读页菜单顶栏-->更多-->分享本书
    public static final String EVENT_XB018 = "event_XB018";//灯绳的点击(切换夜间模式）
    public static final String EVENT_XB019 = "event_XB019";//阅读页菜单底栏-->“目录”按钮点击
    public static final String EVENT_XB020 = "event_XB020";//阅读页-目录-书签的点击
    public static final String EVENT_XB021 = "event_XB021";//阅读页-目录-笔记的点击
    public static final String EVENT_XB022 = "event_XB022";//阅读页菜单底栏-->“进度”按钮点击
    public static final String EVENT_XB023 = "event_XB023";//阅读页菜单底栏-->“评论”按钮点击
    public static final String EVENT_XB024 = "event_XB024";//阅读页菜单底栏-->“设置”按钮点击
    public static final String EVENT_XB025 = "event_XB025";//阅读页菜单底栏-->设置-->调整亮度
    public static final String EVENT_XB026 = "event_XB026";//阅读页菜单底栏-->设置-->系统亮度跟随
    public static final String EVENT_XB027 = "event_XB027";//阅读页菜单底栏-->设置-->系统亮度取消
    public static final String EVENT_XB028 = "event_XB028";//阅读页菜单底栏-->设置-->调整字体
    public static final String EVENT_XB029 = "event_XB029";//阅读页菜单底栏-->设置-->调整字号
    public static final String EVENT_XB030 = "event_XB030";//阅读页菜单底栏-->设置-->调整阅读背景
    public static final String EVENT_XB031 = "event_XB031";//阅读页菜单底栏-->设置-->横/竖屏
    public static final String EVENT_XB032 = "event_XB032";//阅读页菜单底栏-->设置-->夜间模式
    public static final String EVENT_XB033 = "event_XB033";//阅读页菜单底栏-->设置-->自动阅读
    public static final String EVENT_XB034 = "event_XB034";//阅读页菜单底栏-->设置-->人声朗读
    public static final String EVENT_XB035 = "event_XB035";//阅读页菜单底栏-->设置-->更多设置
    public static final String EVENT_XB036 = "event_XB036";//阅读页 下载精排字体弹窗曝光
    public static final String EVENT_XB037 = "event_XB037";//阅读页 下载精排字体弹窗，【试试看】按钮点击
    public static final String EVENT_XB038 = "event_XB038";//阅读页 下载精排字体弹窗，【不用了】按钮点击
    public static final String EVENT_XB039 = "event_XB039";//阅读页-长按文本编辑功能的曝光
    public static final String EVENT_XB040 = "event_XB040";//阅读页-长按文本编辑功能-复制的点击
    public static final String EVENT_XB041 = "event_XB041";//阅读页-长按文本编辑功能-划线的点击
    public static final String EVENT_XB042 = "event_XB042";//阅读页-长按文本编辑功能-笔记的点击
    public static final String EVENT_XB043 = "event_XB043";//阅读页-长按文本编辑功能-分享的点击
    public static final String EVENT_XB044 = "event_XB044";//阅读页-长按文本编辑功能-词典的点击
    public static final String EVENT_XB045 = "event_XB045";//阅读页-长按文本编辑功能-纠错的点击
    public static final String EVENT_XB046 = "event_XB046";//阅读页底部“任务提示”曝光
    public static final String EVENT_XB047 = "event_XB047";//阅读页底部“任务提示”点击
    public static final String EVENT_XB048 = "event_XB048";//点击退出阅读页时，提示任务奖励领取弹窗曝光
    public static final String EVENT_XB049 = "event_XB049";//点击退出阅读页时，提示任务奖励领取弹窗>领取按钮的点击
    public static final String EVENT_XB050 = "event_XB050";//阅读页试读退出时，加书架提示card的曝光
    public static final String EVENT_XB051 = "event_XB051";//阅读页试读退出时，加书架提示card > 加入书架的按钮点击
    public static final String EVENT_XB052 = "event_XB052";//章节尾页【大家来讨论】文字链曝光
    public static final String EVENT_XB053 = "event_XB053";//章节尾页【大家来讨论】文字链点击


    /********************   阅读页 尾页相关  **************************/
    public static final String EVENT_XB054 = "event_XB054";//阅读尾页曝光
    public static final String EVENT_XB055 = "event_XB055";//阅读尾页 >【打赏】按钮点击
    public static final String EVENT_XB056 = "event_XB056";//阅读尾页 >【投推荐票】按钮点击
    public static final String EVENT_XB057 = "event_XB057";//阅读尾页 >【投月票】按钮点击
    public static final String EVENT_XB058 = "event_XB058";//(华为)阅读尾页 >【分享】按钮点击
    public static final String EVENT_XB059 = "event_XB059";//阅读尾页 >【书评区】按钮点击
    public static final String EVENT_XB060 = "event_XB060";//阅读尾页 >【互动】按钮点击
    public static final String EVENT_XB061 = "event_XB061";//阅读尾页评分点击


    /********************   阅读页 付费页相关   **************************/
    public static final String EVENT_XB300 = "event_XB300";//阅读页-阅读付费页的曝光
    public static final String EVENT_XB301 = "event_XB301";//付费页“购买本章”按钮曝光
    public static final String EVENT_XB302 = "event_XB302";//付费页“购买本章”按钮点击
    public static final String EVENT_XB303 = "event_XB303";//付费页“购买整本”按钮曝光
    public static final String EVENT_XB304 = "event_XB304";//付费页“购买整本”按钮点击
    public static final String EVENT_XB305 = "event_XB305";//付费页“余额不足，充值并购买”按钮曝光
    public static final String EVENT_XB306 = "event_XB306";//付费页“余额不足，充值并购买”按钮点击
    public static final String EVENT_XB307 = "event_XB307";//付费页“自动购买下一章”选中点击
    public static final String EVENT_XB308 = "event_XB308";//付费页“自动购买下一章”取消点击
    public static final String EVENT_XB309 = "event_XB309";//付费页“时长兑换”曝光
    public static final String EVENT_XB310 = "event_XB310";//付费页时长兑换“兑换”按钮点击
    public static final String EVENT_XB311 = "event_XB311";//付费页“新手礼包”曝光
    public static final String EVENT_XB312 = "event_XB312";//付费页新手礼包“领取”按钮点击
    public static final String EVENT_XB313 = "event_XB313";//付费页“首充活动”曝光
    public static final String EVENT_XB314 = "event_XB314";//付费页“首充活动”点击
    public static final String EVENT_XB315 = "event_XB315";//付费页“非首充活动”曝光
    public static final String EVENT_XB316 = "event_XB316";//付费页“非首充活动”点击
    public static final String EVENT_XB317 = "event_XB317";//付费页“其他活动”曝光
    public static final String EVENT_XB318 = "event_XB318";//付费页“其他活动”点击
    public static final String EVENT_XB319 = "event_XB319";//付费页“开通包月”曝光
    public static final String EVENT_XB320 = "event_XB320";//付费页“开通包月”点击


    /********************   阅读页&详情页 批量购买相关  **************************/
    public static final String EVENT_XB500 = "event_XB500";//书籍按章购买，批量下载页面曝光
    public static final String EVENT_XB501 = "event_XB501";//批量下载页，免费下载按钮曝光
    public static final String EVENT_XB502 = "event_XB502";//批量下载页，免费下载按钮点击
    public static final String EVENT_XB503 = "event_XB503";//批量下载页，一键购买按钮曝光
    public static final String EVENT_XB504 = "event_XB504";//批量下载页，一键购买按钮点击
    public static final String EVENT_XB505 = "event_XB505";//批量下载页，确认购买按钮曝光
    public static final String EVENT_XB506 = "event_XB506";//批量下载页，确认购买按钮点击
    public static final String EVENT_XB507 = "event_XB507";//批量下载页，充值按钮曝光（无充赠标签）
    public static final String EVENT_XB508 = "event_XB508";//批量下载页，充值按钮点击（无充赠标签）
    public static final String EVENT_XB509 = "event_XB509";//批量下载页，充值按钮曝光（有充赠标签）
    public static final String EVENT_XB510 = "event_XB510";//批量下载页，充值按钮点击（有充赠标签）
    public static final String EVENT_XB513 = "event_XB513";//批量下载页，优惠券的曝光
    public static final String EVENT_XB514 = "event_XB514";//批量下载页，优惠券的点击
    public static final String EVENT_XB515 = "event_XB515";//批量下载页，“有新手礼包且原价格大于300书币”的页面曝光
    public static final String EVENT_XB516 = "event_XB516";//批量下载页，“有新手礼包且原价格大于300书币”的页面中<充值>按钮曝光
    public static final String EVENT_XB517 = "event_XB517";//批量下载页，“有新手礼包且原价格大于300书币”的页面中<充值>按钮点击
    public static final String EVENT_XB518 = "event_XB518";//批量下载页，“有新手礼包且原价格大于300书币”的页面中<一键购买>按钮曝光
    public static final String EVENT_XB519 = "event_XB519";//批量下载页，“有新手礼包且原价格大于300书币”的页面中<一键购买>按钮点击
    public static final String EVENT_XB520 = "event_XB520";//批量下载页，“有新手礼包且原价格小于300书币”的页面曝光
    public static final String EVENT_XB521 = "event_XB521";//批量下载页，“有新手礼包且原价格小于300书币”的页面中<确认购买>按钮曝光
    public static final String EVENT_XB522 = "event_XB522";//批量下载页，“有新手礼包且原价格小于300书币”的页面中<确认购买>按钮点击
    public static final String EVENT_XB559 = "event_XB559";//章节尾页的曝光
    public static final String EVENT_XB560 = "event_XB560";//作者有话说曝光


    /********************   阅读页&详情页 整本购买相关  **************************/
    public static final String EVENT_XB523 = "event_XB523";//书籍按本购买，底部购买提示弹窗曝光
    public static final String EVENT_XB524 = "event_XB524";//书籍按本购买，底部弹窗免费下载按钮曝光
    public static final String EVENT_XB525 = "event_XB525";//书籍按本购买，底部弹窗免费下载按钮点击
    public static final String EVENT_XB526 = "event_XB526";//书籍按本购买，底部弹窗充值并购买按钮曝光
    public static final String EVENT_XB527 = "event_XB527";//书籍按本购买，底部弹窗充值并购买按钮点击
    public static final String EVENT_XB528 = "event_XB528";//书籍按本购买，底部弹窗确认购买按钮曝光
    public static final String EVENT_XB529 = "event_XB529";//书籍按本购买，底部弹窗确认购买按钮点击
    public static final String EVENT_XB530 = "event_XB530";//书籍按本购买，底部弹窗充值按钮曝光（无充赠标签）
    public static final String EVENT_XB531 = "event_XB531";//书籍按本购买，底部弹窗充值按钮点击（无充赠标签）
    public static final String EVENT_XB532 = "event_XB532";//书籍按本购买，底部弹窗充值按钮曝光（有充赠标签）
    public static final String EVENT_XB533 = "event_XB533";//书籍按本购买，底部弹窗充值按钮点击（有充赠标签）
    public static final String EVENT_XB534 = "event_XB534";//书籍按本购买，底部弹窗一键购买按钮曝光
    public static final String EVENT_XB535 = "event_XB535";//书籍按本购买，底部弹窗一键购买按钮点击
    public static final String EVENT_XB536 = "event_XB536";//书籍按本购买，底部弹窗优惠券的曝光
    public static final String EVENT_XB537 = "event_XB537";//书籍按本购买，底部弹窗优惠券的点击
    public static final String EVENT_XB538 = "event_XB538";//书籍按本购买，底部弹窗“有新手礼包且原价格大于300书币”的页面曝光
    public static final String EVENT_XB539 = "event_XB539";//书籍按本购买，底部弹窗“有新手礼包且原价格大于300书币”的页面中<充值>按钮曝光
    public static final String EVENT_XB540 = "event_XB540";//书籍按本购买，底部弹窗“有新手礼包且原价格大于300书币”的页面中<充值>按钮点击
    public static final String EVENT_XB541 = "event_XB541";//书籍按本购买，底部弹窗“有新手礼包且原价格大于300书币”的页面中<一键购买>按钮曝光
    public static final String EVENT_XB542 = "event_XB542";//书籍按本购买，底部弹窗“有新手礼包且原价格大于300书币”的页面中<一键购买>按钮点击
    public static final String EVENT_XB543 = "event_XB543";//书籍按本购买，底部弹窗“有新手礼包且原价格小于300书币”的页面曝光
    public static final String EVENT_XB544 = "event_XB544";//书籍按本购买，底部弹窗“有新手礼包且原价格小于300书币”的页面中<确认购买>按钮曝光
    public static final String EVENT_XB545 = "event_XB545";//书籍按本购买，底部弹窗“有新手礼包且原价格小于300书币”的页面中<确认购买>按钮点击


    /********************   阅读页&更多设置  **************************/
    public static final String EVENT_XB643 = "event_XB643";//阅读页菜单底栏-->设置-->行间距的选择
    public static final String EVENT_XB644 = "event_XB644";//阅读页菜单底栏-->添加书签的点击
    public static final String EVENT_XB645 = "event_XB645";//阅读页菜单底栏-->书籍详情的点击
    public static final String EVENT_XB646 = "event_XB646";//阅读页菜单底栏-->更多阅读背景的曝光
    public static final String EVENT_XB647 = "event_XB647";//阅读页菜单底栏-->更多阅读背景的点击
    public static final String EVENT_XB648 = "event_XB648";//阅读页菜单底栏-->听书按钮的点击
    public static final String EVENT_XB621 = "event_XB621";//阅读页菜单底栏-->设置-->翻页方式的选择
    public static final String EVENT_XB622 = "event_XB622";//阅读页菜单底栏-->设置-->全屏阅读的选择
    public static final String EVENT_XB623 = "event_XB623";//阅读页菜单底栏-->设置-->显示底部导航栏的选择
    public static final String EVENT_XB624 = "event_XB624";//阅读页菜单底栏-->设置-->上下滑动屏幕亮度的选择
    public static final String EVENT_XB625 = "event_XB625";//阅读页菜单底栏-->设置-->音量键翻页的选择
    public static final String EVENT_XB626 = "event_XB626";//阅读页菜单底栏-->设置-->点击屏幕左侧下翻页的选择
    public static final String EVENT_XB627 = "event_XB627";//阅读页菜单底栏-->设置-->阅读界面屏保时间的选择
    public static final String EVENT_XB628 = "event_XB628";//阅读页当前设置上报
    public static final String EVENT_XB637 = "event_XB637";//阅读页特权引导条的曝光
    public static final String EVENT_XB638 = "event_XB638";//阅读页特权引导条的点击

    public static final String EVENT_XB662 = "event_XB662";//阅读页-目录-下载的点击


    /********************  发现页  **************************/
    public static final String EVENT_XF001 = "event_XF001";//发现页的曝光
    public static final String EVENT_XF002 = "event_XF002";//听书专区的点击
    public static final String EVENT_XF003 = "event_XF003";//包月专区的点击
    public static final String EVENT_XF004 = "event_XF004";//免费专区的点击
    public static final String EVENT_XF005 = "event_XF005";//排行榜的点击
    public static final String EVENT_XF006 = "event_XF006";//书评广场的点击
    public static final String EVENT_XF007 = "event_XF007";//专题的点击
    public static final String EVENT_XF008 = "event_XF008";//名人堂的点击
    public static final String EVENT_XF009 = "event_XF009";//精选书城的点击
    public static final String EVENT_XF010 = "event_XF010";//找书神器的点击
    public static final String EVENT_XF011 = "event_XF011";//华为专区的点击

    /********************  发现-书评广场  **************************/
    public static final String EVENT_XF081 = "event_XF081";//书评广场二级页的曝光
    public static final String EVENT_XF082 = "event_XF082";//书荒互助button的点击
    public static final String EVENT_XF083 = "event_XF083";//原创空间button的点击
    public static final String EVENT_XF084 = "event_XF084";//大神沙龙button的点击

    /********************  发现-专题  **************************/
    public static final String EVENT_XF085 = "event_XF085";//专题-最新-二级页的曝光
    public static final String EVENT_XF086 = "event_XF086";//专题-最新-二级页内容的的点击
    public static final String EVENT_XF087 = "event_XF087";//专题-经典-二级页的曝光
    public static final String EVENT_XF088 = "event_XF088";//专题-经典-二级页内容的点击

    /********************  发现-找书神器  **************************/
    public static final String EVENT_XF089 = "event_XF089";//找书神器页面的曝光
    public static final String EVENT_XF090 = "event_XF090";//找书神器-大家都在搜1的点击
    public static final String EVENT_XF091 = "event_XF091";//找书神器-大家都在搜2点击
    public static final String EVENT_XF092 = "event_XF092";//找书神器-大家都在搜3点击
    public static final String EVENT_XF093 = "event_XF093";//找书神器-查看更多按钮点击
    public static final String EVENT_XF094 = "event_XF094";//找书神器-自定义组合找书点击

    /********************  发现-华为专区  **************************/
    public static final String EVENT_XF095 = "event_XF095";//华为专区页面的曝光
    public static final String EVENT_XF096 = "event_XF096";//华为专区页面的点击

    /********************  发现-影视专区  **************************/
    public static final String EVENT_XF097 = "event_XF097";//影视专区的点击
    public static final String EVENT_XF098 = "event_XF098";//包月专区搜索按钮点击
    public static final String EVENT_XF099 = "event_XF099";//免费专区-搜索入口的点击

    /********************  发现-听书 **************************/
    public static final String EVENT_XF012 = "event_XF012";//听书热门推荐tab的点击
    public static final String EVENT_XF013 = "event_XF013";//听书书库tab的点击
    public static final String EVENT_XF014 = "event_XF014";//听书专区，热门推荐页面曝光
    public static final String EVENT_XF015 = "event_XF015";//听书专区，听书书库页面曝光
    public static final String EVENT_XF016 = "event_XF016";//热门推荐，顶部banner点击
    public static final String EVENT_XF017 = "event_XF017";//热门推荐，各栏目曝光
    public static final String EVENT_XF018 = "event_XF018";//热门推荐，各栏目点击
    public static final String EVENT_XF019 = "event_XF019";//听书书库，各分类tab点击
    public static final String EVENT_XF020 = "event_XF020";//听书书库，各分类列表中书籍点击
    public static final String EVENT_XF021 = "event_XF021";//听书搜索，搜索按钮点击
    public static final String EVENT_XF022 = "event_XF022";//听书搜索，输入关键词后进行搜索
    public static final String EVENT_XF023 = "event_XF023";//听书搜索，搜索主页_搜索热词曝光
    public static final String EVENT_XF024 = "event_XF024";//听书搜索，搜索主页_搜索热词点击
    public static final String EVENT_XF025 = "event_XF025";//听书搜索，搜索联想页_书架书籍曝光
    public static final String EVENT_XF026 = "event_XF026";//听书搜索，搜索联想页_书架书籍点击
    public static final String EVENT_XF027 = "event_XF027";//听书搜索，搜索联想页_联想书籍曝光
    public static final String EVENT_XF028 = "event_XF028";//听书搜索，搜索联想页_联想书籍点击
    public static final String EVENT_XF029 = "event_XF029";//听书搜索，搜索结果页_结果页曝光
    public static final String EVENT_XF030 = "event_XF030";//听书搜索，搜索结果页_书籍card点击
    public static final String EVENT_XF031 = "event_XF031";//听书搜索，搜索无结果页曝光
    public static final String EVENT_XF032 = "event_XF032";//书籍详情页，底栏_听书按钮曝光
    public static final String EVENT_XF033 = "event_XF033";//书籍详情页，底栏_听书按钮点击
    public static final String EVENT_XF034 = "event_XF034";//书籍阅读页，顶栏_听书按钮曝光
    public static final String EVENT_XF035 = "event_XF035";//书籍阅读页，顶栏_听书按钮点击
    public static final String EVENT_XF036 = "event_XF036";//听书播放面板曝光
    public static final String EVENT_XF037 = "event_XF037";//听书播放面板，书籍封面点击
    public static final String EVENT_XF038 = "event_XF038";//听书播放面板，<分享>按钮点击
    public static final String EVENT_XF039 = "event_XF039";//听书播放面板，<加入书架>按钮点击
    public static final String EVENT_XF040 = "event_XF040";//听书播放面板，<下载>按钮点击
    public static final String EVENT_XF041 = "event_XF041";//听书播放面板，<评论>按钮点击
    public static final String EVENT_XF042 = "event_XF042";//听书播放面板，<更多>按钮点击
    public static final String EVENT_XF043 = "event_XF043";//听书播放面板，更多弹窗，<自动购买下一集>开启
    public static final String EVENT_XF044 = "event_XF044";//听书播放面板，更多弹窗，<自动购买下一集>关闭
    public static final String EVENT_XF045 = "event_XF045";//听书播放面板，<目录>按钮点击
    public static final String EVENT_XF046 = "event_XF046";//听书播放面板，目录页面章节点击
    public static final String EVENT_XF047 = "event_XF047";//听书播放面板，<上一集>按钮点击
    public static final String EVENT_XF048 = "event_XF048";//听书播放面板，<播放/暂停>按钮点击
    public static final String EVENT_XF049 = "event_XF049";//听书播放面板，<下一集>按钮点击
    public static final String EVENT_XF050 = "event_XF050";//听书播放面板，<定时>按钮点击
    public static final String EVENT_XF051 = "event_XF051";//听书播放面板，定时二级页，<无>点击
    public static final String EVENT_XF052 = "event_XF052";//听书播放面板，定时二级页，<15分钟>点击
    public static final String EVENT_XF053 = "event_XF053";//听书播放面板，定时二级页，<30分钟>点击
    public static final String EVENT_XF054 = "event_XF054";//听书播放面板，定时二级页，<60分钟>点击
    public static final String EVENT_XF055 = "event_XF055";//听书播放面板，定时二级页，<90分钟>点击
    public static final String EVENT_XF056 = "event_XF056";//试听退出，提示加入书架弹窗曝光
    public static final String EVENT_XF057 = "event_XF057";//试听退出，提示加入书架弹窗，<加入书架>点击
    public static final String EVENT_XF058 = "event_XF058";//试听退出，提示加入书架弹窗，<取消>点击
    public static final String EVENT_XF059 = "event_XF059";//听书批量下载页，批量下载页面曝光
    public static final String EVENT_XF060 = "event_XF060";//听书批量下载页，<免费下载>按钮点击
    public static final String EVENT_XF061 = "event_XF061";//听书批量下载页，<确认购买>按钮点击
    public static final String EVENT_XF062 = "event_XF062";//听书批量下载页，<充值并购买>按钮点击
    public static final String EVENT_XF063 = "event_XF063";//听书按本购买，底部购买提示弹窗曝光
    public static final String EVENT_XF064 = "event_XF064";//听书按本购买，底部弹窗<购买>按钮点击
    public static final String EVENT_XF065 = "event_XF065";//听书按本购买，底部弹窗<取消>按钮点击
    public static final String EVENT_XF066 = "event_XF066";//听书下载，底部在线限免弹窗曝光
    public static final String EVENT_XF067 = "event_XF067";//听书下载，底部在线限免弹窗<继续下载>按钮点击
    public static final String EVENT_XF068 = "event_XF068";//听书下载，底部在线限免弹窗<取消>按钮点击
    public static final String EVENT_XF069 = "event_XF069";//听书单集购买，底部购买提示弹窗曝光
    public static final String EVENT_XF070 = "event_XF070";//听书单集购买，底部弹窗<自动购买下一集>勾选
    public static final String EVENT_XF071 = "event_XF071";//听书单集购买，底部弹窗<自动购买下一集>取消
    public static final String EVENT_XF072 = "event_XF072";//听书单集购买，底部弹窗<充值并购买>按钮点击
    public static final String EVENT_XF073 = "event_XF073";//听书单集购买，底部弹窗<确认购买>按钮点击
    public static final String EVENT_XF074 = "event_XF074";//听书单集购买，底部弹窗＜取消＞按钮点击
    public static final String EVENT_XF075 = "event_XF075";//听书在线播放用户数
    public static final String EVENT_XF076 = "event_XF076";//听书下载播放用户数
    public static final String EVENT_XF077 = "event_XF077";//听书播放用户数
    public static final String EVENT_XF078 = "event_XF078";//听书专区页面的曝光
    public static final String EVENT_XF079 = "event_XF079";//听书专区，各栏目曝光
    public static final String EVENT_XF080 = "event_XF080";//听书专区，各栏目点击

    /********************  其他 **************************/
    public static final String EVENT_XG001 = "event_XG001";//收到广告弹窗的用户数->弹窗曝光
    public static final String EVENT_XG002 = "event_XG002";//弹窗广告点击量->弹窗点击
    public static final String EVENT_XG003 = "event_XG003";//弹窗关闭按钮点击>弹窗关闭
    public static final String EVENT_XG007 = "event_XG007";//提醒打开PUSH弹窗的曝光
    public static final String EVENT_XG008 = "event_XG008";//提醒打开PUSH弹窗'去设置'按钮的点击
    public static final String EVENT_XG009 = "event_XG009";//提醒打开PUSH弹窗'关闭'按钮的点击
    public static final String EVENT_XG010 = "event_XG010";//用户使用个性皮肤
    public static final String EVENT_XG011 = "event_XG011";//用户返回键退出应用

    /********************  启动页 **************************/
    public static final String EVENT_XG004 = "event_XG004";//闪屏广告曝光
    public static final String EVENT_XG005 = "event_XG005";//闪屏广告点击
    public static final String EVENT_XG006 = "event_XG006";//闪屏广告 —【跳过广告】按钮点击
    public static final String EVENT_XG014 = "event_XG014";//启动页-点击男生
    public static final String EVENT_XG015 = "event_XG015";//启动页-点击女生
    public static final String EVENT_XG120 = "event_XG120";//启动页-点击跳过
    public static final String EVENT_XG121 = "event_XG121";//退出APP进后台
    public static final String EVENT_XG122 = "event_XG122";//首次打开
    public static final String EVENT_XG123 = "event_XG123";//后台进前台
    public static final String EVENT_XG124 = "event_XG124";//闪屏的曝光
    public static final String EVENT_XG125 = "event_XG125";//版本介绍页的曝光
    public static final String EVENT_XG126 = "event_XG126";//版本介绍页跳过的点击
    public static final String EVENT_XG127 = "event_XG127";//性别选择页面曝光

    /********************  搜索页 **************************/
    public static final String EVENT_XS001 = "event_XS001";//搜索按钮的点击人数
    public static final String EVENT_XS002 = "event_XS002";//搜索热词曝光
    public static final String EVENT_XS003 = "event_XS003";//搜索热词的点击
    public static final String EVENT_XS004 = "event_XS004";//搜索热词”换一换”按钮点击
    public static final String EVENT_XS005 = "event_XS005";//搜索历史的曝光
    public static final String EVENT_XS006 = "event_XS006";//搜索历史的点击

    public static final String EVENT_XS010 = "event_XS010";//搜索结果页-直达区card曝光
    public static final String EVENT_XS011 = "event_XS011";//搜索结果页-直达区card点击
    public static final String EVENT_XS012 = "event_XS012";//搜索结果页的曝光
    public static final String EVENT_XS013 = "event_XS013";//搜索结果页相关搜索词曝光
    public static final String EVENT_XS014 = "event_XS014";//搜索结果页相关搜索词点击
    public static final String EVENT_XS015 = "event_XS015";//搜索结果页单书card曝光
    public static final String EVENT_XS016 = "event_XS016";//搜索结果页单书card点击
    public static final String EVENT_XS017 = "event_XS017";//搜索框广告词曝光
    public static final String EVENT_XS018 = "event_XS018";//搜索结果页-顶部筛选栏点击
    public static final String EVENT_XS019 = "event_XS019";//搜索结果页-下拉菜单选项点击
    public static final String EVENT_XS020 = "event_XS020";//搜索框广告词点击

    /********************  分类页 **************************/
    public static final String EVENT_XD001 = "event_XD001";//分类tab点击
    public static final String EVENT_XD003 = "event_XD003";//分类页上方搜索按钮点击
//    public static final String EVENT_XD004 = "event_XD004";//分类页-上方四个icon标签的曝光
//    public static final String EVENT_XD005 = "event_XD005";//分类页-上方四个icon标签的点击

//    public static final String EVENT_XD006 = "event_XD006";//分类页-icon下面二级分类的曝光
//    public static final String EVENT_XD007 = "event_XD007";//分类页-icon下面二级分类的点击
    public static final String EVENT_XD008 = "event_XD008";//分类页-分类详情页的曝光
    public static final String EVENT_XD009= "event_XD009";//分类页-分类详情页的顶部筛选
    public static final String EVENT_XD010 = "event_XD010";//分类页-分类详情页-菜单选项点击


    /********************  详情页 **************************/
    public static final String EVENT_XC001 = "event_XC001";//详情页页面的曝光
    public static final String EVENT_XC002 = "event_XC002";//详情页，展开简介按钮点击
    public static final String EVENT_XC003 = "event_XC003";//详情页，查看目录按钮点击
    public static final String EVENT_XC004 = "event_XC004";//详情页，分享按钮点击
    public static final String EVENT_XC005 = "event_XC005";//详情页，加入书架按钮点击
    public static final String EVENT_XC006 = "event_XC006";//详情页，免费试读按钮点击
    public static final String EVENT_XC007 = "event_XC007";//详情页，听书曝光
    public static final String EVENT_XC008 = "event_XC008";//详情页，听书点击
    public static final String EVENT_XC009 = "event_XC009";//详情页，下载按钮点击
    public static final String EVENT_XC010 = "event_XC010";//详情页，书封右侧包月开通文字链广告曝光
    public static final String EVENT_XC011 = "event_XC011";//详情页，书封右侧包月开通文字链广告点击
    public static final String EVENT_XC012 = "event_XC012";//详情页，外露评论的点击
    public static final String EVENT_XC013 = "event_XC013";//详情页，书评区【更多】按钮点击
    public static final String EVENT_XC014 = "event_XC014";//详情页，外露书评中【赞】按钮的点击
    public static final String EVENT_XC015 = "event_XC015";//详情页，外露书评中【回复】按钮的点击
    public static final String EVENT_XC016 = "event_XC016";//详情页-作家主页的曝光
    public static final String EVENT_XC017 = "event_XC017";//详情页-作家主页的作者名点击
    public static final String EVENT_XC018 = "event_XC018";//详情页新手向导card的曝光
    public static final String EVENT_XC019 = "event_XC019";//详情页新手向导card有曝光时，底部免费阅读/试读 按钮的点击
    public static final String EVENT_XC020 = "event_XC020";//详情页关联推荐card曝光
    public static final String EVENT_XC021 = "event_XC021";//详情页作品亮点card曝光
    public static final String EVENT_XC022 = "event_XC022";//详情页作品亮点card热度标签点击
    public static final String EVENT_XC023 = "event_XC023";//详情页作品亮点card榜单点击
    public static final String EVENT_XC024 = "event_XC024";//详情页作品亮点card更多亮点点击
    public static final String EVENT_XC025 = "event_XC025";//详情页>作品亮点>作品亮点详情页曝光
    public static final String EVENT_XC026 = "event_XC026";//详情页>作品亮点>作品亮点详情页>榜单点击
    public static final String EVENT_XC027 = "event_XC027";//详情页，书封下icon按钮

    /********************  包月vip **************************/
    //包月模块统计
    public static final String EVENT_E050 = "event_E050";//包月页面的曝光
    public static final String EVENT_E051 = "event_E051";//包月页面上方banner的曝光
    public static final String EVENT_E052 = "event_E052";//包月页面上方banner的点击,客户端不支持点击
    public static final String EVENT_E053 = "event_E053";//包月套餐不同套餐的点击
    public static final String EVENT_E054 = "event_E054";//自动续费提示弹窗的曝光
    public static final String EVENT_E055 = "event_E055";//自动续费提示弹窗-到期自动续费开关打开的点击
    public static final String EVENT_E056 = "event_E056";//自动续费提示弹窗-到期自动续费开关关闭的点击
    public static final String EVENT_E057 = "event_E057";//自动续费提示弹窗-仅开通X个月的点击
    public static final String EVENT_E058 = "event_E058";//自动续费提示弹窗-确认开通的点击
    public static final String EVENT_E059 = "event_E059";//自动续费提示弹窗-取消的点击
    public static final String EVENT_E060 = "event_E060";//到期自动续费开关开启的点击
    public static final String EVENT_E061 = "event_E061";//到期自动续费开关关闭的点击
    public static final String EVENT_E062 = "event_E062";//确定关闭自动续费弹窗的曝光
    public static final String EVENT_E063 = "event_E063";//确定关闭自动续费弹窗-确定关闭的点击
    public static final String EVENT_E064 = "event_E064";//确定关闭自动续费弹窗-取消的点击
    public static final String EVENT_E065 = "event_E065";//包月特权card的曝光
    public static final String EVENT_E066 = "event_E066";//包月特权card-详情的点击
    public static final String EVENT_XE064 = "event_XE064";//包月畅读的点击
    public static final String EVENT_XE065 = "event_XE065";//新作抢先的点击
    public static final String EVENT_XE066 = "event_XE066";//签到特权的点击
    public static final String EVENT_XE067 = "event_XE067";//成长加速的点击
    public static final String EVENT_XE068 = "event_XE068";//专享活动的点击
    public static final String EVENT_E072 = "event_E072";//包月特权二级页的曝光，h5上报
    public static final String EVENT_E073 = "event_E073";//包月说明card的曝光
    public static final String EVENT_E074 = "event_E074";//包月说明card-帮助的点击
    public static final String EVENT_D0271 = "event_D0271";//包月专区，【开通包月】按钮曝光
    public static final String EVENT_B311 = "event_B311";//详情页，书封右侧包月开通文字链广告曝光
    public static final String EVENT_B312 = "event_B312";//详情页，书封右侧包月开通文字链广告点击
    public static final String EVENT_D75 = "event_D75";
    public static final String EVENT_D91 = "event_D91";

    /********************  个性主题 **************************/
    public static final String EVENT_A163 = "event_A163";
    public static final String EVENT_A165 = "event_A165";
    public static final String EVENT_A167 = "event_A167";
    public static final String EVENT_A168 = "event_A168";
    public static final String EVENT_A169 = "event_A169";
    public static final String EVENT_A170 = "event_A170";
    public static final String EVENT_A171 = "event_A171";
    public static final String EVENT_A172 = "event_A172";
    public static final String EVENT_A173 = "event_A173";
    public static final String EVENT_A174 = "event_A174";
    public static final String EVENT_A175 = "event_A175";
    public static final String EVENT_A176 = "event_A176";
    public static final String EVENT_A186 = "event_A186";

    /********************  外部广告 **************************/
    public static final String EVENT_XG036 = "event_XG036";//应用推广类广告，开始下载应用时，触发此事件上报
    public static final String EVENT_XG037 = "event_XG037";//开始安装推广应用时，触发此事件上报
    public static final String EVENT_XG038 = "event_XG038";//安装推广应用完成时，触发此事件上报

    /********************  手Q春节红包 **************************/
    public static final String EVENT_XJ038 = "event_XJ038";//推荐页春节红包提现的曝光
    public static final String EVENT_XJ039 = "event_XJ039";//推荐页春节红包提现的点击



    /********************  以下是qq登录和微信登录 **************************/
    public static final String EVENT_QQEXTRA_DO_LOGIN_TYPE = "do_login_type";
    public static final String EVENT_WXEXTRA_DO_LOGIN = "do_login";
    public static final String EVENT_WXEXTRA_GET_CODE = "get_code";
    public static final String EVENT_LOGIN_BY_WX = "event_login_by_wx";
    public static final String EVENT_WXEXTRA_REFRESHTOKEN = "refresh_token";
    public static final String EVENT_WXEXTRA_GETUSERINFO = "get_userinfo";
    public static final String EVENT_WXEXTRA_GET_ACCESSTOKEN = "get_accesstoken";


    public static final String EVENT_PUSH_RECEIVER = "event_push_receiver";
    public static final String EVENT_PUSH_CLICK = "event_push_click";


    /******************** 书架页相关 **************************/
    public static final String EVENT_XA100 = "event_XA100";//签到入口的曝光
    public static final String EVENT_XA101 = "event_XA101";//签到入口的点击
    public static final String EVENT_XA102 = "event_XA102";//签到成功弹窗的曝光
    public static final String EVENT_XA103 = "event_XA103";//签到成功弹窗的“观看广告在得积分”按钮的点击
    public static final String EVENT_XA104 = "event_XA104";//签到成功弹窗的“已观看广告，获得积分”的曝光
    public static final String EVENT_XA105 = "event_XA105";//书架浏览历史的点击
    public static final String EVENT_XA106 = "event_XA106";//福利card的曝光
    public static final String EVENT_XA107 = "event_XA107";//福利card的点击
    public static final String EVENT_XA108 = "event_XA108";//签到card的曝光
    public static final String EVENT_XA109 = "event_XA109";//签到card的点击
    public static final String EVENT_XA110 = "event_XA110";//书架添加按钮的点击（进书城找书）
    public static final String EVENT_XA111 = "event_XA111";//异形广告请求失败量
    public static final String EVENT_XA112 = "event_XA112";//异形广告的曝光
    public static final String EVENT_XA113 = "event_XA113";//异形广告的点击
    public static final String EVENT_XA114 = "event_XA114";//书架阅读时长栏的曝光
    public static final String EVENT_XA115 = "event_XA115";//
    public static final String EVENT_XA116 = "event_XA116";//
    public static final String EVENT_XA117 = "event_XA117";//
    public static final String EVENT_XA118 = "event_XA118";//签到成功动效的曝光
    public static final String EVENT_XA119 = "event_XA119";//新手引导-书籍编辑
    public static final String EVENT_XA120 = "event_XA120";//新手引导-宫格切换
    public static final String EVENT_XA121 = "event_XA121";//书架更多功能的曝光
    public static final String EVENT_XA122 = "event_XA122";//书架切换宫格样式按钮的点击
    public static final String EVENT_XA123 = "event_XA123";//书架切换列表样式按钮的点击
    public static final String EVENT_XA124 = "event_XA124";//书架批量管理按钮的点击
    public static final String EVENT_XA125 = "event_XA125";//单本编辑弹窗的曝光
    public static final String EVENT_XA126 = "event_XA126";//单本编辑详情按钮的点击
    public static final String EVENT_XA127 = "event_XA127";//单本编辑置顶按钮的点击
    public static final String EVENT_XA128 = "event_XA128";//单本编辑删除按钮的点击
    public static final String EVENT_XA129 = "event_XA129";//单本编辑分享按钮的点击
    public static final String EVENT_XA130 = "event_XA130";//单本编辑相似好书按钮的点击
    public static final String EVENT_XA131 = "event_XA131";//单本右滑编辑按钮的点击
    public static final String EVENT_XA132 = "event_XA132";//单本右滑相似好书按钮的点击
    public static final String EVENT_XA133 = "event_XA133";//书架顶部新手特权标识的曝光
    public static final String EVENT_XA134 = "event_XA134";//书架顶部新手特权标识的点击
    public static final String EVENT_XA135 = "event_XA135";//书架未登录用户加书架任务弹窗的曝光
    public static final String EVENT_XA136 = "event_XA136";//书架未登录用户加书架任务弹窗按钮的点击
    public static final String EVENT_XA137 = "event_XA137";//书架未登录用户登录后加书架任务奖励弹窗的曝光
    public static final String EVENT_XA138 = "event_XA138";//书架顶部广告曝光
    public static final String EVENT_XA139 = "event_XA139";//书架顶部广告点击




    /******************** 其他 **************************/
    public static final String EVENT_XG100 = "event_XG100";//福利入口的曝光
    public static final String EVENT_XG101 = "event_XG101";//福利入口的点击
    public static final String EVENT_XG102 = "event_XG102";//deeplink链接的返回按钮的曝光
    public static final String EVENT_XG103 = "event_XG103";//deeplink链接的返回按钮的点击
    public static final String EVENT_XG109 = "event_XG109";//福利tab的点击
    public static final String EVENT_XG110 = "event_XG110";//下载成功状态

    /******************** 我的页 **************************/
    public static final String EVENT_XE104 = "event_XE104";//免广告按钮的点击
    public static final String EVENT_XE105 = "event_XE105";//我的积分"每日福利"按钮的点击
    public static final String EVENT_XE106 = "event_XE106";//我的积分"积分商城"按钮的点击
    public static final String EVENT_XE107 = "event_XE107";//观看视频广告后成功领取积分弹窗的曝光
    public static final String EVENT_XE109 = "event_XE109";//我的游戏中心的曝光
    public static final String EVENT_XE110 = "event_XE110";//我的游戏中心的点击
    public static final String EVENT_XE111 = "event_XE111";//我的-我的页我的积分按钮的点击
    public static final String EVENT_XE112 = "event_XE112";//我的-我的页积分提现按钮的点击
    public static final String EVENT_XE113 = "event_XE113";//我的-我的页banner的曝光
    public static final String EVENT_XE114 = "event_XE114";//我的-我的页banner的点击
    public static final String EVENT_XE115 = "event_XE115";//我的-未登录态的点击
    public static final String EVENT_XE120 = "event_XE120";//我的页--兑换书籍
    public static final String EVENT_XE121 = "event_XE121";//我的-邀请好友
    public static final String EVENT_XE122 = "event_XE122";//我的-我的书友
    public static final String EVENT_XE123 = "event_XE123";//我的-我的消息的曝光
    public static final String EVENT_XE124 = "event_XE124";//我的-我的消息的点击
    public static final String EVENT_XE125 = "event_XE125";//红点的曝光
    public static final String EVENT_XE126 = "event_XE126";//红点的点击

    /******************** 阅读页 **************************/
    public static final String EVENT_XB601 = "event_XB601";//阅读页免广告按钮的点击
    public static final String EVENT_XB602 = "event_XB602";//无券提示弹窗的曝光
    public static final String EVENT_XB603 = "event_XB603";//无券提示弹窗的-积分商城按钮的点击
    public static final String EVENT_XB600 = "event_XB600";//阅读页翻页统计
    public static final String EVENT_XB604 = "event_XB604";//语速加的点击
    public static final String EVENT_XB605 = "event_XB605";//语速减的点击
    public static final String EVENT_XB606 = "event_XB606";//每个声音选择的点击
    public static final String EVENT_XB607 = "event_XB607";//每个定时的点击
    public static final String EVENT_XB608 = "event_XB608";//结束朗读的点击
    public static final String EVENT_XB609 = "event_XB609";//朗读设置的点击
    public static final String EVENT_XB610 = "event_XB610";//无网络条件下toast的曝光
    public static final String EVENT_XB611 = "event_XB611";//移动网络条件下toast的曝光
    public static final String EVENT_XB612 = "event_XB612";//首次使用在线音色时弹窗的曝光
    public static final String EVENT_XB613 = "event_XB613";//首次使用在线音色时弹窗-继续使用此声音的点击
    public static final String EVENT_XB614 = "event_XB614";//首次使用在线音色时弹窗-自动切换至默认声音的点击
    public static final String EVENT_XB615 = "event_XB615";//朗读设置弹窗的曝光
    public static final String EVENT_XB616 = "event_XB616";//朗读设置弹窗-继续使用此声音的点击
    public static final String EVENT_XB617 = "event_XB617";//朗读设置弹窗-自动切换至默认声音的点击
    public static final String EVENT_XB618 = "event_XB618";//朗读设置弹窗-取消的点击
    public static final String EVENT_XB629 = "event_XB629";//登录成功的上报
    public static final String EVENT_XB633 = "event_XB633";//同意协议的点击
    public static final String EVENT_XB634 = "event_XB634";//协议查看点击
    public static final String EVENT_XB635 = "event_XB635";//登录页返回按钮的点击
    public static final String EVENT_XB636 = "event_XB636";//退出阅读页上报
    public static final String EVENT_XB639 = "event_XB639";//阅读尾页成就栏曝光
    public static final String EVENT_XB640 = "event_XB640";//阅读尾页分享我的成就点击
    public static final String EVENT_XB641 = "event_XB641";//阅读尾页回书架按钮点击
    public static final String EVENT_XB642 = "event_XB642";//阅读尾页去书城按钮点击
    public static final String EVENT_XB649 = "event_XB649";//开启听书弹窗的曝光
    public static final String EVENT_XB650 = "event_XB650";//开启听书弹窗确认按钮的点击
    public static final String EVENT_XB651 = "event_XB651";//开启听书弹窗取消按钮的点击
    public static final String EVENT_XB652 = "event_XB652";//开启听书弹窗去赚积分按钮的点击
    public static final String EVENT_XB653 = "event_XB653";//开启听书下载插件按钮的点击
    public static final String EVENT_XB654 = "event_XB654";//阅读页顶部菜单栏的曝光
    public static final String EVENT_XB655 = "event_XB655";//阅读页顶部菜单栏登录引导的点击
    public static final String EVENT_XB656 = "event_XB656";//阅读页顶部菜单栏头像的点击
    public static final String EVENT_XB657 = "event_XB657";//阅读页顶部菜单栏头像的点击后登录赚积分的点击
    public static final String EVENT_XB658 = "event_XB658";//阅读页顶部菜单栏头像的点击后登录启动萌新特权的点击
    public static final String EVENT_XB659 = "event_XB659";//阅读页顶部菜单栏下载本书的曝光
    public static final String EVENT_XB660 = "event_XB660";//阅读页顶部菜单栏下载本书的点击
    public static final String EVENT_XB661 = "event_XB661";//阅读页顶部菜单栏分享书友的点击

    /******************** 单数调起 **************************/
    public static final String EVENT_XG104 = "event_XG104";//首次启动请求单书调起配置量
    public static final String EVENT_XG105 = "event_XG105";//首次启动请求单书请求成功量
    public static final String EVENT_XG106 = "event_XG106";//首次启动请求单书成功调起量
    public static final String EVENT_XG111 = "event_XG111";//首次启动请求单书调起的Qurl成功

    public static final String EVENT_XG107 = "event_XG107";//申请设备号授权人数
    public static final String EVENT_XG108 = "event_XG108";//获取设备号授权失败人数

    /******************** 登录页 **************************/
    public static final String EVENT_XG112 = "event_XG112";//登录页面曝光
    public static final String EVENT_XG113 = "event_XG113";//登录微信登录的点击
    public static final String EVENT_XG114 = "event_XG114";//登录QQ登录的点击
    public static final String EVENT_XG115 = "event_XG115";//登录成功弹窗的曝光
    public static final String EVENT_XG116 = "event_XG116";//登录成功弹窗的去提现按钮的点击
    public static final String EVENT_XG117 = "event_XG117";//登录成功toast的曝光


    /******************** 站内信 **************************/
    public static final String EVENT_XE127 = "event_XE127";//我的消息通知@我列表页的曝光
    public static final String EVENT_XE128 = "event_XE128";//我的消息活动列表页的曝光
    public static final String EVENT_XE129 = "event_XE129";//消息列表页消息的曝光
    public static final String EVENT_XE130 = "event_XE130";//消息列表页消息的点击


    /******************** 插件系统用户数据统计 **************************/
    /**
     * 插件下载按钮的点击
     *
     * 必备参数：时间，qimei
     * 插件分类：（plugin_class）
     * 插件名称：(plugin_name）
     * 插件版本：(plugin_version）
     * 应用版本：appversion
     */
    public static final String EVENT_XE200 = "event_XE200";


    /**
     * 插件启动上报
     *
     * 必备参数：时间，qimei
     * 插件分类：（plugin_class）
     * 插件名称：(plugin_name）
     * 插件版本：(plugin_version）
     * 应用版本：appversion
     */
    public static final String EVENT_XE201 = "event_XE201";

}
