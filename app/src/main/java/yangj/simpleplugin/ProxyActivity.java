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
        Resources resources = createResources(createAssetManager(sPath));
        XmlResourceParser parser = resources.getLayout(layoutResID);
        View view = LayoutInflater.from(this).inflate(parser, null);
        super.setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Method method = mProxyClass.getMethod("onResume");
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
            Method method = mProxyClass.getMethod("onDestroy", new Class[]{});
            method.invoke(mProxyActivity, new Object[]{});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    /**
     * 初始化基础数据
     *
     * @param savedInstanceState
     */
    private void initData(Bundle savedInstanceState) {
        Intent data = getIntent();
        String className = data.getStringExtra(CLASS_NAME);
        // initData: className = spt.md5.ui.PluginActivity
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
            methodSetProxy.setAccessible(true);
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
     *
     * @param className
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
