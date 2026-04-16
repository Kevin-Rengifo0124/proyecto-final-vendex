package com.proyecto.vendex_proyecto_final.Vendedor.Nav_Fragments_Vendedor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesVendedor
import com.proyecto.vendex_proyecto_final.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentProductosVendedor
import com.proyecto.vendex_proyecto_final.Vendedor.Productos.AgregarProductoActivity
import com.proyecto.vendex_proyecto_final.databinding.FragmentInicioVendedorBinding

class FragmentInicioVendedor : Fragment() {

    private lateinit var binding: FragmentInicioVendedorBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioVendedorBinding.inflate(inflater, container, false)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.opcion_mis_productos_vendedor -> {
                    replaceFragment(FragmentProductosVendedor())
                }

                R.id.opcion_mis_ordenes_vendedor -> {
                    replaceFragment(FragmentOrdenesVendedor())
                }
            }
            true
        }

        replaceFragment(FragmentProductosVendedor())
        binding.bottomNavigation.selectedItemId = R.id.opcion_mis_productos_vendedor
        binding.addFab.setOnClickListener {
           startActivity(Intent(context, AgregarProductoActivity:: class.java))
        }
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.bottomFragment, fragment).commit()
    }
}