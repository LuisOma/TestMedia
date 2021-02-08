package com.example.testmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.testmedia.audio.ui.AudioActivity
import com.example.testmedia.gallery.GalleryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_audio.setOnClickListener(this)
        btn_gallery.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_audio->{
                startActivity(Intent(this@MainActivity,AudioActivity::class.java))
            }
            R.id.btn_gallery->{
                startActivity(Intent(this@MainActivity,GalleryActivity::class.java))
            }
        }
    }
}