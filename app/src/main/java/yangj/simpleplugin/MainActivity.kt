package yangj.simpleplugin

import android.content.Context
import android.content.Intent
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import yangj.simpleplugin.base.BaseActivity
import yangj.simpleplugin.constant.Constants
import yangj.simpleplugin.utils.PluginUtils
import java.io.File

/**
 * @author YangJ
 */
class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        val path = File(externalCacheDir, Constants.PLUGIN_FILE_NAME).absolutePath
        val name = "yangj.simpleplugin.GameActivity"
        val result = PluginUtils.getPluginApkPackageActivity(this@MainActivity, path, name)
        println("result = $result")
    }

    override fun initView() {
        button.setOnClickListener {
            // 启动插件中的Activity
            val path = File(externalCacheDir, Constants.PLUGIN_FILE_NAME).absolutePath
            val optimizedDirectory = getDir("plugin", Context.MODE_PRIVATE).absolutePath
            val dexClassLoader = DexClassLoader(path, optimizedDirectory, null,
                    classLoader)
            val cls = dexClassLoader.loadClass("yangj.simpleplugin.GameActivity")
            println("cls = $cls")
            val obj = cls.newInstance()
            println("obj = $obj")
            startActivity(Intent(this, cls))
        }
    }
}
