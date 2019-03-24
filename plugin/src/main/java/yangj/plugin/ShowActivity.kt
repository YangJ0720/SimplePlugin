package yangj.plugin

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show.*
import yangj.plugin.base.BaseActivity

class ShowActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        initView()
    }

    private fun initView() {
        if (intent == null) {
            textView.text = "intent is null"
        } else {
            textView.text = intent.getStringExtra("item")
        }
    }
}
