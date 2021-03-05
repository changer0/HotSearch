package com.lulu.hotsearch.utils

import android.net.Uri
import androidx.core.content.FileProvider
import com.lulu.baseutil.Init
import com.lulu.hotsearch.BuildConfig
import java.io.File

object FileProviderUtil {

    //文件共享鉴权
    private const val FILE_PROVIDER_AUTHORITIES = BuildConfig.PACKAGE_NAME + ".provider"

    fun getFileUri(file: File): Uri {
        return FileProvider.getUriForFile(Init.context, FILE_PROVIDER_AUTHORITIES, file)
    }
}