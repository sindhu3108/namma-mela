package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class MainActivity : ComponentActivity() {

    private lateinit var shows: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val cast = findViewById<Button>(R.id.btnCast)
        val seat = findViewById<Button>(R.id.btnSeats)
        val fan = findViewById<Button>(R.id.btnFan)
        val profile = findViewById<Button>(R.id.btnProfile)

        val playTitle = findViewById<TextView>(R.id.playTitleText)
        val playInput = findViewById<EditText>(R.id.playInput)
        val updateBtn = findViewById<Button>(R.id.updateBtn)

        val fade = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scale = AnimationUtils.loadAnimation(this, R.anim.scale)

        // Apply animations
        cast.startAnimation(fade)
        seat.startAnimation(fade)
        fan.startAnimation(fade)
        profile.startAnimation(fade)

        cast.setOnClickListener {
            it.startAnimation(scale)
            val intent = Intent(this, CastActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        seat.setOnClickListener {
            it.startAnimation(scale)
            val intent = Intent(this, SeatActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        fan.setOnClickListener {
            it.startAnimation(scale)
            val intent = Intent(this, FanActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        profile.setOnClickListener {
            it.startAnimation(scale)
            val intent = Intent(this, ProfileActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        updateBtn.setOnClickListener {
            val newPlay = playInput.text.toString()
            if (newPlay.isNotEmpty()) {
                playTitle.text = newPlay
                playInput.setText("")
            }
        }

        // --- FIX: POPULATING RECYCLERVIEW ---
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val shimmer = findViewById<ShimmerFrameLayout>(R.id.shimmer)
        
        recycler.layoutManager = LinearLayoutManager(this)
        
        // Fix 1: Add Local Data so it's not empty
        shows = ArrayList<String>()
        shows.add("Company Nataka")
        shows.add("Village Drama")
        shows.add("Folk Dance Night")
        
        val adapter = ShowAdapter(shows)
        recycler.adapter = adapter

        // Fix 4: Stop Shimmer and show the list
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        recycler.visibility = View.VISIBLE

        // Keep Firebase as an optional live update (Fix 6)
        val ref = FirebaseDatabase.getInstance().getReference("shows")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    shows.clear()
                    for (data in snapshot.children) {
                        data.getValue(String::class.java)?.let { shows.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.seats -> {
                    startActivity(Intent(this, SeatActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
