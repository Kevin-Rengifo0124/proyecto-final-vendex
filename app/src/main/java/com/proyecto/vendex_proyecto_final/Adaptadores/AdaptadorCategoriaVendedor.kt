package com.proyecto.vendex_proyecto_final.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.vendex_proyecto_final.Modelo.ModeloCategoria
import com.proyecto.vendex_proyecto_final.databinding.ItemCategoriaVendedorBinding

class AdaptadorCategoriaVendedor :
    RecyclerView.Adapter<AdaptadorCategoriaVendedor.HolderCategoriaVendedor> {

    private lateinit var binding: ItemCategoriaVendedorBinding
    private val mContext: Context
    private val categoriaArrayList: ArrayList<ModeloCategoria>

    constructor(mContext: Context, categoriaArrayList: ArrayList<ModeloCategoria>) {
        this.mContext = mContext
        this.categoriaArrayList = categoriaArrayList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HolderCategoriaVendedor {
        binding = ItemCategoriaVendedorBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HolderCategoriaVendedor(binding.root)
    }

    override fun onBindViewHolder(
        holder: HolderCategoriaVendedor,
        position: Int
    ) {
        val modelo = categoriaArrayList[position]
        val id = modelo.id
        val categoria = modelo.categoria

        holder.item_nombre_categoria_vendedor.text = categoria

        holder.item_eliminar_categorias.setOnClickListener {
            Toast.makeText(mContext, "Eliminar categoria", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return categoriaArrayList.size
    }

    inner class HolderCategoriaVendedor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var item_nombre_categoria_vendedor = binding.itemNombreCategoriaVendedor
        var item_eliminar_categorias = binding.itemEliminarCategorias
    }
}