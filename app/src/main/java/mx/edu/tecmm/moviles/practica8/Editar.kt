package mx.edu.tecmm.moviles.practica8

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class Editar : AppCompatActivity() {

    var position: Int = 0
    lateinit var txtName: EditText
    lateinit var txtPhoneNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = "Modificar Contacto"
        position = intent.getIntExtra("position", -1)
        Log.e("Contact", "Se recibio un ${position}")
        txtName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNomber)

        val contact = ArchivosControl.listContact[position]
        txtName.setText(contact.name)
        txtPhoneNumber.setText(contact.phoneNumber)

    }
    fun save(v: View) {
        val contact = Contact(txtName.text.toString(), txtPhoneNumber.text.toString())
        ArchivosControl.listContact.set(position, contact)
        val gson = Gson()
        val control = ArchivosControl(this)
        val contenido = gson.toJson(ArchivosControl.listContact)
        val res = control.guardar(contenido)
        val msg = if(res) "Datos Modificados" else "Error Modificar"
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        finish()
    }
}