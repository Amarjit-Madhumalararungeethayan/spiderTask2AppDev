package com.example.minetime

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.example.minetime.databinding.ActivityEasyBinding
import com.example.minetime.databinding.ActivityHardBinding

class Hard : AppCompatActivity() {
    lateinit var binding: ActivityHardBinding

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

        binding.button2.isEnabled = false

        binding.button.setOnClickListener() {
            binding.battleground.refresh2()
            binding.battleground.chooseCoors()
            binding.battleground.check()
            binding.button2.isEnabled = true
            binding.textView.text = ""
            scoreRefresh()
        }
        binding.button2.setOnClickListener() {
            binding.textView.text =
                "[ (${xCoordinates[0]},${yCoordinates[0]}) , (${xCoordinates[1]},${yCoordinates[1]}) , (${xCoordinates[2]},${yCoordinates[2]}) , (${xCoordinates[3]},${yCoordinates[3]}) , (${xCoordinates[4]},${yCoordinates[4]}) , (${xCoordinates[5]},${yCoordinates[5]}) , (${xCoordinates[6]},${yCoordinates[6]}) , (${xCoordinates[7]},${yCoordinates[7]}) , (${xCoordinates[8]},${yCoordinates[8]}) , (${xCoordinates[9]},${yCoordinates[9]}) , (${xCoordinates[10]},${yCoordinates[10]}) , (${xCoordinates[11]},${yCoordinates[11]}) , (${xCoordinates[12]},${yCoordinates[12]}) , (${xCoordinates[13]},${yCoordinates[13]}) , (${xCoordinates[14]},${yCoordinates[14]}) ]"
            binding.button2.isEnabled = false
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

                var temp = 0

                if(Point == 48){
                    binding.battleground.isVisible = false
                    binding.button2.isVisible = false
                    binding.button.isVisible = false
                    binding.textView.isVisible = false

                    binding.textView4.text = "You Win !"
                }
                if(endGame){
                    vibrateNow()
                    binding.battleground.isVisible = false
                    binding.button2.isVisible = false
                    binding.button.isVisible = false
                    binding.textView.isVisible = false

                    binding.textView4.text = "You Lose !"
                    endGame = false
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

