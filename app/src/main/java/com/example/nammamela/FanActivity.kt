package com.example.nammamela

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fan)

        val input = findViewById<EditText>(R.id.input)
        val post = findViewById<Button>(R.id.post)
        val comments = findViewById<LinearLayout>(R.id.comments)

        post.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                val tv = TextView(this)
                tv.text = text
                tv.setTextColor(Color.WHITE)
                tv.textSize = 16f
                tv.setPadding(0, 8, 0, 8)

                comments.addView(tv)
                input.setText("")
            }
        }
    }
}
