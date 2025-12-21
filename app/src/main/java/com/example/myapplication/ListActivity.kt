package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListBinding
    private lateinit var adapter : UserAdapter

    private val users = mutableListOf<User>(
        User(1, "Aditya", "aditya@gmail.com"),
        User(2, "Jule", "jule@gmail.com")
    )

//    private val editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val updatedUser = result.data?.getParcelableExtra<User>("updated_user")
//            updatedUser?.let {
//                val index = users.indexOfFirst { u -> u.id == it.id }
//                if (index != -1) {
//                    users[index] = it
//                    adapter.notifyItemChanged(index)
//                }
//            }
//        }
//    }

    private val detailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedUser =
                    result.data?.getParcelableExtra<User>("updated_user")

                if (updatedUser != null) {
                    val index = users.indexOfFirst { it.id == updatedUser.id }
                    if (index != -1) {
                        users[index] = updatedUser
                        adapter.notifyItemChanged(index)
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = UserAdapter(users) { user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("user", user)
            detailLauncher.launch(intent)
        }

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = this@ListActivity.adapter
        }

    }
}