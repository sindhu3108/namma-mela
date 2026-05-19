package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class ShowDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)

        val title = intent.getStringExtra("showName") ?: "Unknown Play"
        findViewById<TextView>(R.id.detailTitle).text = title

        findViewById<Button>(R.id.bookNowBtn).setOnClickListener {
            startActivity(Intent(this, SeatActivity::class.java))
        }
    }
}