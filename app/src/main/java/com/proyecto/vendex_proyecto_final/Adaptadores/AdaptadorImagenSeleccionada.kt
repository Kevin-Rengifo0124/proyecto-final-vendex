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
    private lateinit var binding: ItemImagenSeleccionadasBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HolderImagenSeleccionada {

        binding =
            ItemImagenSeleccionadasBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderImagenSeleccionada(binding.root)
    }

    override fun onBindViewHolder(
        holder: HolderImagenSeleccionada,
        position: Int
    ) {

        val modelo = imagenesSelecArrayList[position]
        val imagenUri = modelo.imageUri

        //Leyendo la imagen
        try {
            Glide.with(context)
                .load(imagenUri)
                .placeholder(R.drawable.item_imagen)
                .into(holder.imagenItem)
        }catch (e: Exception){

        }

        //Evento para eliminar una imagen de una lista
        holder.boton_borrar.setOnClickListener {
            imagenesSelecArrayList.remove(modelo)
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return imagenesSelecArrayList.size
    }

    inner class HolderImagenSeleccionada(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imagenItem = binding.imagenItem
        var boton_borrar = binding.borrarItem

    }


}