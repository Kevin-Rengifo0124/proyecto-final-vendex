package com.proyecto.vendex_proyecto_final.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.vendex_proyecto_final.databinding.ActivityLoginVendedorBinding

class LoginVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.botonLogin.setOnClickListener {
            validarInfo()
        }

        binding.textViewRegistarVendedor.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroVendedorActivity::class.java))
        }

    }

    private var email = ""
    private var password = ""
    private fun validarInfo() {
        email = binding.editEmailVendedor.text.toString().trim()
        password = binding.editPasswordVendedor.text.toString().trim()

        if (email.isEmpty()) {
            mostrarError(binding.editEmailVendedor, "Ingrese su email completo")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarError(binding.editEmailVendedor, "Email no válido")
        } else if (password.isEmpty()) {
            mostrarError(binding.editPasswordVendedor, "Ingrese su password")
        } else {
            loginVendedor()
        }
    }

    private fun loginVendedor() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
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