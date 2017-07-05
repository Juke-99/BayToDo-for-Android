package com.dreamseeker.org.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.dreamseeker.org.baytodo.R
import com.dreamseeker.org.iterator.Lists

/**
 * Created by kisaragihatuka on 2017/06/28.
 *
 */
class ListAdapter(context: Context, resource: Int, lists: List<Lists>): ArrayAdapter<Lists>(context, resource, lists) {
    var mResource : Int = resource
    var mitem : List<Lists> = lists
    var layoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View

        if(convertView != null) {
            view = convertView
        } else {
            view = layoutInflater.inflate(mResource, null)
        }

        val item : Lists = mitem[position]
        val title : TextView = view.findViewById(R.id.task_title)
        val date : TextView = view.findViewById(R.id.regist_date)
        val checkbox : CheckBox = view.findViewById(R.id.checkBox)

        title.text = item.title
        date.text = item.date
        checkbox.isChecked = item.checkbox

        return view
    }
}