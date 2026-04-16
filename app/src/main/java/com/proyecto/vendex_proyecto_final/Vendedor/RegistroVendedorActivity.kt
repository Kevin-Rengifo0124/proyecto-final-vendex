package com.proyecto.vendex_proyecto_final.Vendedor

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
        email = binding.editEmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        comprobarPassword = binding.editConfirmarPassword.text.toString().trim()

        if (nombre.isEmpty()) {
            mostrarError(binding.editNombresVendedor, "Ingrese su nombre completo")
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

            .addOnFailureListener { e ->
                Toast.makeText(this, "Falló el registro debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun insertarInformacionBD() {
        progressDialog.setMessage("Guardando información...")

        val userIdBD = firebaseAuth.uid
        val nombreBD = nombre
        val emailBD = email
        val tiempoBD = Constantes().obtenerTiempoDispositivo()

        val datosVendedor = HashMap<String, Any>()

        datosVendedor["uid"] = "$userIdBD"
        datosVendedor["nombre"] = "$nombreBD"
        datosVendedor["email"] = "$emailBD"
        datosVendedor["tipoUsuario"] = "Vendedor"
        datosVendedor["tiempoRegistro"] = "$tiempoBD"

        val references = FirebaseDatabase.getInstance().getReference("Usuarios")
        references.child(userIdBD!!)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finish()
            }
            .addOnFailureListener {e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Falló el registro en base de datos debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }


    }
}