package ipg.pt.trabalhofinal


import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView


class AdapterPaises(val fragment: FragmentPaises) : RecyclerView.Adapter<AdapterPaises.ViewHolderPaises>() {

    var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPaises(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewPaisNome)
        private val textViewAvaPre = itemView.findViewById<TextView>(R.id.textViewPaisAva)
        private val textViewPre = itemView.findViewById<TextView>(R.id.textViewPaisPreco)

        private lateinit var pais: Paises

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaPais(pais: Paises) {
            this.pais = pais

            textViewNome.text = pais.Nome
            textViewAvaPre.text = pais.AvaliaPre.toString()
            textViewPre.text = pais.Preco.toString()
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            Dados.paisSelecionado = pais
        }

        private fun desSeleciona() {
            selecionado = null
        }

        companion object {
            var selecionado: ViewHolderPaises? = null;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPaises {
        val itemPais = fragment.layoutInflater.inflate(R.layout.item_paises, parent, false)
        return ViewHolderPaises(itemPais)
    }


    override fun onBindViewHolder(holder: ViewHolderPaises, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaPais(Paises.fromCursor(cursor!!))
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}