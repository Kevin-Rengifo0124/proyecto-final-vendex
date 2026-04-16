package com.proyecto.vendex_proyecto_final.Vendedor.Productos

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.proyecto.vendex_proyecto_final.Adaptadores.AdaptadorImagenSeleccionada
import com.proyecto.vendex_proyecto_final.Constantes
import com.proyecto.vendex_proyecto_final.Modelo.ModeloImagenSeleccionada
import com.proyecto.vendex_proyecto_final.databinding.ActivityAgregarProductoBinding

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarProductoBinding
    private var imagenUri: Uri?=null

    private lateinit var imagenSelecArrayList: ArrayList<ModeloImagenSeleccionada>

    private lateinit var adaptadorImagenSeleccionada: AdaptadorImagenSeleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagenSelecArrayList = ArrayList()

        binding.imgAgregarProducto.setOnClickListener {
            seleccionarImagen()
        }

        cargarImagenes()


    }

    private fun cargarImagenes() {
        adaptadorImagenSeleccionada = AdaptadorImagenSeleccionada(this,imagenSelecArrayList)
        binding.RVImagenesProducto.adapter = adaptadorImagenSeleccionada
    }

    private fun seleccionarImagen() {
        ImagePicker.with(this)
            .crop().compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent {intent ->
                resultadoImagen.launch(intent)
            }
    }

    private val resultadoImagen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
        if (resultado.resultCode == Activity.RESULT_OK) {
            val data = resultado.data
            imagenUri = data!!.data
            val tiempo = "${Constantes().obtenerTiempoDispositivo()}"
            val modeloImagenSeleccionada = ModeloImagenSeleccionada(tiempo, imagenUri, null, false)
            imagenSelecArrayList.add(modeloImagenSeleccionada)
            cargarImagenes()
        } else {

            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
        }

    }
}