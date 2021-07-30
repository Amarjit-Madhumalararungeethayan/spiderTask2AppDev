package com.example.minetime

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.example.minetime.databinding.ActivityEasyBinding
import com.example.minetime.databinding.ActivityHardBinding

var winH = true

class Hard : AppCompatActivity() {
    lateinit var binding: ActivityHardBinding

    lateinit var sharedPrefs2: SharedPreferences

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
        binding = ActivityHardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.isVisible = false

        binding.button.setOnClickListener() {
            binding.battleground.refresh2()
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

                if(Point == 48 && winH){

                    val win = MediaPlayer.create(baseContext, R.raw.win)
                    win.start()

                    binding.imageView.isVisible = true
                    binding.battleground.isVisible = false
                    binding.button.isVisible = false

                    binding.textView4.text = "You Win !"

                    winH = false
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

                    sharedPrefs2 = getSharedPreferences("HighScoreX", MODE_PRIVATE)
                    val highscore = sharedPrefs2.getInt("highscorex", 0)

                    if (highscore >= Point) {
                        binding.textView5.text = "HighScore -> ${highscore}"
                    }
                    else {
                        binding.textView5.text = "HighScore -> ${Point}"
                        val editor = getSharedPreferences("HighScoreX", MODE_PRIVATE).edit()
                        editor.putInt("highscorex", Point)
                        editor.apply()
                    }
                }
                if(Point == 38 && Speak){
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

