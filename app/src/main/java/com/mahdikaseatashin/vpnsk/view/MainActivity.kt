package com.mahdikaseatashin.vpnsk.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahdikaseatashin.vpnsk.R
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
