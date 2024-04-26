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
import com.google.gson.reflect.TypeToken

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
        val control = ArchivosControl(this)
        val contenido = control.leerArchivo()
        //conversion de string a json
        val gson = Gson()
        val tipoLista = object: TypeToken<List<Contact>>() {}.type
        try{
            ArchivosControl.listContact = gson.fromJson(contenido, tipoLista)
        }catch(e:Exception){
            ArchivosControl.listContact = ArrayList<Contact>()
        }

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