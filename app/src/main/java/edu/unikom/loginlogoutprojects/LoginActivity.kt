package edu.unikom.loginlogoutprojects

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.unikom.loginlogoutprojects.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        binding.btnLogin.setOnClickListener {
            val nama = binding.edtNama.text.toString()
            val email = binding.edtEmail.text.toString()
            val noHp = binding.edtNoHp.text.toString()

            if(nama.isEmpty()){
                binding.edtNama.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }

            if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmail.error = "Email tidak valid"
                return@setOnClickListener
            }

            if(noHp.isEmpty()){
                binding.edtNoHp.error = "No HP tidak boleh kosong"
                return@setOnClickListener
            }

            //coroutine untuk menyimpan data ke datastore
            lifecycleScope.launch {
                dataStoreManager.saveUserData(nama, email, noHp)
                navigateToMain()
            }
        }

    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}