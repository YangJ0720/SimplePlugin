package yangj.simpleplugin.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils

/**
 * Created by YangJ on 2018/11/16.
 */
object PluginUtils {

    /**
     * 获取插件apk的PackageInfo
     */
    private fun getPluginApkPackageInfo(context: Context, path: String): PackageInfo {
        val manager = context.packageManager
        return manager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
    }

    /**
     * 获取插件apk的Activity
     */
    fun getPluginApkPackageActivity(context: Context, path: String, name: String): String? {
        val info = getPluginApkPackageInfo(context, path)
        return getPluginApkPackageActivity(info, name)
    }

    /**
     * 获取插件apk的Activity
     */
    private fun getPluginApkPackageActivity(info: PackageInfo, name: String): String? {
        val activities = info.activities
        activities?.forEach { it ->
            if (TextUtils.equals(it.name, name)) {
                return name
            }
        }
        return null
    }

}