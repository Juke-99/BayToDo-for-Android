package com.dreamseeker.org.baytodo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dreamseeker.org.adapter.ListAdapter
import com.dreamseeker.org.iterator.Lists
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listItems : ArrayList<Lists> = ArrayList()
        var i = 0

        while(i < 6) {

            if(i == 3) {
                val item = Lists("TaskTitle" + i, "2017/5/28", true)  //No Checking
                listItems.add(item)
                i++
            } else {
                val item = Lists("TaskTitle" + i, "2017/6/28", false)
                listItems.add(item)
                i++
            }
        }

        val adapter = ListAdapter(this, R.layout.task_layout, listItems)
        task_list.adapter = adapter
    }
}
