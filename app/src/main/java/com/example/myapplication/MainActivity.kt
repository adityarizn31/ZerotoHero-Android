package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityMainBinding

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

        binding.btnSendList.setOnClickListener {

            val userlist = arrayListOf(
                User("adityarn", "aditya@gmail.com", "Jaksel"),
                User("nzul", "nzul@gmail.com", "Jaksel"),
                User("diah", "diah@gmail.com", "Jaksel"),
            )

            val intent = Intent(this, DetailActivity::class.java)
            intent.putParcelableArrayListExtra("USER_LIST", userlist)
            startActivity(intent)
        }

    }
}