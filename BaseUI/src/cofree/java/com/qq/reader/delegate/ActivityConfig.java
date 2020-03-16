
package com.qq.reader.delegate;

public interface ActivityConfig {

    public static final int STATUS_DEFAULT = 0;
    public static final int STATUS_IMMERSE = 1;
    public static final int STATUS_TRANSLUCENT = 2;
    public static final int DEFAULT_STATUS_TYPE = STATUS_IMMERSE;

    boolean isHomeAsUpEnabled();
//    boolean isTitleNeedUpdate();
//    boolean isShowMenuDescription();
    int getStatusType();
}
