package com.example.nvest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.nvest.ui.home.HomeFragment
import com.example.nvest.ui.product.AddProductActivity
import com.example.nvest.ui.profile.ProfileActivity
import com.example.nvest.ui.report.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationview: BottomNavigationView
    private lateinit var textViewDate: TextView
    private lateinit var textViewGreeting: TextView
    private lateinit var fabAddProduct: FloatingActionButton
    private lateinit var imgProfile: ImageView

    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0
    private var activeActivity: String = "home"

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Declare All UI */
        bottomNavigationview = findViewById(R.id.bottomNavigationView)
        textViewDate = findViewById(R.id.textViewDateNow)
        textViewGreeting = findViewById(R.id.textViewGreeting)
        fabAddProduct = findViewById(R.id.fabAdd)
        imgProfile = findViewById(R.id.imageViewAvatar)

        val intent = intent.extras
        /* End Declare All UI */

        /* Fragment Event */
        bottomNavigationview.background = null
        bottomNavigationview.menu.getItem(1).isEnabled = false

        if (intent?.getString("ACTIVITY") == "report") {
            replaceFragment(ReportFragment())
            bottomNavigationview.menu.getItem(1).isChecked = true
            activeActivity = "report"
        } else {
            replaceFragment(HomeFragment())
        }

        /* End Fragment Event*/

        /* Text Event */
        val sdf = SimpleDateFormat("EEEE, dd MMM yyyy HH:mm:ss a")

        lifecycleScope.launch {
            while (isActive) {
                textViewDate.text = sdf.format(Date())
                delay(1000)
            }
        }

        val nickname = "Lutfi"
        textViewGreeting.text = "Halo, ${nickname} \uD83D\uDC4B"
        /* End Text Event*/

        /* Clicked Event */
        bottomNavigationview.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    activeActivity = "home"
                    true
                }
                R.id.nav_report -> {
                    replaceFragment(ReportFragment())
                    activeActivity = "report"
                    true
                }
                else -> false
            }
        }

        fabAddProduct.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java)
                .putExtra("ACTIVITY", activeActivity)
            )
            finish()
        }

        imgProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java)
                .putExtra("ACTIVITY", activeActivity)
            )
            finish()
        }
        /* End Clicked Event*/
    }

    /* Show Fragment */
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    /* Screen Back Button */
    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(baseContext, "Tekan Sekali Lagi Untuk Keluar", Toast.LENGTH_SHORT)
                .show()
        }
        mBackPressed = System.currentTimeMillis()
    }
}