package com.proyecto.vendex_proyecto_final.Vendedor

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesVendedor
import com.proyecto.vendex_proyecto_final.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentProductosVendedor
import com.proyecto.vendex_proyecto_final.Vendedor.Nav_Fragments_Vendedor.FragmentInicioVendedor
import com.proyecto.vendex_proyecto_final.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaVendedor
import com.proyecto.vendex_proyecto_final.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasVendedor
import com.proyecto.vendex_proyecto_final.databinding.ActivityMainVendedorBinding

class MainActivityVendedor : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainVendedorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar,
            R.string.open_drawer, R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioVendedor())
        binding.navigationView.setCheckedItem(R.id.opcion_inicio_vendedor)

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.navFragment, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.opcion_inicio_vendedor -> {
                replaceFragment(FragmentInicioVendedor())
            }

            R.id.opcion_mi_tienda_vendedor -> {
                replaceFragment(FragmentMiTiendaVendedor())
            }

            R.id.opcion_resenia_vendedor -> {
                replaceFragment(FragmentReseniasVendedor())
            }

            R.id.opcion_cerrar_sesion_vendedor -> {
                Toast.makeText(applicationContext, "Saliste de la aplicación", Toast.LENGTH_SHORT)
                    .show()
            }

            R.id.opcion_mis_productos_vendedor -> {
                replaceFragment(FragmentProductosVendedor())
            }

            R.id.opcion_mis_ordenes_vendedor -> {
                replaceFragment(FragmentOrdenesVendedor())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}