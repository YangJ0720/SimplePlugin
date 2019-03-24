package yangj.plugin

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show.*
import yangj.plugin.base.BaseActivity

class PluginActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        initView()
    }

    private fun initView() {
        textView.text = "插件"
    }
}
