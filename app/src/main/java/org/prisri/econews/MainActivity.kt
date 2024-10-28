package org.prisri.econews

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.prisri.econews.activities.HomeActivity
import org.prisri.econews.activities.LoginActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val versionTextView = findViewById<TextView>(R.id.vers)
        val packageManager = packageManager
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName
            val versionCode = packageInfo.versionCode
            versionTextView.text= "Version $versionName"
        } catch (e:Exception){
            e.message?.let { Log.e("theError", it) }
        }
        nextActivity()
    }
    private fun nextActivity() {
        Handler().postDelayed({
            val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
            if (sharedPreferences.getBoolean("isAuth", false)){
                val i = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }else{
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }, 2000)
    }
}