package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.qq.reader.common.monitor.Node;
import com.qq.reader.dispatch.R;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;
import com.qq.reader.utils.Utility;
import com.tencent.mars.xlog.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfCommont extends URLServer {
    private final String SERVER_ACTION_DETAIL = "detail";
    private final String SERVER_ACTION_INDEX = "index";
    private final String SERVER_ACTION_ADDCOMMENT = "addcomment";
    private final String SERVER_ACTION_INDEXFORCOMMONZONE = "indexforcommonzone";
    private final String SERVER_ACTION_ZONELIST = "zonelist";
    private final String SERVER_ACTION_CHAPTER = "chapter";


    public URLServerOfCommont(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_DETAIL);
        matchServerActionPool.add(SERVER_ACTION_INDEX);
        matchServerActionPool.add(SERVER_ACTION_ADDCOMMENT);
        matchServerActionPool.add(SERVER_ACTION_INDEXFORCOMMONZONE);
        matchServerActionPool.add(SERVER_ACTION_ZONELIST);
        matchServerActionPool.add(SERVER_ACTION_CHAPTER);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_DETAIL:
                goCommentDetail();
                return true;
            case SERVER_ACTION_INDEX:
                goCommentIndex();
                return true;
            case SERVER_ACTION_INDEXFORCOMMONZONE:
                goCommentIndexForCommonzone();
                return true;
            case SERVER_ACTION_ADDCOMMENT:
                goAddComment();
                return true;
            case SERVER_ACTION_ZONELIST:
                goZonelist();
                return true;
            case SERVER_ACTION_CHAPTER:
                goChapterComment();
                return true;
        }
        return false;
    }

    public void goZonelist() {
        JumpActivityUtil.goCommentSquare(getBindActivity(), null, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).setQurl(getDataQURL()));

    }

    public void goAddComment() {
        if (getParameterMap() != null) {
            String bid = getParameterMap().get("bid");
            JumpActivityUtil.goWriteComment_CommentList(getBindActivity(), Long.valueOf(bid), getJumpActivityParameter().setRequestCode(RouteConstant.ACTIVITY_REQUESTCODE_SEND_COMMENT).setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).setQurl(getDataQURL()));
        }
    }

    public void goCommentIndexForCommonzone() {
        if (getParameterMap() != null) {
            String bid = getParameterMap().get("bid");
            String cType = getParameterMap().get("ctype");
            String title = null;
            if(bid.equals("1")){
                title = Utility.getStringById(R.string.book_famine_and_mutual_aid);
            }else if(bid.equals("2")){
                title = Utility.getStringById(R.string.original_space);
            }else if(bid.equals("3")){
                title = Utility.getStringById(R.string.manito_salon);
            }
            JumpActivityUtil.goOfficialClassifyComment(getBindActivity(), Long.valueOf(bid), title, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).setQurl(getDataQURL()));
        }
    }

    /**
     * 书评区主页
     */
    public void goCommentIndex() {
        if (getParameterMap() != null) {
            String bid = getParameterMap().get("bid");
            String cType = getParameterMap().get("ctype");

            JumpActivityUtil.goBookComment(getBindActivity(), Long.valueOf(bid), null, Integer.valueOf(cType), getJumpActivityParameter().setQurl(getDataQURL()));
        }
    }

    /**
     * 书评详情页
     */
    public void goCommentDetail() {
        if (getParameterMap() != null) {
            String commentId = getParameterMap().get("commentid");
            String cType = getParameterMap().get("ctype");
            String bid = getParameterMap().get("bid");
            String authorid = getParameterMap().get("authorid");
            String itemid = getParameterMap().get("itemid");
            String alg = getParameterMap().get("alg");

            //激活恢复框
            boolean isActiveReplyLayout = true;

            //弹出输入法
            boolean isShowKeyboard = false;


            int from = 0;
            try {
                from = Integer.valueOf(getParameterMap().get("from"));
            }catch (Exception e){
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont",e.getMessage());
            }
            int index = RouteConstant.FLOOR_SOFA,next = RouteConstant.FLOOR_NEXT_UP_VALUE;
            boolean islocate = false;
            try {
                index = Integer.valueOf(getParameterMap().get("index"));
            }catch (Exception e){
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont",e.getMessage());
            }
            try {
                next = Integer.valueOf(getParameterMap().get("next"));
            }catch (Exception e){
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont",e.getMessage());
            }
            try {
                islocate = Integer.valueOf(getParameterMap().get(RouteConstant.FLOOR_ISLOCATE)) == 1;
            }catch (Exception e){
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont",e.getMessage());
            }
            try {
                if (!TextUtils.isEmpty(getParameterMap().get(RouteConstant.ACTIVE_REPLY_LAYOUT))) {
                    isActiveReplyLayout = Boolean.valueOf(getParameterMap().get(RouteConstant.ACTIVE_REPLY_LAYOUT));
                } else {
                    isActiveReplyLayout = true;
                }
            } catch (Exception e) {
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont", e.getMessage());
            }

            try {
                if (!TextUtils.isEmpty(getParameterMap().get(RouteConstant.SHOW_KEYBOARD))) {
                    isShowKeyboard = Boolean.valueOf(getParameterMap().get(RouteConstant.SHOW_KEYBOARD));
                } else {
                    isShowKeyboard = false;
                }
            } catch (Exception e) {
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont", e.getMessage());
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put(Node.KEY_S_ITEMID, itemid);
            map.put(Node.KEY_S_ALG, alg);
            getJumpActivityParameter().setJsonParamater(map);
            JumpActivityUtil.goBookCommentDetail(getBindActivity(), Long.valueOf(bid), commentId, Integer.valueOf(cType), authorid, index, next, islocate,from, isActiveReplyLayout, isShowKeyboard, getJumpActivityParameter());
        }
    }

    /**
     * 章评详情页
     */
    public void goChapterComment() {
        if (getParameterMap() != null) {

            String chapterId = getParameterMap().get("chapterid");
            String chapterUuid = getParameterMap().get("chapterUuid");//同步主线655 按照服务端要求新增对章节uu
            String bid = getParameterMap().get("bid");
            int index = RouteConstant.FLOOR_SOFA;
            try {
                index = Integer.valueOf(getParameterMap().get("index"));
            }catch (Exception e){
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont",e.getMessage());
            }
            int next = RouteConstant.FLOOR_NEXT_UP_VALUE;
            try {
                next = Integer.valueOf(getParameterMap().get("next"));
            }catch (Exception e){
                Log.printErrStackTrace("URLServerOfCommont", e, null, null);
                Log.e("URLServerOfCommont",e.getMessage());
            }

            JumpActivityUtil.goSingleChapterComment(getBindActivity(), bid, chapterId, chapterUuid, index, next, getJumpActivityParameter());
        }
    }
}
