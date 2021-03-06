package ipg.pt.trabalhofinal


import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ipg.pt.trabalhofinal.R
import ipg.pt.trabalhofinal.databinding.FragmentLivrosBinding


class AdapterLivros(val fragment: FragmentLivros) : RecyclerView.Adapter<AdapterLivros.ViewHolderLivros>() {

     var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderLivros(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewLivroNome)
        private val textViewAva = itemView.findViewById<TextView>(R.id.textViewLivroAva)
        private val textViewTipo = itemView.findViewById<TextView>(R.id.textViewLivroTipo)

        private lateinit var livro: Livros

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaLivro(livro: Livros) {
            this.livro = livro

            textViewNome.text = livro.Nome
            textViewAva.text = livro.Avalia
            textViewTipo.text = livro.Tipo
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            Dados.livroSelecionado = livro
            itemView.setBackgroundResource(R.color.gray)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderLivros? = null;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLivros {
        val itemLivro= fragment.layoutInflater.inflate(R.layout.item_livro, parent, false)
        return ViewHolderLivros(itemLivro)
    }


    override fun onBindViewHolder(holder: ViewHolderLivros, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaLivro(Livros.fromCursor(cursor!!))
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

}