package yangj.plugin

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_list.*
import yangj.plugin.base.BaseActivity

/**
 * @author YangJ
 */
class ListActivity : BaseActivity() {

    private lateinit var mAdapter: ArrayAdapter<String>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initView()
    }

    private fun initView() {
        // 初始化数据适配器
        val size = 100
        val list = ArrayList<String>(size)
        for (i in 1..size) {
            list.add(i.toString())
        }
        println("list = $list")
        mAdapter = ArrayAdapter(mProxyActivity, android.R.layout.simple_list_item_1, list)
        println("mAdapter = $mAdapter")
        // 绑定数据适配器
        listView.adapter = mAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.adapter.getItem(position) as String
            // 启动ShowActivity
            val intent = Intent()
            intent.putExtra("item", item)
            val cls = ShowActivity::class.java
            val className = cls.name
            startActivity(intent, className)
        }
    }
}
