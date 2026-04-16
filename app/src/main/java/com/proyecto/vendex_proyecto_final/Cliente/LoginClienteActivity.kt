package com.proyecto.vendex_proyecto_final.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.vendex_proyecto_final.databinding.ActivityLoginClienteBinding

class LoginClienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.botonLoginCliente.setOnClickListener {
            validarInfo()
        }

        binding.textViewRegistarCliente.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroClienteActivity::class.java))
        }

    }

    private var email = ""
    private var password = ""
    private fun validarInfo() {
        email = binding.editEmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()

        if (email.isEmpty()) {
            mostrarError(binding.editEmail, "Ingrese su email completo")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarError(binding.editEmail, "Email no válido")
        } else if (password.isEmpty()) {
            mostrarError(binding.editPassword, "Ingrese su password")
        } else {
            loginCliente()
        }
    }

    private fun loginCliente() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityCliente::class.java))
                finishAffinity()
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se puede iniciar sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

    }

    private fun mostrarError(campo: EditText, mensaje: String) {
        campo.error = mensaje
        campo.requestFocus()
    }
}