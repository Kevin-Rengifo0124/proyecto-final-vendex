package com.proyecto.vendex_proyecto_final.Cliente.Nav_Fragments_Cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.proyecto.vendex_proyecto_final.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesCliente
import com.proyecto.vendex_proyecto_final.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendaCliente
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.databinding.FragmentInicioClienteBinding


class FragmentInicioCliente : Fragment() {

    private lateinit var binding: FragmentInicioClienteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioClienteBinding.inflate(inflater, container, false)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.opcion_tienda_cliente -> {
                    replaceFragment(FragmentTiendaCliente())
                }

                R.id.opcion_mis_ordenes_cliente -> {
                    replaceFragment(FragmentMisOrdenesCliente())
                }

            }
            true
        }
        replaceFragment(FragmentTiendaCliente())
        binding.bottomNavigation.selectedItemId = R.id.opcion_tienda_cliente
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.bottomFragment, fragment).commit()
    }


}