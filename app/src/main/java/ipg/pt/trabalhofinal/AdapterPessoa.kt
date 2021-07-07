package ipg.pt.trabalhofinal


import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AdapterPessoa(val fragment: FragmentPessoas) : RecyclerView.Adapter<AdapterPessoa.ViewHolderPessoas>() {

    var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPessoas(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewPessoaNome)
        private val textViewMorada = itemView.findViewById<TextView>(R.id.textViewPessoaMorada)
        private val textViewData = itemView.findViewById<TextView>(R.id.textViewPessoaDataNascimento)
        private val textViewCC= itemView.findViewById<TextView>(R.id.textViewPessoaCC)
        private val textViewContacto = itemView.findViewById<TextView>(R.id.textViewPessoaContacto)
        private val textViewVacinas = itemView.findViewById<TextView>(R.id.textViewPessoaNumVacinas)

        private lateinit var pessoa: Pessoas

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaPessoa(pessoa: Pessoas) {
            this.pessoa = pessoa

            textViewNome.text = pessoa.Nome
            textViewMorada.text = pessoa.Morada
            textViewData.text = pessoa.DataNascimento.toString()
            textViewCC.text = pessoa.CarCida
            textViewContacto.text = pessoa.Contacto
            textViewVacinas.text = pessoa.Numero_Vacinas.toString()
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            Dados.pessoaSelecionada = pessoa
        }

        private fun desSeleciona() {
            selecionado = null
        }

        companion object {
            var selecionado: ViewHolderPessoas? = null;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPessoas {
        val itemPessoa = fragment.layoutInflater.inflate(R.layout.item_pessoa, parent, false)
        return ViewHolderPessoas(itemPessoa)
    }


    override fun onBindViewHolder(holder: ViewHolderPessoas, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaPessoa(Pessoas.fromCursor(cursor!!))
    }


    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}