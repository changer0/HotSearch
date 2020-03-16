package com.qq.reader.entity.props;

/**
 * @author zhanglulu on 2019/6/4.
 * for 章节信息
 */
public class ChapterInfo {

    public ChapterInfo(String path, int id) {
        this.path = path;
        this.id = id;
    }

    private String path;
    private int id;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
