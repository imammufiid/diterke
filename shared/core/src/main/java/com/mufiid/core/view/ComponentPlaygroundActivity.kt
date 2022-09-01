package com.mufiid.core.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mufiid.core.R

class ComponentPlaygroundActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(
                Intent(context, ComponentPlaygroundActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component_playground)
    }
}