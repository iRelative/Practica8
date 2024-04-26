package mx.edu.tecmm.moviles.practica8

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder

class ArchivosControl (val context: Context){
    val nombre_archivo = "datos.json"
    companion object{
        var listContact = ArrayList<Contact>()
    }
    fun guardar(contenido:String):Boolean{
        var res = false
        try {
            val archivo = File(context.getExternalFilesDir(null), nombre_archivo)
            val fos = FileOutputStream(archivo, false)
            val escribe = OutputStreamWriter(fos)
            escribe.write(contenido)
            escribe.close()
            res = true
        }catch (e:Exception){
            Log.e("ERROR ARCHIVO", "Ocurrio un error al guardar")
        }
        return res
    }

    fun leerArchivo():String{
        val contenido = StringBuilder()
        try {
            val archivo = File(context.getExternalFilesDir(null), nombre_archivo)
            if(archivo.exists()){
                val fis = FileInputStream(archivo)
                val lector = InputStreamReader(fis)
                var buffer = CharArray(4096)
                var cantidad = lector.read(buffer)
                while(cantidad != -1){
                    contenido.append(buffer, 0, cantidad)
                    cantidad = lector.read(buffer)
                }
                lector.close()
            }
        }catch (e:Exception){
            Log.e("ERROR ARCHIVO", "Error de lectura")
        }
        return contenido.toString()
    }
}