package com.yuewen.component.router.constant;

/**
 * Created by ronaldo on 2019/4/12.
 */

public class YWRouterConstant {
    public static final String PARAM_ROUTER_ACTION = ".receiver.ComponentRouterReceiver";
    public static final String PARAM_ROUTER_PATH = "param_router_path";

    public static class ResultCode {
        public static final int RESULT_OK = 0;
        // 服务找不到
        public static final int RESULT_SERVICE_NOT_FOUND = -1;
        // 方法找不到
        public static final int RESULT_METHOD_NOT_FOUND = -2;
        // 参数错误
        public static final int RESULT_ARGUMENT_ERROR = -3;
        // 网络错误
        public static final int RESULT_NET_ERROR = -4;
        // 数据错误
        public static final int RESULT_DATA_ERROR = -5;
    }

    public static class ResultMsg {
        public static final String RESULT_OK = "结果正常";
        // 服务找不到
        public static final String RESULT_SERVICE_NOT_FOUND = "服务找不到";
        // 方法找不到
        public static final String RESULT_METHOD_NOT_FOUND = "方法找不到";
        // 参数错误
        public static final String RESULT_ARGUMENT_ERROR = "参数错误";
        // 网络错误
        public static final String RESULT_NET_ERROR = "网络错误";
        // 数据错误
        public static final String RESULT_DATA_ERROR = "数据错误";
    }
}
