package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WAJIB inflate dulu
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data user
        val user = intent.getParcelableExtra<User>("user")!!

        // Set data ke EditText via binding
        binding.etUsername.setText(user.username)
        binding.etEmail.setText(user.email)

        // Button save
        binding.btnSave.setOnClickListener {
            val updatedUser = user.copy(
                username = binding.etUsername.text.toString(),
                email = binding.etEmail.text.toString()
            )

            val resultIntent = Intent().apply {
                putExtra("updated_user", updatedUser)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}