package yangj.plugin

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_plugin.*
import yangj.plugin.base.BaseActivity

/**
 * @author YangJ
 */
class PluginActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        tvPlugin.text = "插件"
        button.setOnClickListener {
            Toast.makeText(mProxyActivity, "插件", Toast.LENGTH_SHORT).show()
        }
    }
}
