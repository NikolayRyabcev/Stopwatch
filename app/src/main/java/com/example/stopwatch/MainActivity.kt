package com.example.stopwatch

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var result: TextView
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        editText = findViewById(R.id.time)
        result = findViewById(R.id.result)
        handler = Handler(Looper.getMainLooper())


        button.setOnClickListener {
            if (!editText.text.isNullOrEmpty()) {
                var time = (editText.text).toString().toLong()
                time *= 1000
                button.isEnabled = false
                timing(time)
            }

        }
    }

    private fun timing(time: Long) {
        val timeThread = Thread {
            var startTime = System.currentTimeMillis()
            var elaspedTime: Long
            while (true) {
                elaspedTime = time - System.currentTimeMillis() + startTime
                handler.postDelayed({ result.text = formatMilliseconds(elaspedTime) }, 100L)
                if (elaspedTime <= 0L) break
            }
            handler.post { button.isEnabled = true }


        }
        timeThread.start()
    }

    private fun formatMilliseconds(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "$minutes:$seconds"
    }
}