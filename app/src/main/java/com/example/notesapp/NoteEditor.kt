package com.example.notesapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class NoteEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        val editText = findViewById<EditText>(R.id.editText)

        val intent = intent
        var noteId = intent.getIntExtra("noteId",-1)
        if(noteId != -1){
            val h =MainActivity.list
//            editText.text = h[noteId]
            editText.setTextKeepState(h[noteId])
        }
        else{
            MainActivity.list.add("")
            noteId = MainActivity.list.size - 1
        }
        editText.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val p =0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val p =0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                MainActivity.list.set(noteId, s.toString())
                MainActivity.arrayAdapter.notifyDataSetChanged()

                val sharedPreferences =getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE)
                val set = HashSet<String>(MainActivity.list)
                sharedPreferences.edit().putStringSet("notes",set).apply()
            }

        })
    }
}
