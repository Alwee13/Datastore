package edu.unikom.loginlogoutprojects

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.unikom.loginlogoutprojects.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        //Fungsi Slide in animation
        val slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        binding.imgImageLogo.startAnimation(slideInAnim)
        binding.txtNamaAplikasi.startAnimation(slideInAnim)
        binding.txtLoading.startAnimation(slideInAnim)

        lifecycleScope.launch {
            delay(3000) // Menunggu dalam milidetik

            val user = dataStoreManager.userDataFlow.first()
            if (user != null) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }
            finish()
        }
    }
}