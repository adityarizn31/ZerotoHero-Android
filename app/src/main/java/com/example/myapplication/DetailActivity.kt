package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvUserReceived : TextView = findViewById(R.id.tvUserReceived)

        val user = intent.getStringExtra(EXTRA_USER) ?: "No Name"
        tvUserReceived.text = "Username : $user"

    //    val user = intent.getStringExtra(EXTRA_USER)
//        val text = "Username : $user"
//        tvUserReceived.text = text

    }
}