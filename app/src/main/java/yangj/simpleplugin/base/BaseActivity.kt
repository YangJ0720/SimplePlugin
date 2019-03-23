package yangj.simpleplugin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by YangJ on 2018/11/16.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initData()
        initView()
    }

    abstract fun getLayoutResId(): Int

    abstract fun initData()

    abstract fun initView()

}