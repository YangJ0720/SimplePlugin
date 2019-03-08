package yangj.simpleplugin.base

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import yangj.simpleplugin.hook.InstrumentationProxy

/**
 * Created by YangJ on 2018/11/16.
 */
abstract class BaseActivity : AppCompatActivity() {

    private var isReplace = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initData()
        initView()
    }

    abstract fun getLayoutResId(): Int

    abstract fun initData()

    abstract fun initView()

    /**
     * 启动插件中的Activity
     */
    protected fun startActivityPlugin(intent: Intent) {
        replaceActivityInstrumentation()
        startActivity(intent)
    }

    /**
     * hook -> activity -> mInstrumentation
     */
    private fun replaceActivityInstrumentation() {
        if (isReplace) return
        // 替换Instrumentation
        val field = Activity::class.java.getDeclaredField("mInstrumentation")
        field.isAccessible = true
        val obj = field.get(this) as Instrumentation
        field.set(this, InstrumentationProxy(obj))
        //
        isReplace = true
    }

}