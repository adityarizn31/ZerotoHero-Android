package com.example.sunlifeapps.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlifeapps.R
import com.example.sunlifeapps.data.pref.SilancarModel
import com.example.sunlifeapps.databinding.ActivityMainBinding
import com.example.sunlifeapps.view.ViewModelFactory
import com.example.sunlifeapps.view.adapter.RecyclerviewAdapter
import com.example.sunlifeapps.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        checkSession()
        setupRecycler()
    }

//    private fun checkSession() {
//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        }
//    }

    private fun setupRecycler() {
        val recyclerView = findViewById<RecyclerView>(R.id.menuRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val menulist = listOf(
            SilancarModel("KTP", R.drawable.ic_ktp),
            SilancarModel("KK", R.drawable.ic_ktp),
            SilancarModel("Akta Kelahiran", R.drawable.ic_ktp),
            SilancarModel("Kartu Identitas Anak / KIA", R.drawable.ic_ktp),
            SilancarModel("Surat Pindah Datang Provinsi", R.drawable.ic_ktp),
        )

        recyclerView.adapter = RecyclerviewAdapter(menulist)
    }
}

//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//    }
//
//    private fun setupAction() {
//        binding.btn
//    }
