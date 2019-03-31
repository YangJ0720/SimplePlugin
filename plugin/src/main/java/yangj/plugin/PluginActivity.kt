package yangj.plugin

import android.graphics.BitmapFactory
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
        val resId = R.drawable.a0a
        println("resId = $resId")
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        println("bitmap = $bitmap")
        imageView.setImageBitmap(bitmap)
        tvPlugin.text = getString(R.string.app_name)
        button.setOnClickListener {
            val text = resources.getString(R.string.app_name)
            Toast.makeText(mProxyActivity, text, Toast.LENGTH_SHORT).show()
        }
    }
}
