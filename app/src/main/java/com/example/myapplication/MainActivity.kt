package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnMoveDataUser : Button = findViewById(R.id.btnUsername)
        btnMoveDataUser.setOnClickListener(this)
    }

    override fun onClick(click: View?) {
        when(click?.id) {
            R.id.btnUsername -> {
                val moveDataUsername = Intent(this@MainActivity, DetailActivity::class.java)
                moveDataUsername.putExtra(DetailActivity.EXTRA_USER, "Aditya")
                startActivity(moveDataUsername)
            }
        }
    }
}