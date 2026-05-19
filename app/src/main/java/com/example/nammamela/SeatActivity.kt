package com.example.nammamela

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SeatActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)

        db = AppDatabase.getDatabase(this)
        val grid = findViewById<GridLayout>(R.id.grid)
        val total = findViewById<TextView>(R.id.total)

        val price = 150
        val selectedSeats = mutableSetOf<Int>()
        val seatButtons = mutableMapOf<Int, Button>()

        // Fetch already booked seats from Room DB
        val bookedSeats = db.seatDao().getAllSeats().filter { it.isBooked }.map { it.seatNumber }

        for (i in 1..16) {
            val btn = Button(this)
            val seatNum = "S$i"
            btn.text = seatNum
            
            // Layout params for GridLayout
            val params = GridLayout.LayoutParams()
            params.width = 0
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.setMargins(8, 8, 8, 8)
            btn.layoutParams = params

            // Check if already booked
            if (bookedSeats.contains(seatNum)) {
                btn.backgroundTintList = ColorStateList.valueOf(Color.RED)
                btn.isEnabled = false
            } else {
                // Initial state: Available (Orange)
                btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFA500"))
                
                btn.setOnClickListener {
                    if (selectedSeats.contains(i)) {
                        // Unselect
                        selectedSeats.remove(i)
                        btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFA500"))
                    } else {
                        // Select
                        selectedSeats.add(i)
                        btn.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
                    }
                    
                    val totalPrice = selectedSeats.size * price
                    total.text = getString(R.string.total_price_format, totalPrice)
                }
            }

            grid.addView(btn)
            seatButtons[i] = btn
        }

        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            if (selectedSeats.isNotEmpty()) {
                selectedSeats.forEach { index ->
                    val seatNum = "S$index"
                    db.seatDao().insert(Seat(seatNumber = seatNum, isBooked = true))
                    
                    val btn = seatButtons[index]
                    btn?.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    btn?.isEnabled = false
                }
                selectedSeats.clear()
                total.text = getString(R.string.total_price_format, 0)
                Toast.makeText(this, R.string.booking_successful, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.select_at_least_one, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
