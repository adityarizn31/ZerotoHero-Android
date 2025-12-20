package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val users = listOf(
            User("adityarn", "adityarizkiawann@gmail.com", "Jakarta Selatan", "082262309419"),
            User("ammar", "ammar@gmail.com", "Jakarta Selatan", "082262309419"),
            User("arham", "arham@gmail.com", "Jakarta Selatan", "082262309419"),
        )

        binding.btnSend.setOnClickListener{
            val intent = Intent(this, UsersListActivity::class.java)
            intent.putParcelableArrayListExtra("users", ArrayList(users))
            startActivity(intent)
        }
    }
}