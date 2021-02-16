package com.lulu.basic.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qq.reader.module.bookstore.qweb.fragment.R
import kotlinx.android.synthetic.main.progress_bar.*

/**
 * @author zhanglulu
 */
public class ProgressDialogFragment: BaseDialogFragment() {

    private var msg = "正在加载"

    init {
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_bar, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMsg.text = msg
    }

    public fun setMsg(msg: String) {
        this.msg = msg
    }

}