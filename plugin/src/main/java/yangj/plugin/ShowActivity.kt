package yangj.plugin

import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_show.*
import yangj.plugin.base.BaseActivity

/**
 * @author YangJ
 */
class ShowActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        val item = intent.getStringExtra("item")
        textView.text = if (TextUtils.isEmpty(item)) {
            "item is null"
        } else {
            item
        }
    }
}
