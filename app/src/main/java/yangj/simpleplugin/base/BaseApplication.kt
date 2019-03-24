package yangj.simpleplugin.base

import android.app.Application
import yangj.simpleplugin.ProxyActivity
import yangj.simpleplugin.utils.FileUtils
import yangj.simpleplugin.utils.HookUtils
import java.io.File

/**
 * Created by YangJ on 2018/11/16.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        // 插件apk文件名称
        val fileName = "plugin-release.apk"
        // 将插件APK拷贝到指定目录
        ProxyActivity.sPath = File(externalCacheDir, fileName).absolutePath
        FileUtils.copy(this, fileName, ProxyActivity.sPath)
        // Hook AMS
        HookUtils.hookAMS()
        HookUtils.hookHandler()
    }

}