package com.wuyson.googleappdata

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_open_gallery.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery(){
        val imagePath = File(this@MainActivity.filesDir,"images")
        val newFile = File(imagePath,"default_image.jpg")

        val contentUri =
            FileProvider.getUriForFile(this@MainActivity, "com.wuyson.googleappdata.fileprovider", newFile)
        Log.e("TAG",contentUri.toString())

        this.grantUriPermission(this.packageName,contentUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
//        intent.data = contentUri
        intent.putExtra(MediaStore.EXTRA_OUTPUT,contentUri)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        this.startActivityForResult(intent,200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                200 -> {
                    val uri = data?.data

                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
                    img_show.setImageBitmap(bitmap)
                }
            }
        }


    }


}
