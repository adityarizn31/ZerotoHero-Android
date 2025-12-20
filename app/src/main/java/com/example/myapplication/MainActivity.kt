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

        // WAJIB — init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val user = User(
                name = binding.etName.text.toString(),
                age = binding.etAge.text.toString().toInt(),
                email = binding.etEmail.text.toString(),
                gender = binding.etGender.text.toString().firstOrNull() ?: 'U'
            )

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("USER_DATA", user)   // <- pakai objek, bukan class
            startActivity(intent)
        }
    }
}