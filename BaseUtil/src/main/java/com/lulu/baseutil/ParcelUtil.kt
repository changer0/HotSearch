package com.lulu.baseutil

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.io.*

/**
 * Author: zhanglulu
 * Time: 2021/2/17
 */
object ParcelUtil {
    @JvmStatic
    fun marshall(parceable: Parcelable): ByteArray {
        val parcel = Parcel.obtain()
        parcel.setDataPosition(0)
        parceable.writeToParcel(parcel, 0)
        val bytes = parcel.marshall()
        Log.d("ParcelUtil", "bytes = " + String(bytes) + "parcel" + parcel.toString())
        parcel.recycle()
        return bytes
    }

    @JvmStatic
    fun unMarshall(bytes: ByteArray): Parcel {
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        return parcel
    }

    @JvmStatic
    fun saveParcel(parcelable: Parcelable?, filePath: String?) {
        if (parcelable == null) {
            return
        }

        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            val file = File(filePath)
            if (file.exists()) {
                file.delete()
            }
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            oos = ObjectOutputStream(bos)
            oos.write(marshall(parcelable))
            oos.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                oos?.close()
                bos?.close()
                fos?.close()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 读取
     * T.CREATOR.createFromParcel(parcel)
     */
    public fun readParcel(filePath: String): Parcel? {
        var fis : FileInputStream? = null
        var bis : BufferedInputStream? = null
        var ois : ObjectInputStream? = null
        try {
            fis = FileInputStream(filePath)
            bis = BufferedInputStream(fis)
            ois = ObjectInputStream(bis)
            val bytes = ois.readBytes()
            //mark = T.CREATOR.createFromParcel(parcel)
            return unMarshall(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                ois?.close()
                bis?.close()
                fis?.close()
            } catch (e: Exception) {}
        }
        return null
    }
}