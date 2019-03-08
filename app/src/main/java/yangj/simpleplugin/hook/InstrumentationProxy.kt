package yangj.simpleplugin.hook

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

/**
 * Created by YangJ on 2018/11/16.
 */
class InstrumentationProxy : Instrumentation {

    private var mInstrumentation: Instrumentation? = null

    constructor(instrumentation: Instrumentation) {
        mInstrumentation = instrumentation
    }

    fun execStartActivity(who: Context, contextThread: IBinder, token: IBinder, target: Activity,
                          intent: Intent, requestCode: Int, options: Bundle?): ActivityResult? {
        println("<- InstrumentationProxy ->")
        val method = Instrumentation::class.java.getDeclaredMethod("execStartActivity",
                Context::class.java, IBinder::class.java, IBinder::class.java,
                Activity::class.java, Intent::class.java, Int::class.java, Bundle::class.java)
        val result = method.invoke(mInstrumentation, who, contextThread, token, target, intent,
                requestCode, options) ?: return null
        println("result = $result")
        println("<- InstrumentationProxy ->")
        // 返回ActivityResult
        return result as ActivityResult
    }

}