package mx.edu.tecmm.moviles.practica8

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.log

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
        val contact = Contact(name.text.toString(), phoneNumber.text.toString(),0)

        val bd = openOrCreateDatabase("MiBD", MODE_PRIVATE, null)
        val parametros = ContentValues()
        parametros.put("nombre",contact.name)
        parametros.put("telefono",contact.phoneNumber)
        bd.insert("Contactos", null, parametros)

        bd.close()

        Toast.makeText(this, "Se Grabo Correctamente", Toast.LENGTH_LONG).show()

        name.setText("")
        phoneNumber.setText("")

        Log.v("MiApp", "Se guardo")

        finish()
    }
}