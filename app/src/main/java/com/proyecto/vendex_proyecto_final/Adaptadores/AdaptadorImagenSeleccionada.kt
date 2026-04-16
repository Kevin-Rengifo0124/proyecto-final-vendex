package com.proyecto.vendex_proyecto_final.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proyecto.vendex_proyecto_final.Modelo.ModeloImagenSeleccionada
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.databinding.ItemImagenSeleccionadasBinding

class AdaptadorImagenSeleccionada(
    private val context: Context,
    private val imagenesSelecArrayList: ArrayList<ModeloImagenSeleccionada>
) : RecyclerView.Adapter<AdaptadorImagenSeleccionada.HolderImagenSeleccionada>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagenSeleccionada {
        val binding = ItemImagenSeleccionadasBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return HolderImagenSeleccionada(binding)
    }

    override fun onBindViewHolder(holder: HolderImagenSeleccionada, position: Int) {
        val modelo = imagenesSelecArrayList[position]
        val imagenUri = modelo.imageUri

        try {
            Glide.with(context)
                .load(imagenUri)
                .placeholder(R.drawable.item_imagen)
                .into(holder.imagenItem)
        } catch (e: Exception) { }

        holder.boton_borrar.setOnClickListener {
            imagenesSelecArrayList.remove(modelo)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = imagenesSelecArrayList.size

    inner class HolderImagenSeleccionada(val binding: ItemImagenSeleccionadasBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val imagenItem = binding.imagenItem
        val boton_borrar = binding.borrarItem
    }
}