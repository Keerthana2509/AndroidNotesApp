package com.example.notesapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        var list = ArrayList<String>()
        @JvmStatic
        lateinit var arrayAdapter: ArrayAdapter<String>
    }
    lateinit var listView :ListView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if(item.itemId == R.id.add_item){
            val intent = Intent(applicationContext,NoteEditor::class.java)
            startActivity(intent)
            return true
        }
        if(item.itemId == R.id.help){
            val intent = Intent(applicationContext,Hepl::class.java)
            startActivity(intent)
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences =getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE)
        val set = sharedPreferences.getStringSet("notes",null)
        if(set == null){
            list.add("Enter notes")
        }
        else{
            list = ArrayList(set)
        }
        listView = findViewById<ListView>(R.id.listview)
        arrayAdapter = object: ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,list){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val textView:TextView = super.getView(position,
                    convertView, parent) as TextView
                //textView.layoutParams.height = 150
                val params: ViewGroup.LayoutParams = textView.layoutParams

                // Set the height of the Item View

                // Set the height of the Item View
                params.height = 120
                textView.layoutParams = params
                // set list view item height programmatically
               // textView.layoutParams.height = 9

                return textView
            }
        }

        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(applicationContext,NoteEditor::class.java)
            intent.putExtra("noteId",position)
            startActivity(intent)
        }
        listView.setOnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Are you sure?")
                .setMessage("Do you really want to delete this note?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        list.removeAt(position)
                        arrayAdapter.notifyDataSetChanged()
                        val set = HashSet<String>(MainActivity.list)
                        sharedPreferences.edit().putStringSet("notes",set).apply()
                    }

                })
                .setNegativeButton("NO",null)
                .show()
            return@setOnItemLongClickListener true
        }
        //listView.adapter = MyCoustomAdapter(arrayAdapter,this)
//        val btn = findViewById<Button>(R.id.button)
//
//        btn.setOnLongClickListener {
//            Toast.makeText(this,"Long pressed",Toast.LENGTH_LONG).show()
//            return@setOnLongClickListener true
//        }
    }
}
