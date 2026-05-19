package com.example.nammamela

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SeatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(seat: Seat)

    @Query("SELECT * FROM Seat")
    fun getAllSeats(): List<Seat>

    @Query("SELECT * FROM Seat WHERE seatNumber = :seatNum LIMIT 1")
    fun getSeatByNumber(seatNum: String): Seat?
}
