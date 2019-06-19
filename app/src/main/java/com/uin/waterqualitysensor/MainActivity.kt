package com.uin.waterqualitysensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.String.format
import java.security.SecureRandom
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {

   /* // ini buat coba aja
    val suhuRand: Int = 40
    val phrand: Float = 6.9f*/

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout_tindakan_suhu.visibility = View.GONE
        layout_tindakan_ph.visibility = View.GONE
        readData()
//        changeCardColor(suhuRand, phrand)

    }

    private fun readData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(TAG, "Failed to read value.", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue(SensorValue::class.java)
                val suhu = data?.suhu
                val ph = data?.ph

                Log.i("MainActivity","suhu = $suhu, ph = $ph")

                tindak(suhu!!, ph!!)

            }
        })
    }

    fun tindak(suhu: Double, ph: Double){
        val suhuBackground: Int?
        val suhuTitle: String?
        val suhuAkibat: String?
        val suhuSolusi : String?

        val phBackground:Int
        val phTitle: String?
        val phAkibat: String?
        val phSolusi : String?
        when (suhu) {
            in 0.0..27.0 -> {
                suhuBackground = R.color.warning
                suhuTitle = "Suhu terlalu rendah"
                suhuAkibat = "Akibat : Nafsu makan udang akan menurun, pertumbuhan menjadi tidak normal, banyak kalori yang hilang sehingga dapat menyebabkan kematian."
                suhuSolusi = "Solusi : Menaikkan kedalaman air menjadi sekitar 100 cm dan melakukan sirkulasi air."
            }
            in 28.0..30.0 -> {
                suhuBackground = R.color.good
                suhuTitle = "Suhu Optimal"
                suhuAkibat = "Udang sehat"
                suhuSolusi = "Saran : Menjaga suhu untuk tetap optimal"
            }
            else -> {
                suhuBackground = R.color.danger
                suhuTitle = "Suhu terlalu tinggi"
                suhuAkibat = "Akibat : Nafsu makan udang akan menurun juga serta dapat mengakibatkan stress pada udang."
                suhuSolusi = "Solusi : Menambah kedalaman air menjadi 130 cm dan melakukan sirkulasi air."}
        }
        when (ph) {
            in 0.0..5.0 -> {
                phBackground = R.color.warning
                phTitle = "pH terlalu rendah"
                phAkibat = "Akibat : Berkurangnya nafsu makan udang, alkalinitas tidak stabil dan udang mudah stress"
                phSolusi = "Solusi : Pemberian kapur baik itu pada masa persiapan kolam maupun selama budidaya berlangsung. Dosis 1.000 – 1.500 Kg/Ha"}
            in 5.0..6.7 -> {
                phBackground = R.color.warning
                phTitle = "pH terlalu rendah"
                phAkibat = "Akibat : Berkurangnya nafsu makan udang, alkalinitas tidak stabil dan udang mudah stress"
                phSolusi = "Solusi : Pemberian kapur baik itu pada masa persiapan kolam maupun selama budidaya berlangsung. Dosis 500 – 1.000 Kg/Ha"}
            in 8.6..9.0 -> {
                phBackground = R.color.warning
                phTitle = "pH terlalu tinggi"
                phAkibat = "Akibat : Mudah sakit dan lemah, dan nafsu makan menurun bahkan udang cenderung keropos dan berlumut."
                phSolusi = "Solusi : Rebus daun ketapang untuk menghilangkan zat tanin lalu rendam daun ketapang di dasar air selama beberapa hari."}
            in 6.8..8.5 -> {
                phBackground = R.color.good
                phTitle = "pH optimal"
                phAkibat = "Udang sehat"
                phSolusi = "Saran : Menjaga suhu untuk tetap optimal"}
            else -> {
                phBackground = R.color.danger
                phTitle = "pH terlalu tinggi"
                phAkibat = "Akibat : Kematian udang"
                phSolusi = "Solusi : Rebus daun ketapang untuk menghilangkan zat tanin lalu rendam daun ketapang di dasar air selama beberapa hari."
            }
        }

        val formattedSuhu = "%.2f".format(suhu)
        val formattedPh = "%.2f".format(ph)

        suhutv.text = formattedSuhu
        phtv.text = formattedPh
        suhu_conclusion.text = suhuTitle
        suhu_akibat.text = suhuAkibat
        suhu_solusi.text = suhuSolusi

        ph_conclusion.text = phTitle
        ph_akibat.text = phAkibat
        ph_solusi.text = phSolusi

        layout_tindakan_suhu.visibility = View.VISIBLE
        layout_tindakan_ph.visibility = View.VISIBLE

        layout_suhu.setBackgroundResource(suhuBackground)
        layout_ph.setBackgroundResource(phBackground)
        layout_suhu.preventCornerOverlap
        layout_ph.preventCornerOverlap

        if (phBackground == R.color.good && suhuBackground== R.color.good) status.text = "Optimal"
        else if (phBackground == R.color.good || suhuBackground == R.color.good) status.text = "Perlu tindakan"
        else if (phBackground != R.color.good && suhuBackground != R.color.good) status.text = "Bahaya"
        else status.text = "idk"
    }
}
