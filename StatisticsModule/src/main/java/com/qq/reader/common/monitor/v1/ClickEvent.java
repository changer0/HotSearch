package com.qq.reader.common.monitor.v1;

import com.qq.reader.common.monitor.EventNames;

/**
 * Created by liuxiaoyang on 2017/10/18.
 */

public class ClickEvent extends StatEvent {

    private ClickEvent(StatEvent.Builder builder) {
        super(builder);
    }

    public static class Builder extends StatEvent.Builder {
        public Builder(String _pageName) {
            super(EventNames.EVENT_CLICK, _pageName);
        }

        public Builder(PageInfo pageInfo) {
            super(EventNames.EVENT_CLICK, pageInfo);
        }

        private Builder(String _eventName, String _pageName) {
            super(_eventName, _pageName);
        }

        public ClickEvent build() {
            return new ClickEvent(this);
        }
    }
}
