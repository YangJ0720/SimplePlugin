package yangj.simpleplugin

import android.content.pm.PackageManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import yangj.simpleplugin.base.BaseActivity

/**
 * @author YangJ
 */
class MainActivity : BaseActivity() {

    private lateinit var mAdapter: ArrayAdapter<String>

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        val data = getApkPluginPackageActivity(ProxyActivity.sPath)
        mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
    }

    override fun initView() {
        listView.adapter = mAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // 点击item启动插件中对应的Activity
            val className = parent.adapter.getItem(position) as String
            ProxyActivity.startActivityPlugin(this, className)
        }
    }

    /**
     * 获取插件apk文件中的Activity
     * @param apkPluginPath 参数为插件apk文件路径
     */
    private fun getApkPluginPackageActivity(apkPluginPath: String): ArrayList<String> {
        val packageInfo = packageManager.getPackageArchiveInfo(apkPluginPath, PackageManager.GET_ACTIVITIES)
        val activities = packageInfo.activities
        val size = activities.size
        val data = ArrayList<String>(size)
        activities.forEach {
            data.add(it.name)
        }
        return data
    }
}
