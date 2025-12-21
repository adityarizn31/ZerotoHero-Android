package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnKirimData.setOnClickListener {
            val user = arrayListOf(
                User("aditya", "rizkiawan@gmail.com", "Jaksel", "DCO"),
                User("rizkiawan", "rizkiawan@gmail.com", "Jaksel", "DCO"),
                User("nugraha", "rizkiawan@gmail.com", "Jaksel", "DCO"),
            )

            val intent = Intent(this, DetailActivity::class.java)
            intent.putParcelableArrayListExtra("USER_LIST", user)
            startActivity(intent)
        }
    }
}