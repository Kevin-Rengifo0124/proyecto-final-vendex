package com.proyecto.vendex_proyecto_final.Vendedor

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.databinding.ActivityRegistroVendedorBinding

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.botonRegistrarVendedor.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombre = ""
    private var email = ""
    private var password = ""
    private var comprobarPassword = ""

    private fun validarInformacion() {
        nombre = binding.editNombresVendedor.text.toString().trim()
        email = binding.editEmailVendedor.text.toString().trim()
        password = binding.editPasswordVendedor.text.toString().trim()
        comprobarPassword = binding.editConfirmarPasswordVendedor.text.toString().trim()

        if (nombre.isEmpty()) {
            mostrarError(binding.editNombresVendedor, "Ingrese su nombre completo")
        } else if (email.isEmpty()) {
            mostrarError(binding.editEmailVendedor, "Ingrese su email completo")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarError(binding.editEmailVendedor, "Email no válido")
        } else if (password.isEmpty()) {
            mostrarError(binding.editPasswordVendedor, "Ingrese su password")
        } else if (password.length < 6) {
            mostrarError(binding.editPasswordVendedor, "Mínimo 6 caracteres")
        } else if (comprobarPassword.isEmpty()) {
            mostrarError(binding.editConfirmarPasswordVendedor, "Confirme el password")
        } else if (password != comprobarPassword) {
            mostrarError(binding.editConfirmarPasswordVendedor, "No coincide el password")
        } else {
            registrarVendedor()
        }
    }

    private fun mostrarError(campo: EditText, mensaje: String) {
        campo.error = mensaje
        campo.requestFocus()
    }

    private fun registrarVendedor() {

        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertarInformacionBD()
            }

            .addOnFailureListener { e->
                Toast.makeText(this, "Falló el registro debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun insertarInformacionBD() {

    }
}