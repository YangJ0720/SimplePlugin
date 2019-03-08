package yangj.simpleplugin.hook;

import android.content.Intent;
import android.text.TextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by YangJ on 2018/11/19.
 */
public class IActivityManagerProxy implements InvocationHandler {

    public static final String EXTRA_PLUGIN = "plugin";

    private Object mActivityManager;

    public IActivityManagerProxy(Object mActivityManager) {
        this.mActivityManager = mActivityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (TextUtils.equals("startActivity", method.getName())) {
            int index = 0;
            Intent intent = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    intent = (Intent) args[i];
                    break;
                }
            }
            Intent manifest = new Intent();
            String packageName = "yangj.simpleplugin";
            manifest.setClassName(packageName, packageName + ".ProxyActivity");
            manifest.putExtra(EXTRA_PLUGIN, intent);
            //
            args[index] = manifest;
        }
        return method.invoke(mActivityManager, args);
    }
}
