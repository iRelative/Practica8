package mx.edu.tecmm.moviles.practica8

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class Editar : AppCompatActivity() {

    var position: Int = 0
    lateinit var txtName: EditText
    lateinit var txtPhoneNumber: EditText
    private lateinit var bd: AppDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = "Modificar Contacto"
        position = intent.getIntExtra("position", -1)
        Log.e("Contact", "Se recibio un ${position}")
        txtName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNomber)

        bd = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "contactosbd")
            .allowMainThreadQueries()
            .build()

        if (position != -1) { // Verificar si se está editando un contacto existente
            val contacto = bd.contactoDao().obtenerContactos()[position]
            txtName.setText(contacto.name)
            txtPhoneNumber.setText(contacto.phoneNumber)

            // Mostrar el botón "Eliminar" solo si se está editando un contacto existente
            findViewById<Button>(R.id.btnDelete).visibility = View.VISIBLE

            // Asociar el método eliminarContacto al botón de eliminar
            findViewById<Button>(R.id.btnDelete).setOnClickListener {
                eliminarContacto()
            }
        }

    }
    fun guardar(v:View){
        val nombre = txtName.text.toString()
        val telefono = txtPhoneNumber.text.toString()
        val contacto = bd.contactoDao().obtenerContactos()[position]
        val contactoActualizado = Contact(contacto.id, nombre, telefono)
        bd.contactoDao().modificar(contactoActualizado)
        Toast.makeText(this, "Se modificó correctamente", Toast.LENGTH_LONG).show()
        finish()
    }
    private fun eliminarContacto() {
        val contacto = bd.contactoDao().obtenerContactos()[position]
        bd.contactoDao().eliminar(contacto)
        Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_LONG).show()
        finish()
    }
}