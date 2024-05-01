package mx.edu.tecmm.moviles.practica8

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var rcv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rcv = findViewById(R.id.rvContact)

    }

    override fun onResume() {
        super.onResume()

        ProvicionalData.listContact.clear()

        val bd = openOrCreateDatabase("MiBD", MODE_PRIVATE, null)

        bd.execSQL("CREATE TABLE IF NOT EXISTS " +
                "Contactos(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, telefono TEXT NOT NULL);")

        val cursor = bd.rawQuery("SELECT * FROM Contactos", null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val telefono = cursor.getString(2)
            val contacto = Contact(nombre, telefono, id)
            ProvicionalData.listContact.add(contacto)

        }

        Log.w("Contactos", "Hay ${ProvicionalData.listContact.size} contactos")


        val gson = Gson()
        val contenido = gson.toJson(ProvicionalData.listContact)
        Log.v("Pruebas", contenido)
        bd.close()

        rcv.adapter = Adapter(this)
        rcv.layoutManager = LinearLayoutManager(this)

    }

    fun btnAdd(v: View) {
        val intent = Intent(this, Agregar::class.java)
        startActivity(intent)
    }

    fun clickItem(position: Int) {
        val intent = Intent(this, Editar::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

}





