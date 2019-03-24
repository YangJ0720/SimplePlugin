package yangj.plugin;

import android.os.Bundle;
import android.util.Log;

import yangj.plugin.base.BaseActivity;

/**
 * @author YangJ
 */
public class LoginActivity extends BaseActivity {

    // TAG
    private static final String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate");
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {

    }

}