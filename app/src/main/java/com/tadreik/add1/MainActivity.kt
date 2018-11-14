package com.tadreik.add1

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.os.CountDownTimer
import android.widget.*


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val numberLabel = findViewById<TextView>(R.id.number_text)
        val numberImage = findViewById<ImageView>(R.id.number_image)
        val timeLabel = findViewById<TextView>(R.id.time_text)
        val scoreLabel = findViewById<TextView>(R.id.score_text)
        val editText = findViewById<EditText>(R.id.editText)
        val resetButton = findViewById<Button>(R.id.reset_button)

        var isCounterRunning = false
        var score = 0

        resetButton.visibility = View.GONE

        val counter = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                isCounterRunning = true
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                timeLabel.text = String.format("%d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Time Up!", Toast.LENGTH_LONG).show()
                numberImage.visibility = View.INVISIBLE
                resetButton.visibility = View.VISIBLE
                numberLabel.textSize = 40F
                numberLabel.text = "You scored $score"
                isCounterRunning = false
            }
        }

        fun operation() {
            if( !isCounterRunning ){
                isCounterRunning = true

                counter.start()
            } else {
                counter.cancel()
                counter.start()
            }
        }

        operation()


        fun generateRandomNumber() : String {
            var result = ""

            for (i in 1..4) {
                val digit = (1..8).shuffled().last()
                result += "$digit"
            }
            return result
        }

        fun updateScoreLabel() {
            scoreLabel.text = "$score"
        }

        fun setRandomNumberLabel() {
            numberLabel.text = generateRandomNumber()
        }

        setRandomNumberLabel()
        updateScoreLabel()

        fun checkIfWon() {
            if (editText.text.toString().isNotEmpty()) {
                if (Integer.parseInt(editText.text.toString()) - Integer.parseInt(numberLabel.text.toString()) == 1111) {
                    Toast.makeText(this,"Correct!", Toast.LENGTH_SHORT).show()
                    score += 1
                    editText.text.clear()
                    updateScoreLabel()
                    setRandomNumberLabel()
                } else {
                    Toast.makeText(this,"Wrong!", Toast.LENGTH_SHORT).show()
                    if (score > 0) {
                        score -= 1
                    }
                    editText.text.clear()
                    updateScoreLabel()
                    setRandomNumberLabel()
                }
            } else {
                Toast.makeText(this@MainActivity, "Enter a number", Toast.LENGTH_SHORT).show()
            }
        }

        editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    checkIfWon()
                    return@OnKeyListener true
                }
            }
            false
        })

        resetButton.setOnClickListener {
            score = 0
            numberLabel.text = generateRandomNumber()
            numberImage.visibility = View.VISIBLE
            resetButton.visibility = View.GONE
            updateScoreLabel()
            operation()
        }
    }

    override fun onPause() {
        super.onPause()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}
