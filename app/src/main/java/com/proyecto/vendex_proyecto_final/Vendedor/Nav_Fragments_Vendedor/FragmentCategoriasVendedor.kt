package com.proyecto.vendex_proyecto_final.Vendedor.Nav_Fragments_Vendedor

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.proyecto.vendex_proyecto_final.R
import com.proyecto.vendex_proyecto_final.databinding.FragmentCategoriasVendedorBinding


class FragmentCategoriasVendedor : Fragment() {

    private lateinit var binding: FragmentCategoriasVendedorBinding
    private lateinit var mContext: Context

    private lateinit var progressDialog: ProgressDialog

    private var imageUri: Uri? = null

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriasVendedorBinding.inflate(inflater, container, false)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.imagenCategorias.setOnClickListener {
            seleccionarImagenCategorias()
        }

        binding.botonAgregarCategoria.setOnClickListener {
            validarInformacion()
        }
        return binding.root
    }

    private fun seleccionarImagenCategorias() {
        ImagePicker.with(requireActivity())
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                resultadoImagen.launch(intent)
            }
    }

    private val resultadoImagen =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val data = resultado.data
                imageUri = data!!.data
                binding.imagenCategorias.setImageURI(imageUri)
            } else {
                Toast.makeText(mContext, "Acción cancelada", Toast.LENGTH_SHORT).show()
            }

        }

    private var categoria = ""
    private fun validarInformacion() {
        categoria = binding.editCategoria.text.toString().trim()
        if (categoria.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese una categoria", Toast.LENGTH_SHORT).show()
        } else {
            agregarCategoriaBD()
        }
    }

    private fun agregarCategoriaBD() {

        progressDialog.setMessage("Agregando categoria")
        progressDialog.show()

        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        val keyId = ref.push().key

        val hasMap = HashMap<String, Any>()
        hasMap["id"] = "${keyId}"
        hasMap["categoria"] = "${categoria}"

        ref.child(keyId!!)
            .setValue(hasMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Se agregó la categoria con éxito", Toast.LENGTH_SHORT)
                    .show()
                binding.editCategoria.setText("")
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()

            }

    }


}