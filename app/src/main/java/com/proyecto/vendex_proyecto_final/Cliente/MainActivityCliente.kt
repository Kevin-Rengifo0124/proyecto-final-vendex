package com.proyecto.vendex_proyecto_final.Cliente

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.proyecto.vendex_proyecto_final.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesCliente
import com.proyecto.vendex_proyecto_final.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendaCliente
import com.proyecto.vendex_proyecto_final.Cliente.Nav_Fragments_Cliente.FragmentInicioCliente
import com.proyecto.vendex_proyecto_final.Cliente.Nav_Fragments_Cliente.FragmentMiPerfilCliente
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.databinding.ActivityMainClienteBinding

class MainActivityCliente : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioCliente())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.navFragment, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opcion_inicio_cliente -> {
                replaceFragment(FragmentInicioCliente())
            }
            R.id.opcion_mi_perfil_cliente -> {
                replaceFragment(FragmentMiPerfilCliente())
            }
            R.id.opcion_cerrar_sesion_cliente -> {
                Toast.makeText(applicationContext, "Has cerrado sesión", Toast.LENGTH_SHORT).show()
            }
            R.id.opcion_tienda_cliente -> {
                replaceFragment(FragmentTiendaCliente())
            }
            R.id.opcion_mis_ordenes_cliente -> {
                replaceFragment(FragmentMisOrdenesCliente())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}