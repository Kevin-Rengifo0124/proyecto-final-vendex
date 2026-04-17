package com.proyecto.vendex_proyecto_final.Vendedor.Productos

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.vendex_proyecto_final.Adaptadores.AdaptadorImagenSeleccionada
import com.proyecto.vendex_proyecto_final.Constantes
import com.proyecto.vendex_proyecto_final.Modelo.ModeloCategoria
import com.proyecto.vendex_proyecto_final.Modelo.ModeloImagenSeleccionada
import com.proyecto.vendex_proyecto_final.databinding.ActivityAgregarProductoBinding

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarProductoBinding
    private var imagenUri: Uri? = null

    private lateinit var imagenSelecArrayList: ArrayList<ModeloImagenSeleccionada>

    private lateinit var adaptadorImagenSeleccionada: AdaptadorImagenSeleccionada

    private lateinit var categoriasArrayList: ArrayList<ModeloCategoria>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarCategorias()

        imagenSelecArrayList = ArrayList()

        binding.imgAgregarProducto.setOnClickListener {
            seleccionarImagen()
        }

        binding.categoria.setOnClickListener {
            seleccionarCategorias()
        }

        cargarImagenes()


    }

    private fun cargarCategorias() {
        categoriasArrayList = ArrayList()
        val ref =
            FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriasArrayList.clear()
                for (ds in snapshot.children) {
                    val modelo = ds.getValue(ModeloCategoria::class.java)
                    categoriasArrayList.add(modelo!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private var idCat = ""
    private var tituloCategoria = ""
    private fun seleccionarCategorias() {
        val categoriasArray = arrayOfNulls<String>(categoriasArrayList.size)
        for (i in categoriasArray.indices) {
            categoriasArray[i] = categoriasArrayList[i].categoria
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccione una categoria").setItems(categoriasArray) { dialog, witch ->

            idCat = categoriasArrayList[witch].id
            tituloCategoria = categoriasArrayList[witch].categoria
            binding.categoria.text = tituloCategoria

        }
            .show()

    }

    private fun cargarImagenes() {
        adaptadorImagenSeleccionada = AdaptadorImagenSeleccionada(this, imagenSelecArrayList)
        binding.RVImagenesProducto.adapter = adaptadorImagenSeleccionada
    }

    private fun seleccionarImagen() {
        ImagePicker.with(this)
            .crop().compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resultadoImagen.launch(intent)
            }
    }

    private val resultadoImagen =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val data = resultado.data
                imagenUri = data!!.data
                val tiempo = "${Constantes().obtenerTiempoDispositivo()}"
                val modeloImagenSeleccionada =
                    ModeloImagenSeleccionada(tiempo, imagenUri, null, false)
                imagenSelecArrayList.add(modeloImagenSeleccionada)
                cargarImagenes()
            } else {

                Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
            }

        }
}