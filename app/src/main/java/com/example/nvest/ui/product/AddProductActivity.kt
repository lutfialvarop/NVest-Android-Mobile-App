package com.example.nvest.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nvest.MainActivity
import com.example.nvest.R
import com.example.nvest.databinding.ActivityAddProductBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class AddProductActivity : AppCompatActivity() {
    private lateinit var buttonBack: ImageView
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var binding: ActivityAddProductBinding

    private val requestCodeCameraPermission = 1001
    private var scannedValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // setContentView(R.layout.activity_add_product)

        /* Permision */
        if (ContextCompat.checkSelfPermission(this@AddProductActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askForCameraPermision()
        } else {
            setupControls()
        }
        /* End Permision */

        /* Declare All UI */
        buttonBack = findViewById(R.id.imageViewBack)
        /* End Declare All UI */

        /* Animation */
        val animSlide: Animation = AnimationUtils.loadAnimation(this, R.anim.scanner_animation)
        binding.viewBarcodeLine.startAnimation(animSlide)
        /* End Animation*/

        /* Clicked Event */
        buttonBack.setOnClickListener {
            onBackPressed()
        }
        /* End Clicked Event*/
    }

    private fun setupControls() {
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.surfaceViewCamera.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(holder)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(holder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                cameraSource.start(holder)
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems

                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue

                    runOnUiThread {
                        cameraSource.stop()
                        Toast.makeText(
                            this@AddProductActivity,
                            "value-$scannedValue",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()
                    }
                } else {
                    Toast.makeText(this@AddProductActivity, "value-else", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun askForCameraPermision() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), requestCodeCameraPermission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupControls()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ContextCompat.checkSelfPermission(this@AddProductActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraSource.stop()
        }
    }

    /* Screen Back Button */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent = intent.extras

        if (intent != null) {
            startActivity(Intent(this, MainActivity::class.java)
                .putExtra("ACTIVITY", intent.getString("ACTIVITY"))
            )
            finish()
        }
    }
}



