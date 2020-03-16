package com.qq.reader.adv;

import android.os.Bundle;

/**
 * Created by menwentong on 2017/6/26.
 */

public class JumpAction {


    private Bundle mActionParams = null;

    public JumpAction(){
        this(null);
    }

    public JumpAction(Bundle b) {
        if (b != null) {
            mActionParams = b;
        } else {
            mActionParams = new Bundle();
        }
    }

    public synchronized Bundle getActionParams() {
        return mActionParams;
    }


}
