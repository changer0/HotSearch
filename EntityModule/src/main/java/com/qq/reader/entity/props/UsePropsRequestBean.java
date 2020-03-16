package com.qq.reader.entity.props;

import com.qq.reader.common.gsonbean.BaseBean;

import java.util.List;

/**
 * @author zhanglulu on 2019/6/6.
 * for 使用道具的请求bean
 *
 * {
 *     "propsIds":[],
 *     "tagertId":"1"//书籍id或广告id或章节id
 * }
 */
public class UsePropsRequestBean extends BaseBean {
    private List<String> propsIds;
    private String tagertId;
    //道具类型：免广告劵1,//下载劵2
    private int type;


    public UsePropsRequestBean(List<String> propsIds, String tagertId, int type) {
        this.propsIds = propsIds;
        this.tagertId = tagertId;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getPropsIds() {
        return propsIds;
    }

    public void setPropsIds(List<String> propsIds) {
        this.propsIds = propsIds;
    }

    public String getTagertId() {
        return tagertId;
    }

    public void setTagertId(String tagertId) {
        this.tagertId = tagertId;
    }
}
