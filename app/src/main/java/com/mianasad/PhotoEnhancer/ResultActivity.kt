package com.mianasad.PhotoEnhancer

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.InterstitialAd
import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import com.mianasad.PhotoEnhancer.MainActivity
import com.mianasad.PhotoEnhancer.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    var binding: ActivityResultBinding? = null
    private val mInterstitialAd: InterstitialAd? = null
    var imageView: ImageView? = null
    var imageView2: ImageView? = null
    var imageView3: ImageView? = null
    var imageView4: ImageView? = null
    var home: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        imageView = binding!!.shareBtn
        imageView2 = binding!!.facebookBtn
        imageView3 = binding!!.instagrambtn
        imageView4 = binding!!.whatsappBtn
        home = binding!!.homeBtn
        supportActionBar!!.hide()
        binding!!.image.setImageURI(intent.data)
        imageView!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            val screenshotUri = intent.data
            sharingIntent.type = "image/jpeg"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            startActivity(Intent.createChooser(sharingIntent, "Share image using"))
        }
        imageView2!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            val screenshotUri = intent.data
            sharingIntent.type = "image/jpeg"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            startActivity(Intent.createChooser(sharingIntent, "Share image using"))
        }
        imageView3!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            val screenshotUri = intent.data
            sharingIntent.type = "image/jpeg"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            startActivity(Intent.createChooser(sharingIntent, "Share image using"))
        }
        imageView4!!.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            val screenshotUri = intent.data
            sharingIntent.type = "image/jpeg"
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            startActivity(Intent.createChooser(sharingIntent, "Share image using"))
        }
        home!!.setOnClickListener {
            val returnBtn = Intent(applicationContext,
                    MainActivity::class.java)
            startActivity(returnBtn)
        }
    }
}