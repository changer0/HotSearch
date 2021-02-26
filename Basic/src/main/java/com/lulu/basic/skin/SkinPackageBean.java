package com.lulu.basic.skin;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lulu.basic.bean.BaseBean;

/**
 * @author zhanglulu
 */
@Entity(tableName = "skin_package_entity")
public class SkinPackageBean extends BaseBean {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String title;
    private String downloadUrl;
    private int version;
    private boolean isUpdate;
    private boolean hasLocalFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isHasLocalFile() {
        return hasLocalFile;
    }

    public void setHasLocalFile(boolean hasLocalFile) {
        this.hasLocalFile = hasLocalFile;
    }
}
