package com.qq.reader.dispatch;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by hexiaole on 2020/1/20.
 */
public interface IEventListener {
    void doFunction(Bundle b);

    Activity getFromActivity();
}
