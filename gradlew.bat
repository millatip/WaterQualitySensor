package com.tif.uin.millatip.barberkingqline

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.random.Random

class MenuActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    var nextValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        supportActionBar?.title = "Barberking Queue Line"

        val randomValues =  Random.nextInt(0, 10)
        number.text = randomValues.toString()
        nextValue = randomValues + 1

        val intent = intent
        val myValue = intent.getStringExtra("id")

        text_view1.text = "Selamat datang, $myValue"

        antri_btn.setOnClickListener {
            addDialog()
            makeVisible()
        }

        batal.setOnClickListener {
            makeInvisible()
        }

        logout.setOnClickListener {
            view ->
                showMessage(view, "Logging out...")
            signOut()
        }

        fbAuth.addAuthStateListener {
            if(fbAuth.currentUser == null){
                this.finish()
            }
        }
    }

    fun addDialog(){
        val dialog: MaterialDialog = MaterialDialog(this).show {
            title(R.string.permintaan)
            message(R.string.gapapa)
            input(
                hint = "Tolong siapkan cat rambut toska",
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            )
            positiveButton(R.string.agree)
            negativeButton(R.string.disagree)
        }
    }

    fun makeInvisible(){
        antrianke.visibility = View.GONE
        batal.visibility = View.GONE
    }

    fun makeVisible(){
        antrianke.visibility = View.VISIBLE
        antrianke.text = "Anda antrian ke $nextValu