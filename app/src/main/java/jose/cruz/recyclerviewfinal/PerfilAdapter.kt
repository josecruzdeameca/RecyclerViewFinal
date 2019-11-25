package jose.cruz.recyclerviewfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.perfil_layout.view.*

class PerfilAdapter (perfiles:ArrayList<Perfil>, contexto:Context): RecyclerView.Adapter<PerfilAdapter.ViewHolder>(){

    var perfiles:ArrayList<Perfil>?=null
    var contexto:Context?=null

    init {
        this.perfiles=perfiles
        this.contexto=contexto
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vistaPerfil: View = LayoutInflater.from(contexto)
            .inflate(R.layout.perfil_layout, parent, false)

        val perfilViewHolder = ViewHolder(vistaPerfil)

        vistaPerfil.tag = perfilViewHolder

        return perfilViewHolder
    }

    override fun getItemCount(): Int {
        return perfiles!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(perfiles!![position].foto).into(holder.foto)
        holder.nombre!!.text = perfiles!![position].nombre
        holder.genero!!.text = perfiles!![position].genero
    }

    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){
        var foto:ImageView?=null
        var nombre:TextView?=null
        var genero:TextView?=null

        init {
            foto = vista.iv_foto
            nombre = vista.tv_nombre
            genero = vista.tv_genero
        }

    }


}