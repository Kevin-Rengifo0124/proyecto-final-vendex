package com.proyecto.vendex_proyecto_final.Cliente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.databinding.ActivityLoginClienteBinding

class LoginClienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewRegistarCliente.setOnClickListener {
            startActivity(Intent(this@LoginClienteActivity, RegistroClienteActivity::class.java))
        }

    }
}