package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userEmail = findViewById<TextView>(R.id.userEmail)
        val bookingStats = findViewById<TextView>(R.id.bookingStats)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        val auth = FirebaseAuth.getInstance()
        userEmail.text = auth.currentUser?.email ?: "Guest"

        val db = AppDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            // This is a simple implementation. In a real app, we'd have a count query in DAO.
            val seats = db.seatDao().getAllSeats()
            val bookedCount = seats.count { it.isBooked }
            
            withContext(Dispatchers.Main) {
                if (bookedCount > 0) {
                    bookingStats.text = getString(R.string.my_bookings_format, bookedCount)
                } else {
                    bookingStats.text = getString(R.string.no_bookings)
                }
            }
        }

        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}