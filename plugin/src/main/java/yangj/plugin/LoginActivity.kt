package yangj.plugin

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import yangj.plugin.base.BaseActivity

/**
 * @author YangJ
 */
class LoginActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        username.hint = resources.getString(R.string.user_name)
        password.hint = resources.getString(R.string.pass_word)
        login.text = resources.getString(R.string.login)
    }

}