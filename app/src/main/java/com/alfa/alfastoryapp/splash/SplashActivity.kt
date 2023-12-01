package com.alfa.alfastoryapp.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alfa.alfastoryapp.MainActivity
import com.alfa.alfastoryapp.databinding.ActivitySplashBinding
import com.alfa.alfastoryapp.datastore.UserPreference
import com.alfa.alfastoryapp.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val userPreference = UserPreference(this)
        lifecycleScope.launch {
            delay(4000)
            val splash = if (userPreference.isLogin){
                Intent(this@SplashActivity, MainActivity::class.java)
            }else{
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(splash)
            finish()
        }
    }
}