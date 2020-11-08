package com.example.askandroidforpermissions

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        withDexter()
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun withDexter(){
        findViewById<TextView>(R.id.permission_tv).setOnClickListener {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener{
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            showToast("Permission granted")
                        }

                        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                            showToast("Permission denied")

                        }

                        override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, token: PermissionToken?) {
                            showToast("onPermissionRationaleShouldBeShown")

                            AlertDialog.Builder(this@MainActivity)
                                    .setTitle("Give Permission Internet")
                                    .setMessage("You need to give permission internet for this app")
                                    .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener {
                                        dialogInterface, i ->
                                        dialogInterface.dismiss()
                                        token?.cancelPermissionRequest()
                                    })
                                    .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener {
                                        dialogInterface, i ->
                                        dialogInterface.dismiss()
                                        token?.continuePermissionRequest()
                                    })
                                    .show()
                        }

                    })
                    .check()
        }


    }

    fun pureAndroidPermitions(){

    }
}