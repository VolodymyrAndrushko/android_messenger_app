package com.example.task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task2.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEventListeners()
    }

    private fun setEventListeners() {
        TODO("Not yet implemented")
    }
}


private fun setPhoto(){

}