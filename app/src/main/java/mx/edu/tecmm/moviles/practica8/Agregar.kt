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
import retrofit2.Call
import retrofit2.Callback
import kotlin.random.Random
import retrofit2.Response

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
    fun MGuardar(v: View){
        val nombre = findViewById<EditText>(R.id.txtName).text.toString()
        val telefono = findViewById<EditText>(R.id.txtPhoneNomber).text.toString()

        val retrofit = RetrofitApp.getRetrofit()
        val servicio = retrofit.create(IContacto::class.java)
        val id = Random.nextInt(1, 101)
        val contacto = Contacto(id, nombre, telefono)
        val peticion: Call<Boolean> = servicio.agregar(contacto)

        peticion.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null && res) {
                        Toast.makeText(applicationContext, "Datos Guardados!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Error Guardar Contacto", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Error Servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }
}