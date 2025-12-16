package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_GENDER = "extra_gender"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvDataUsername : TextView = findViewById(R.id.tvDataUsername)
        val tvDataAge : TextView = findViewById(R.id.tvDataAge)
        val tvDataGender : TextView = findViewById(R.id.tvDataGender)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val age = intent.getIntExtra(EXTRA_AGE, 23)
        val gender = intent.getStringExtra(EXTRA_GENDER)

        val tvUsername = "$username"
        val tvage = "$age"
        val tvgender = "$gender"

        tvDataUsername.text = tvUsername
        tvDataAge.text = tvage
        tvDataGender.text = tvgender
    }
}