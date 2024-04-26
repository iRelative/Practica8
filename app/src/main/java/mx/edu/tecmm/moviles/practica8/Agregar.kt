package mx.edu.tecmm.moviles.practica8

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class Agregar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun save(v: View){
        val name = findViewById<EditText>(R.id.txtName)
        val phoneNumber = findViewById<EditText>(R.id.txtPhoneNomber)
        val contact = Contact(name.text.toString(), phoneNumber.text.toString())
        ArchivosControl.listContact.add(contact)
        val gson = Gson()
        val control = ArchivosControl(this)
        val contenido = gson.toJson(ArchivosControl.listContact)
        val res = control.guardar(contenido)
        val msg = if(res) "Datos Guardados"
        else "Error al Guardar Datos"
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        val archivo = control.leerArchivo()
        Log.v("Contenido del Archivo", archivo)
        finish()
    }
}