package com.example.minetime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.example.minetime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button2.isEnabled = false

        binding.button.setOnClickListener(){
            binding.battleground.refresh()
            binding.button2.isEnabled = true
            binding.textView.text = ""

        }
        binding.button2.setOnClickListener(){
            binding.textView.text = "[ (${xCoordinates[0]},${yCoordinates[0]}) , (${xCoordinates[1]},${yCoordinates[1]}) , (${xCoordinates[2]},${yCoordinates[2]}) , (${xCoordinates[3]},${yCoordinates[3]}) , (${xCoordinates[4]},${yCoordinates[4]}) , (${xCoordinates[5]},${yCoordinates[5]}) , (${xCoordinates[6]},${yCoordinates[6]}) ]"
            binding.button2.isEnabled = false
        }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }

}