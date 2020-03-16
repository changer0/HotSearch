package com.qq.reader.core.view.toast;

import androidx.annotation.NonNull;
import android.widget.Toast;

/**
 * ReaderToast 内部使用
 * toast 内部监听.
 *
 * @author p_bbinliu
 * @date 2019-10-23
 */
public interface BadTokenListener {

    void onBadTokenCaught(@NonNull Toast toast);
}
