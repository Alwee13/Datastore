package edu.unikom.loginlogoutprojects

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.unikom.loginlogoutprojects.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        lifecycleScope.launch {
            dataStoreManager.userDataFlow.collectLatest { userData ->
                if (userData == null) {
                    navigateToLogin()
                } else{
                    binding.txtNama.text = "Nama Anda: ${userData.nama}"
                    binding.txtEmail.text = "Email Anda: ${userData.email}"
                    binding.txtNoHp.text = "No HP Anda: ${userData.noHp}"

                }
            }
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                dataStoreManager.clearUserData()
            }
        }

    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}