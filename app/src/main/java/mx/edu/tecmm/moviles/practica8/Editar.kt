package mx.edu.tecmm.moviles.practica8

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Editar : AppCompatActivity() {

    var position: Int = 0
    lateinit var txtName: EditText
    lateinit var txtPhoneNumber: EditText
    lateinit var contactos: MutableList<Contacto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = "Modificar Contacto"
        position = intent.getIntExtra("position", -1)
        Log.e("Contact", "Se recibio un ${position}")
        txtName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNomber)

        // Obteniendo la lista de contactos de ProvisionalDatos
        contactos = ProvicionalData.listContact

        val contacto = contactos[position]
        txtName.setText(contacto.nombre)
        txtPhoneNumber.setText(contacto.telefono)

        // Mostrar el botón "Eliminar" solo si se está editando un contacto existente
        findViewById<Button>(R.id.btnEliminar).visibility = View.VISIBLE
        // Asociar el método eliminarContacto al botón de eliminar
        findViewById<Button>(R.id.btnEliminar).setOnClickListener {
            eliminarContacto()
        }

    }
    fun guardar(v: View) {
        val nombre = txtName.text.toString()
        val telefono = txtPhoneNumber.text.toString()
        val retrofit = RetrofitApp.getRetrofit()
        val servicio = retrofit.create(IContacto::class.java)
        val contacto = Contacto(contactos[position].idcontacto, nombre, telefono)

        val peticion: Call<Boolean> = servicio.modificar(contacto)

        peticion.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null && res) {
                        // Modificar el contacto en la lista local
                        contactos[position] = contacto
                        Toast.makeText(this@Editar, "Se modificó correctamente", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@Editar, "Error al modificar el contacto", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@Editar, "Error en la respuesta del servidor", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }

    private fun eliminarContacto() {

        val retrofit = RetrofitApp.getRetrofit()
        val servicio = retrofit.create(IContacto::class.java)

        val idContacto = contactos[position].idcontacto

        val peticion: Call<Boolean> = servicio.eliminar(idContacto)

        peticion.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null && res) {
                        // Eliminar el contacto de la lista local
                        contactos.removeAt(position)
                        Toast.makeText(this@Editar, "Contacto eliminado", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@Editar, "Error Eliminar Contacto", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@Editar, "Error Servidor", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }

}