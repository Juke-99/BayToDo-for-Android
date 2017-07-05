package com.dreamseeker.org.httpclient

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dreamseeker.org.adapter.ListAdapter
import com.dreamseeker.org.baytodo.R
import com.dreamseeker.org.iterator.Lists
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * Created by kisaragihatuka on 2017/07/02.
 */

class Rest: AppCompatActivity() {
    fun startVolley(title: String, context: Context) {
        val postQueue: RequestQueue = Volley.newRequestQueue(context)
        val POST_URL = "http://localhost:9999/edit.php"
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, POST_URL,
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

                return params
            }
        }

        postQueue.add(stringRequest)
    }

    fun updateMemo(memo: String, title: String, context: Context) {
        val putQueue: RequestQueue = Volley.newRequestQueue(context)
        val PUT_URL = "http://localhost:9999/memo.php"
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
                params.put("memo", memo)
                params.put("title", title)

                return params
            }
        }

        putQueue.add(stringRequest)
    }
}