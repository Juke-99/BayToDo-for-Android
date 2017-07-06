package com.dreamseeker.org.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dreamseeker.org.baytodo.R
import com.dreamseeker.org.httpclient.Rest
import com.dreamseeker.org.iterator.Lists

/**
 * Created by kisaragihatuka on 2017/06/28.
 *
 */
class ListAdapter(context: Context, resource: Int, lists: List<Lists>): ArrayAdapter<Lists>(context, resource, lists) {
    var mResource : Int = resource
    var mitem : List<Lists> = lists
    var layoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val rest: Rest = Rest()
    val preContext: Context = context

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

        checkboxState(checkbox, title, date)

        checkbox.setOnCheckedChangeListener { compoundButton, b ->
            updateCheckbox(title.text.toString(), checkbox.isChecked, preContext)
            checkboxState(checkbox, title, date)
        }

        return view
    }

    fun updateCheckbox(title: String, checkbox: Boolean, context: Context) {
        val putQueue: RequestQueue = Volley.newRequestQueue(context)
        val PUT_URL = "http://localhost:9999/volley-controller/checkbox.php"
        val stringRequest: StringRequest = object : StringRequest(Request.Method.PUT, PUT_URL,
                Response.Listener<String> { s ->
                    Toast.makeText(context, "Success!!", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show()
                }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = mutableMapOf()
                params.put("title", title)
                params.put("checkbox", checkbox.toString())

                return params
            }
        }

        putQueue.add(stringRequest)
    }

    fun checkboxState(checkbox: CheckBox, title: TextView, date: TextView) {
        if(checkbox.isChecked) {
            title.setTextColor(Color.rgb(150, 150, 150))
            date.setTextColor(Color.rgb(150, 150, 150))
        } else {
            title.setTextColor(Color.rgb(0, 0, 0))
            date.setTextColor(Color.rgb(0, 0, 0))
        }
    }
}