package com.dreamseeker.org.baytodo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dreamseeker.org.httpclient.Rest
import org.json.JSONArray
import org.json.JSONObject

class MemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        val rest: Rest = Rest()
        val okButton: Button = findViewById(R.id.ok_memo) as Button
        val textView: TextView = findViewById(R.id.title) as TextView
        val editText: EditText = findViewById(R.id.plain_text_memo) as EditText
        val intent: Intent = intent
        val title: String = intent.getStringExtra("title")

        textView.text = title
        memoRead(this, title)

        okButton.setOnClickListener { view ->
            val sendIntent = Intent(this, MainActivity::class.java)

            rest.updateMemo(editText.text.toString(), title, this)
            startActivity(sendIntent)
        }
    }

    fun memoRead(context: Context, title: String) {
        val POST_URL: String = "http://localhost:9999/volley-controller/memo_read.php"
        val postQueue: RequestQueue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, POST_URL,
                object : Response.Listener<String> {
                    override fun onResponse(response: String?) {
                        setMemo(response)
                        Toast.makeText(context, "Success!!", Toast.LENGTH_SHORT).show()
                    }
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

    fun setMemo(response: String?) {
        val jsonObject: JSONObject = JSONObject(response)
        val count: JSONArray = jsonObject.getJSONArray("SQL_TEST")

        var i = 0
        val editText: EditText = findViewById(R.id.plain_text_memo) as EditText
        //val listItems : ArrayList<Lists> = ArrayList()

        while(i < count.length()) {
            val data = count.getJSONObject(i)
            editText.setText(data.getString("memo"))
            i++
        }
    }
}
