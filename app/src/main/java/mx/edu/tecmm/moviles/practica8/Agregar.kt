package mx.edu.tecmm.moviles.practica8

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room

class Agregar : AppCompatActivity() {

    private lateinit var bd: AppDataBase
    private lateinit var txtNombre: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var btnEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar)

        txtNombre = findViewById(R.id.txtName)
        txtTelefono = findViewById(R.id.txtPhoneNomber)

        // Referencia al botón eliminar
        btnEliminar = findViewById(R.id.btnDelete)

        // Ocultar el botón eliminar en la actividad de agregar
        btnEliminar.visibility = View.GONE

        bd = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "contactosbd")
            .allowMainThreadQueries()
            .build()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun guardar(v: View){
        val nombre = txtNombre.text.toString()
        val telefono = txtTelefono.text.toString()
        val contacto = Contact(0, nombre, telefono)
        bd.contactoDao().agregar(contacto)
        Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_LONG).show()
        finish()
    }
}