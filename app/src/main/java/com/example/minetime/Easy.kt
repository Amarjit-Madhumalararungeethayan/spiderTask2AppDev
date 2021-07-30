package com.example.minetime

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.example.minetime.databinding.ActivityEasyBinding

var Speak = true
var winE = true

class Easy : AppCompatActivity() {
    lateinit var binding: ActivityEasyBinding

    lateinit var sharedPrefs1: SharedPreferences

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Do you want to play again ?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id-> System.exit(0) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id-> System.exit(0) })
            .setIcon(R.drawable.bomb)
            .show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEasyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.battleground.invalidate()


        binding.imageView.isVisible = false

        binding.button.setOnClickListener() {
            binding.battleground.refresh1()
            binding.battleground.chooseCoors()
            binding.battleground.check()
            scoreRefresh()
        }
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }


    }

    private fun scoreRefresh() {
        val countDown: CountDownTimer
        countDown = object : CountDownTimer(10000000, 10) {
            override fun onTick(millisecsToFinish: Long) {
                binding.textView3.text = "Points -> ${Point}"

                if(Point == 52 && winE){

                    val win = MediaPlayer.create(baseContext, R.raw.win)
                    win.start()

                    binding.imageView.isVisible = true
                    binding.battleground.isVisible = false
                    binding.button.isVisible = false

                    binding.textView4.text = "You Win !"

                    winE = false
                }
                if(endGame){
                    val exp = MediaPlayer.create(baseContext, R.raw.bombexp)
                    exp.start()

                    binding.imageView.setImageResource(R.drawable.one)
                    binding.imageView.isVisible = true
                    vibrateNow()
                    binding.battleground.isVisible = false
                    binding.button.isVisible = false

                    binding.textView4.text = "You Lose !"
                    endGame = false

                    sharedPrefs1 = getSharedPreferences("HighScore", MODE_PRIVATE)
                    val highscore = sharedPrefs1.getInt("highscore", 0)

                    if (highscore >= Point) {
                        binding.textView5.text = "HighScore -> ${highscore}"
                    }
                    else {
                        binding.textView5.text = "HighScore -> ${Point}"
                        val editor = getSharedPreferences("HighScore", MODE_PRIVATE).edit()
                        editor.putInt("highscore", Point)
                        editor.apply()
                    }
                }
                if(Point == 42 && Speak){
                    val hb = MediaPlayer.create(baseContext, R.raw.hb)
                    hb.start()
                    Speak = false
                }
            }

            override fun onFinish() {
                scoreRefresh()
            }
        }
        countDown.start()
    }
    private fun vibrateNow() {
        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v.vibrate(500)
        }

    }
}


