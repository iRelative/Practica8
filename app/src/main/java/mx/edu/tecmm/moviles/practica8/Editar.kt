package mx.edu.tecmm.moviles.practica8

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Editar : AppCompatActivity() {

    var position: Int = 0
    lateinit var txtName: EditText
    lateinit var txtPhoneNumber: EditText
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = "Modificar Contacto"
        position = intent.getIntExtra("position", -1)
        Log.e("Contact", "Se recibio un ${position}")
        txtName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNomber)

        val contact = ProvicionalData.listContact[position]
        txtName.setText(contact.name)
        txtPhoneNumber.setText(contact.phoneNumber)
        this.id = contact.id
    }
    fun save(v: View) {
        val contact = Contact(txtName.text.toString(), txtPhoneNumber.text.toString(), 0 )
        val bd = openOrCreateDatabase("MiBD", MODE_PRIVATE, null)
        val parametros = ContentValues()
        parametros.put("nombre",contact.name)
        parametros.put("telefono",contact.phoneNumber)
        bd.update("Contactos",parametros, "id = ?", arrayOf(id.toString()))

        bd.close()

        Toast.makeText(this, "Se modifico", Toast.LENGTH_LONG).show()
        finish()
    }
}