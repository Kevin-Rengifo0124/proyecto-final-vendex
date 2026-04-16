package com.proyecto.vendex_proyecto_final

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.vendex_proyecto_final.Cliente.MainActivityCliente
import com.proyecto.vendex_proyecto_final.Vendedor.MainActivityVendedor

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth = FirebaseAuth.getInstance()
        verBienvenida()

    }

    private fun verBienvenida() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                comprobarTipoUsuario()
            }
        }.start()
    }

    private fun comprobarTipoUsuario() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, MainActivityVendedor::class.java))
        } else {

            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val tipoUsuario = snapshot.child("tipoUsuario").value

                        if (tipoUsuario == "Vendedor") {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    MainActivityVendedor::class.java
                                )
                            )
                            finishAffinity()
                        } else if (tipoUsuario == "Cliente") {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    MainActivityCliente::class.java
                                )
                            )
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

        }
    }


}