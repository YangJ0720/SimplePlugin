package yangj.simpleplugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author YangJ
 */
public class ProxyActivity extends AppCompatActivity {

    public static final String CLASS_NAME = "className";
    /**
     * 插件apk文件缓存路径
     */
    public static String sPath;

    private Class mProxyClass;
    private Object mProxyActivity;

    private static final String TAG = "ProxyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        // 加载插件apk文件中的布局文件
        Resources resources = createResources(createAssetManager(sPath));
        XmlResourceParser parser = resources.getLayout(layoutResID);
        View view = LayoutInflater.from(this).inflate(parser, null);
        // 执行setContentView方法
        super.setContentView(view);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            Method method = mProxyClass.getMethod("onRestart");
            Log.i(TAG, "onRestart: " + method);
            method.invoke(mProxyActivity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Method method = mProxyClass.getMethod("onStart");
            Log.i(TAG, "onStart: " + method);
            method.invoke(mProxyActivity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Method method = mProxyClass.getMethod("onResume");
            Log.i(TAG, "onResume: " + method);
            method.invoke(mProxyActivity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Method method = mProxyClass.getMethod("onPause");
            Log.i(TAG, "onPause: " + method);
            method.invoke(mProxyActivity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            Method method = mProxyClass.getMethod("onStop");
            Log.i(TAG, "onStop: " + method);
            method.invoke(mProxyActivity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Method method = mProxyClass.getMethod("onDestroy");
            Log.i(TAG, "onDestroy: " + method);
            method.invoke(mProxyActivity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化基础数据
     *
     * @param savedInstanceState
     */
    private void initData(Bundle savedInstanceState) {
        // 获取
        Intent data = getIntent();
        String className = data.getStringExtra(CLASS_NAME);
        Log.i(TAG, "initData: className = " + className);
        DexClassLoader dexClassLoader = new DexClassLoader(sPath,
                getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath(),
                null, getClassLoader());
        Log.i(TAG, "dexClassLoader: " + dexClassLoader);
        try {
            // 获取要启动的插件Activity，例如：ShowActivity
            mProxyClass = dexClassLoader.loadClass(className);
            Log.i(TAG, "mProxyClass: " + mProxyClass);
            Constructor constructor = mProxyClass.getConstructor(new Class[]{});
            // 实例化要启动的插件Activity
            mProxyActivity = constructor.newInstance(new Object[]{});
            Log.i(TAG, "mProxyActivity: " + mProxyActivity);
            // 设置代理
            Method methodSetProxy = mProxyClass.getMethod("setProxy", new Class[]{Activity.class});
            Log.i(TAG, "initData: methodSetProxy = " + methodSetProxy);
            methodSetProxy.invoke(mProxyActivity, new Object[]{this});
            // 反射onCreate
            Method methodOnCreate = mProxyClass.getMethod("onCreate",
                    new Class[]{Bundle.class});
            Log.i(TAG, "initData: methodOnCreate = " + methodOnCreate);
            methodOnCreate.invoke(mProxyActivity, new Object[]{savedInstanceState});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动一个插件中的Activity
     * <br>该方法用于提供插件APK中的Activity反射调用，可以启动另外的插件Activity<br/>
     *
     * @param intent    参数为intent
     * @param className 参数为插件APK文件中的Activity名称
     */
    public void startActivityPlugin(Intent intent, String className) {
        intent.setClass(this, ProxyActivity.class);
        intent.putExtra(CLASS_NAME, className);
        startActivity(intent);
    }

    /**
     * 启动一个插件中的Activity
     *
     * @param context   参数为当前上下文对象
     * @param className 参数为插件APK文件中的Activity名称
     */
    public static void startActivityPlugin(Context context, String className) {
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra(CLASS_NAME, className);
        context.startActivity(intent);
    }

    private AssetManager createAssetManager(String apkPluginPath) {
        Class<AssetManager> cls = AssetManager.class;
        AssetManager assets = null;
        try {
            assets = cls.newInstance();
            Method method = cls.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assets, apkPluginPath);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return assets;
    }

    private Resources createResources(AssetManager assets) {
        return new Resources(assets, getResources().getDisplayMetrics(),
                getResources().getConfiguration());
    }

}
