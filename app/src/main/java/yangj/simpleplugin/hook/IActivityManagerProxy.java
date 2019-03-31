package yangj.simpleplugin.hook;

import android.content.Intent;
import android.text.TextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Hook IActivityManager
 * Created by YangJ on 2018/11/19.
 */
public class IActivityManagerProxy implements InvocationHandler {

    public static final String EXTRA_PLUGIN = "plugin";

    private Object mActivityManager;

    // TAG
    private static final String TAG = "IActivityManagerProxy";

    public IActivityManagerProxy(Object mActivityManager) {
        this.mActivityManager = mActivityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = method.getName();
        if (TextUtils.equals("startActivity", methodName)) {
            int index = 0;
            Intent intent = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    intent = (Intent) args[i];
                    break;
                }
            }
            // 启动manifest文件中定义的Activity（此处可以优化）
            Intent manifest = (Intent) intent.clone();
            String packageName = "yangj.simpleplugin";
            String className = packageName + ".MainActivity";
            manifest.setClassName(packageName, className);
            // 将需要启动但是又没有在manifest中定义的Activity添加到intent
            manifest.putExtra(EXTRA_PLUGIN, intent);
            // 替换掉intent这就相当于通知AMS启动一个正常注册的Activity
            args[index] = manifest;
        }
        return method.invoke(mActivityManager, args);
    }
}