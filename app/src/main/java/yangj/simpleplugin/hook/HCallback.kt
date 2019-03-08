package yangj.simpleplugin.hook

import android.content.Intent
import android.os.Handler
import android.os.Message
import yangj.simpleplugin.utils.FieldUtils

const val LAUNCH_ACTIVITY = 100

/**
 * Created by YangJ on 2018/11/19.
 */
class HCallback : Handler.Callback {

    private var mHandler: Handler? = null

    constructor(mHandler: Handler?) {
        this.mHandler = mHandler
    }

    override fun handleMessage(msg: Message?): Boolean {
        if (LAUNCH_ACTIVITY == msg?.what) {
            val obj = msg?.obj
            val intent = FieldUtils.getObject(obj::class.java, obj, "intent") as Intent
            val plugin = intent.getParcelableExtra<Intent>(IActivityManagerProxy.EXTRA_PLUGIN)
            if (plugin != null) {
                intent.component = plugin.component
            }
            mHandler?.handleMessage(msg)
        }
        return true
    }

}