package com.qq.reader.entity.props;

import java.util.List;

/**
 * @author zhanglulu on 2019/5/31.
 * for 下载道具 Model
 */
public class PropsDownloadModel {

    public static final int DOWNLOAD_PROPS_NOT_PROPS_NOT_UNLOCK = 0;//无券未解锁
    public static final int DOWNLOAD_PROPS_YES_PROPS_NOT_UNLOCK = 1;//有券未解锁
    public static final int DOWNLOAD_PROPS_YES_UNLOCK_DOWNLOAD_PART = 2;//已解锁部分下载
    public static final int DOWNLOAD_PROPS_YES_UNLOCK_DOWNLOAD_WHOLE = 3;//已解锁全部下载
    public static final int DOWNLOAD_PROPS_DOWNLOAD_LOADING = 4;//正在下载

    private int state = DOWNLOAD_PROPS_NOT_PROPS_NOT_UNLOCK; //下载道具状态
    private String formatCountdownTime = "";//格式化后的剩余时间
    private boolean expired = true;//是否过期
    private List<Integer> needDownloadChapterIds;//需要下载的章节ID
    private String propsId; //道具ID
    private int unUsePropsCount = 0;//未使用道具个数
    private boolean isLocalData = false;//是否为本地数据，如果是本地数据则认为 countdown 不可靠

    public boolean isLocalData() {
        return isLocalData;
    }

    public void setLocalData(boolean localData) {
        isLocalData = localData;
    }

    public int getUnUsePropsCount() {
        return unUsePropsCount;
    }

    public void setUnUsePropsCount(int unUsePropsCount) {
        this.unUsePropsCount = unUsePropsCount;
    }

    public String getPropsId() {
        return propsId;
    }

    public void setPropsId(String propsId) {
        this.propsId = propsId;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFormatCountdownTime() {
        return formatCountdownTime;
    }

    public void setFormatCountdownTime(String formatCountdownTime) {
        this.formatCountdownTime = formatCountdownTime;
    }

    public List<Integer> getNeedDownloadChapterIds() {
        return needDownloadChapterIds;
    }

    public void setNeedDownloadChapterIds(List<Integer> needDownloadChapterIds) {
        this.needDownloadChapterIds = needDownloadChapterIds;
    }

    @Override
    public String toString() {
        int needDownloadChapterIdsSize = 0;
        if (needDownloadChapterIds != null) {
            needDownloadChapterIdsSize = needDownloadChapterIds.size();
        }
        return "PropsDownloadModel{" +
                "state=" + state +
                ", formatCountdownTime='" + formatCountdownTime + '\'' +
                ", expired=" + expired +
                ", needDownloadChapterIdsSize=" + needDownloadChapterIdsSize +
                ", propsId='" + propsId + '\'' +
                ", unUsePropsCount=" + unUsePropsCount +
                ", isLocalData=" + isLocalData +
                '}';
    }
}
