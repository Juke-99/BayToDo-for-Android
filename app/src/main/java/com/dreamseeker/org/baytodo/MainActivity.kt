package com.dreamseeker.org.baytodo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dreamseeker.org.adapter.ListAdapter
import com.dreamseeker.org.httpclient.Rest
import com.dreamseeker.org.iterator.Lists
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    val rest: Rest = Rest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent: Intent = Intent(application, MemoActivity::class.java)
        val feb: FloatingActionButton = findViewById(R.id.fab) as FloatingActionButton

        rereadVolley(this)

        task_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val listView: ListView = parent as ListView
            val list: Lists = listView.getItemAtPosition(position) as Lists  //--> Lists(title="titlename", date="xxxx/xx/xx", checkbox="true or false")
            intent.putExtra("title", list.title)
            startActivity(intent)
        }

        feb.setOnClickListener { view ->
            showDialog()
        }
    }

    fun showDialog() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val editText: EditText = EditText(this)

        dialog.setTitle("ToDoタイトルを入力してください")
                .setView(editText)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, whichButton ->
                    rest.startVolley(editText.text.toString(), this)
                    rereadVolley(this)
                })
                .setNegativeButton("キャンセル", DialogInterface.OnClickListener { dialogInterface, whichButton ->
                    dialogInterface.dismiss()
                }).create()

        dialog.show()
    }

    fun rereadVolley(context: Context) {
        val GET_URL: String = "http://localhost:9999/volley-controller/read.php"
        val getQueue: RequestQueue = Volley.newRequestQueue(context)
        val jsonRequest: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, GET_URL,
                Response.Listener<JSONObject> { response ->
                    changeListView(response)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(context, "Http Response Error!!", Toast.LENGTH_SHORT).show()
                }
        )

        getQueue.add(jsonRequest)
    }

    fun changeListView(response: JSONObject) {
        val count: JSONArray = response.getJSONArray("SQL_TEST")

        var i = 0
        val listItems : ArrayList<Lists> = ArrayList()
        val adapter = ListAdapter(this, R.layout.task_layout, listItems)
        adapter.clear()

        while(i < count.length()) {
            val data = count.getJSONObject(i)
            val item = Lists(data.getString("title"), "登録日時：${data.getString("regist_date")}", data.getBoolean("checkbox"))
            listItems.add(item)
            i++
        }

        task_list.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
