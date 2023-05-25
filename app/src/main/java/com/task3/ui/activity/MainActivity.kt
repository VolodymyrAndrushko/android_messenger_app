package com.task3.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task3.R
import com.example.task3.databinding.ActivityMainBinding
import com.task3.ui.fragments.contacts.ContactsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val fragment = ContactsFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activityFragmentContainer, fragment)
                .commit()
        }
    }
}