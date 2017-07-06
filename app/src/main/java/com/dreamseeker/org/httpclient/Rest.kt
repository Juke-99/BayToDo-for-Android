package com.dreamseeker.org.httpclient

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * Created by kisaragihatuka on 2017/07/02.
 */

class Rest: AppCompatActivity() {
    fun startVolley(title: String, context: Context) {
        val postQueue: RequestQueue = Volley.newRequestQueue(context)
        val POST_URL = "http://localhost:9999/volley-controller/edit.php"
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
        val PUT_URL = "http://localhost:9999/volley-controller/memo.php"
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