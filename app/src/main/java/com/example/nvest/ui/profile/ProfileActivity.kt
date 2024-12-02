package com.example.nvest.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.nvest.MainActivity
import com.example.nvest.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var buttonBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        /* Declare All UI */
        buttonBack = findViewById(R.id.imageViewBack)
        /* End Declare All UI */

        /* Text Event */
        /* End Text Event*/

        /* Clicked Event */
        buttonBack.setOnClickListener {
            onBackPressed()
        }
        /* End Clicked Event*/
    }

    /* Screen Back Button */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent = intent.extras

        if (intent != null) {
            startActivity(
                Intent(this, MainActivity::class.java)
                    .putExtra("ACTIVITY", intent.getString("ACTIVITY"))
            )
            finish()
        }
    }
}