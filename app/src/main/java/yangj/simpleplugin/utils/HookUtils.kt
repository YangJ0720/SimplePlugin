package yangj.simpleplugin.utils

import android.os.Build
import android.os.Handler
import yangj.simpleplugin.hook.HCallback
import yangj.simpleplugin.hook.IActivityManagerProxy
import java.lang.reflect.Proxy

/**
 * Created by YangJ on 2018/11/19.
 */
object HookUtils {

    fun hookAMS() {
        var singleton: Any? = null
        singleton = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val cls = "android.app.ActivityManager"
            FieldUtils.getObject(cls, null, "IActivityManagerSingleton")
        } else {
            val cls = "android.app.ActivityManagerNative"
            FieldUtils.getObject(cls, null, "gDefault")
        }
        val field = FieldUtils.getField("android.util.Singleton", "mInstance")
        // 获取iActivityManager
        val obj = field.get(singleton)
        //
        val clazz = Class.forName("android.app.IActivityManager")
        val proxy = Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz),
                IActivityManagerProxy(obj))
        field.set(singleton, proxy)
    }

    fun hookHandler() {
        val cls = Class.forName("android.app.ActivityThread")
        val mH = FieldUtils.getField(cls, "mH")
        //
        val name = "sCurrentActivityThread"
        val obj = FieldUtils.getObject(cls, null, name)
        val handler = mH.get(obj) as Handler
        FieldUtils.setField(Handler::class.java, handler, "mCallback", HCallback(handler))
    }

}