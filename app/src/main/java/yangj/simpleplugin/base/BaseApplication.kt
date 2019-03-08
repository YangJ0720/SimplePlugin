package yangj.simpleplugin.base

import android.app.Application
import yangj.simpleplugin.constant.Constants
import yangj.simpleplugin.utils.FileUtils
import yangj.simpleplugin.utils.HookUtils
import java.io.File

/**
 * Created by YangJ on 2018/11/16.
 */
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        // 将插件APK拷贝到指定目录
        val path = File(externalCacheDir, Constants.PLUGIN_FILE_NAME).absolutePath
        FileUtils.copy(this, Constants.PLUGIN_FILE_NAME, path)
        // hook ActivityManagerService
        HookUtils.hookAMS()
        HookUtils.hookHandler()
    }

}