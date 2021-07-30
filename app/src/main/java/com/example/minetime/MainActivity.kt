package com.example.minetime

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.example.minetime.databinding.ActivityMainBinding

var Point = 0

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    fun options(){
        AlertDialog.Builder(this)
            .setMessage("Choose the difficulty :")
            .setCancelable(false)
            .setPositiveButton("Easy",
                DialogInterface.OnClickListener { dialog, id -> runCount = 6 })
            .setNegativeButton("Hard",
                DialogInterface.OnClickListener { dialog, id -> runCount = 15 })
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        binding.button3.setOnClickListener(){
            val intent = Intent(this, Easy::class.java)
            startActivity(intent)
        }

        binding.button4.setOnClickListener(){
            val intent = Intent(this, Hard::class.java)
            startActivity(intent)
        }
    }}