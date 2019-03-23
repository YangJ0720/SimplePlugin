package yangj.simpleplugin.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * 文件操作工具类
 * Created by YangJ on 2018/11/16.
 */
object FileUtils {

    /**
     * 判断文件是否存在
     * @param path 参数为文件路径
     */
    fun exists(path: String): Boolean {
        return File(path).exists()
    }

    /**
     * 拷贝文件到指定路径
     * @param context 参数为当前上下文对象
     * @param fileName 参数为插件apk文件名称
     * @param path 参数为插件apk文件缓存路径
     */
    fun copy(context: Context, fileName: String, path: String): Boolean? {
        // 判断文件是否存在指定路径
        if (exists(path)) return true
        // 开始拷贝
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            inputStream = context.assets.open(fileName)
            outputStream = FileOutputStream(path)
            var len: Int
            val buffer = ByteArray(1024)
            while (true) {
                len = inputStream!!.read(buffer)
                if (len == -1) {
                    break
                }
                outputStream.write(buffer, 0, len)
            }
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return false
    }

}