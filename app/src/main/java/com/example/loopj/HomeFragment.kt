package com.example.loopj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loopj.databinding.FragmentHomeBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import android.util.Log
import java.lang.String


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUsers()
    }

    private fun getUsers() {
        val client = AsyncHttpClient()
        val url = "https://reqres.in/api/users?page=1"

        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray
            ) {
                val response = String(responseBody)
                Log.d("Loop J Success", response.toString())
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header?>,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e("LOOPJ_ERROR", error?.message.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}