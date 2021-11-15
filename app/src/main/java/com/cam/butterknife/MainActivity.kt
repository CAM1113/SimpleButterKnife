package com.cam.butterknife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.cam.butterknife_annotation_lib.BindView
import com.cam.butterknife_lib.ButterKnife

class MainActivity : AppCompatActivity() {
    @BindView(R.id.tv)
    lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        tv.text = "Hello, this is CAM"
    }
}