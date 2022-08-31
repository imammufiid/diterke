package com.mufiid.diterke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mufiid.core.view.ComponentPlaygroundActivity
import com.mufiid.diterke.auth.AuthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_hello).setOnClickListener {
            AuthActivity.launch(this)
        }
    }
}