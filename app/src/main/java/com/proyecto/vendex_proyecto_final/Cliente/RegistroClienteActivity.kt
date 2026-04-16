package com.proyecto.vendex_proyecto_final.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.proyecto.vendex_proyecto_final.Constantes
import com.proyecto.vendex_proyecto_final.databinding.ActivityRegistroClienteBinding

class RegistroClienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.botonRegistrarCliente.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombre = ""
    private var email = ""
    private var password = ""
    private var comprobarPassword = ""
    private fun validarInformacion() {
        nombre = binding.editNombresCliente.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        comprobarPassword = binding.editConfirmarPassword.text.toString().trim()

        if (nombre.isEmpty()) {
            mostrarError(binding.editNombresCliente, "Ingrese su nombre completo")
        } else if (email.isEmpty()) {
            mostrarError(binding.editEmail, "Ingrese su email completo")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarError(binding.editEmail, "Email no válido")
        } else if (password.isEmpty()) {
            mostrarError(binding.editPassword, "Ingrese su password")
        } else if (password.length < 6) {
            mostrarError(binding.editPassword, "Mínimo 6 caracteres")
        } else if (comprobarPassword.isEmpty()) {
            mostrarError(binding.editConfirmarPassword, "Confirme el password")
        } else if (password != comprobarPassword) {
            mostrarError(binding.editConfirmarPassword, "No coincide el password")
        } else {
            registrarCliente()
        }
    }

    private fun mostrarError(campo: EditText, mensaje: String) {
        campo.error = mensaje
        campo.requestFocus()
    }

    private fun registrarCliente() {

        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertarInformacionBD()
            }

            .addOnFailureListener { e ->
                Toast.makeText(this, "Falló el registro debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun insertarInformacionBD() {
        progressDialog.setMessage("Guardando información...")

        val userIdBDCliente = firebaseAuth.uid
        val nombreBDCliente = nombre
        val emailBDCliente = email
        val tiempoBDCliente = Constantes().obtenerTiempoDispositivo()

        val datosCliente = HashMap<String, Any>()

        datosCliente["uid"] = "$userIdBDCliente"
        datosCliente["nombre"] = "$nombreBDCliente"
        datosCliente["email"] = "$emailBDCliente"
        datosCliente["imagen"] = ""
        datosCliente["tipoUsuario"] = "Cliente"
        datosCliente["tiempoRegistro"] = "$tiempoBDCliente"

        val references = FirebaseDatabase.getInstance().getReference("Usuarios")
        references.child(userIdBDCliente!!)
            .setValue(datosCliente)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@RegistroClienteActivity, MainActivityCliente::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Falló el registro en base de datos debido a ${e.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }


    }

}