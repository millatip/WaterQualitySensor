package com.uin.waterqualitysensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.security.SecureRandom
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {

    // ini buat coba aja
    val suhuRand: Int = 40
    val phrand: Float = 6.9f

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //readData()
        changeCardColor(suhuRand, phrand)
    }

    private fun readData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("data")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(TAG, "Failed to read value.", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue(SensorValue::class.java)
                val suhu = data?.suhu?.toInt()
                val ph = data?.ph?.toFloat()!!

                //changeCardColor(suhu, ph)

            }
        })
    }

    fun changeCardColor(suhu: Int?, ph: Float){
        val suhuBackground = when (suhu) {
            in 24..27 -> R.color.warning
            in 28..30 -> R.color.good
            else -> R.color.danger
        }
        val phBackground = when (ph) {
            in 4.5f..6.7f -> R.color.warning
            in 8.6f..9.0f -> R.color.warning
            in 6.8f..8.5f -> R.color.good
            else -> R.color.danger
        }

        suhutv.text = suhu.toString()
        phtv.text = ph.toString()

        layout_suhu.setBackgroundResource(suhuBackground)
        layout_ph.setBackgroundResource(phBackground)

        if (phBackground == R.color.good && suhuBackground== R.color.good) status.text = "Excelent"
        else if (phBackground == R.color.good || suhuBackground== R.color.good) status.text = "Good"
        else if (phBackground != R.color.good && suhuBackground != R.color.good) status.text = "Danger"
        else status.text = "idk"
    }
}
