package jose.cruz.recyclerviewfinal

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var perfiles: ArrayList<Perfil>? = null
    var adapter: PerfilAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView.adapter = PerfilAdapter(perfiles = perfiles, contexto = this)
        RecyclerView.layoutManager = GridLayoutManager(applicationContext, 1)
        RecyclerView.setHasFixedSize(true)
        perfiles = ArrayList()
        adapter = PerfilAdapter(perfiles!!, this)
        RecyclerView.adapter = adapter


        val cache = DiskBasedCache(cacheDir, 1024*1204)
        val network = BasicNetwork(HurlStack())

        val reuquestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = "https://randomuser.me/api/?results=10"

        val jasonObjectPerfiles = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //Log.d("respuesta", response.toString())

                val resultadosJSON = response.getJSONArray("results")

                for (indice in 0..resultadosJSON.length()-1){
                    val perfilJSON = resultadosJSON.getJSONObject(indice)
                    val genero = perfilJSON.getString("gender")
                    val nombreJSON = perfilJSON.getJSONObject("name")
                    val nombre = "${nombreJSON.getString("title")} ${nombreJSON.getString("first")} ${nombreJSON.getString("last")}"
                    val fotoJSON = perfilJSON.getJSONObject("picture")
                    val foto = fotoJSON.getString("large")
                    val locationJSON = perfilJSON.getJSONObject("location")
                    val coordJSON = locationJSON.getJSONObject("coordinates")
                    val latitud = coordJSON.getString("latitude").toDouble()
                    val longitud = coordJSON.getString("longitude").toDouble()

                    perfiles!!.add(Perfil(foto, nombre, genero, longitud, latitud ))

                }

                adapter!!.notifyDataSetChanged()

            }, Response.ErrorListener { error ->
                Log.wtf("error volley", error.localizedMessage)
            })

        reuquestQueue.add(jasonObjectPerfiles)

    }
}
