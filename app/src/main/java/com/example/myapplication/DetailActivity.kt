package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val updatedUser = result.data?.getParcelableExtra<User>("user")
            updatedUser?.let {
                user = it
                binding.tvDetailName.text = it.username
                binding.tvDetailEmail.text = it.email
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra("user")

        user?.let {
            binding.tvDetailName.text = it.username
            binding.tvDetailEmail.text = it.email
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("user", user)
            editLauncher.launch(intent)
        }
    }
}
