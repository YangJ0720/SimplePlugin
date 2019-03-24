package yangj.plugin.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

/**
 * @author YangJ
 * Created by YangJ on 2017/11/1.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 宿主APK中的代理Activity
     */
    protected Activity mProxyActivity;

    // TAG
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        Log.i(TAG, "onCreate: savedInstanceState = " + savedInstanceState);
    }

    @Override
    public void onRestart() {
        Log.i(TAG, "onRestart");
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
    }

    /**
     * 设置代理Activity
     * <br>这个方法由宿主APK反射调用<br/>
     *
     * @param activity 参数为宿主APK中的代理Activity
     */
    public void setProxy(Activity activity) {
        mProxyActivity = activity;
        Log.e(TAG, "setProxy: activity = " + activity);
    }

    @Override
    public void setContentView(int layoutResID) {
        Log.e(TAG, "setContentView: layoutResID = " + layoutResID);
        Log.e(TAG, "setContentView: mProxyActivity = " + mProxyActivity);
        // 设置布局文件
        mProxyActivity.setContentView(layoutResID);
    }

    @Nullable
    @Override
    public View findViewById(int id) {
        // 根据控件id获取控件对象实例
        return mProxyActivity.findViewById(id);
    }

    /**
     * 启动插件中的Activity
     *
     * @param className
     */
    public void startActivity(Intent intent, String className) {
        Log.i(TAG, "startActivity: className = " + className);
        Class cls = mProxyActivity.getClass();
        Log.i(TAG, "startActivity: cls = " + cls);
        try {
            Method method = cls.getMethod("startActivityPlugin", Intent.class, String.class);
            Log.i(TAG, "startActivity: method = " + method);
            method.invoke(mProxyActivity, intent, className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
