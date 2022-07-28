package com.mianasad.PhotoEnhancer

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.initialization.InitializationStatus
import android.content.Intent
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.provider.MediaStore
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants
import com.mianasad.PhotoEnhancer.ResultActivity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import com.google.android.gms.ads.AdRequest
import com.mianasad.PhotoEnhancer.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var IMAGE_REQUEST_CODE = 45
    var CAMERA_REQUEST_CODE = 14
    var RESULT_CODE = 200
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding!!.root)
        MobileAds.initialize(this) { }
        val adRequest = AdRequest.Builder().build()
        binding!!.adView.loadAd(adRequest)
        supportActionBar!!.hide()
        binding!!.editBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
        binding!!.cameraBtn.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CAMERA), 32)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (data!!.data != null) {
                val filePath = data.data
                val dsPhotoEditorIntent = Intent(this, DsPhotoEditorActivity::class.java)
                dsPhotoEditorIntent.data = filePath
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "PhotoEnhancer")
                val toolsToHide = intArrayOf(DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP)
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide)
                startActivityForResult(dsPhotoEditorIntent, RESULT_CODE)
            }
        }
        if (requestCode == RESULT_CODE) {
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.data = data!!.data
            startActivity(intent)
        }
        if (requestCode == CAMERA_REQUEST_CODE) {
            val photo = data!!.extras!!["data"] as Bitmap?
            val uri = getImageUri(photo)
            val dsPhotoEditorIntent = Intent(this, DsPhotoEditorActivity::class.java)
            dsPhotoEditorIntent.data = uri
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "PhotoEnhancer")
            val toolsToHide = intArrayOf(DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP)
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide)
            startActivityForResult(dsPhotoEditorIntent, RESULT_CODE)
        }
    }

    fun getImageUri(bitmap: Bitmap?): Uri {
        val arrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, arrayOutputStream)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }
}