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
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    lateinit var rcv: RecyclerView
    lateinit var bd: AppDataBase

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
        bd = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "contactosbd")
            .allowMainThreadQueries()
            .build()
    }

    override fun onResume() {
        super.onResume()
        actualizarRecyclerView()
    }

    fun btnAdd(v: View){
        val intent = Intent(this, Agregar::class.java)
        startActivity(intent)
    }

    fun clickItem(position: Int){
        val intent = Intent(this, Editar::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
    private fun actualizarRecyclerView() {
        val contactos = bd.contactoDao().obtenerContactos()
        Log.w("Contactos", "Hay ${contactos.size} contactos")
        val adaptador = Adapter(this)
        adaptador.actualizarContactos(contactos)
        rcv.adapter = adaptador
        rcv.layoutManager = LinearLayoutManager(this)
    }

}