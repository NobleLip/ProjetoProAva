
package ipg.pt.trabalhofinal

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import ipg.pt.trabalhofinal.databinding.FragmentEditDeleteLivroBinding
import ipg.pt.trabalhofinal.databinding.FragmentLivrosBinding

class FragmentEditDeleteLivro : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var editTextNome: EditText
    private lateinit var editTextAva: EditText
    private lateinit var editTextTipo: EditText

    private var _binding: FragmentEditDeleteLivroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this
        (activity as MainActivity)
        _binding = FragmentEditDeleteLivroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextLivrosEditNome)
        editTextAva= view.findViewById(R.id.editTextLivrosEditAvalia)
        editTextTipo = view.findViewById(R.id.editTextLivrosTipo)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_LIVROS, null, this)

        editTextNome.setText(Dados.livroSelecionado!!.Nome)
        editTextAva.setText(Dados.livroSelecionado!!.Avalia)
        editTextTipo.setText(Dados.livroSelecionado!!.Tipo)

        binding.buttonEditLivro.setOnClickListener {
            guardar()
        }
        binding.buttonEliminarLivro.setOnClickListener {
            elimina()
        }
    }

    fun navegaListaLivros() {
        findNavController().navigate(R.id.action_fragmentEditDeleteLivro_to_fragmentLivros)
    }

    fun elimina() {
        val uriLivro = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_LIVROS,
            Dados.livroSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLivro,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_livro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.livro_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaLivros()
    }

    fun guardar() {
        val Nome = editTextNome.text.toString()
        if (Nome.isEmpty()) {
            editTextNome.setError(getString(R.string.Necessario_NomeLivro))
            editTextNome.requestFocus()
            return
        }

        val Avalia = editTextAva.text.toString()
        if (Avalia.toInt() > 5 || Avalia.toInt() < 0){
            editTextAva.setError(getString(R.string.AvaliaErro))
            return
        }
        if (Avalia.isEmpty()) {
            editTextAva.setError(getString(R.string.Necessario_AvaLivro))
            editTextAva.requestFocus()
            return
        }

        val Tipo = editTextTipo.text.toString()
        if (Tipo.isEmpty()) {
            editTextTipo.setError(getString(R.string.Necessario_TipoLivro))
            editTextTipo.requestFocus()
            return
        }

        val livro = Dados.livroSelecionado!!
        livro.Nome = Nome
        livro.Avalia= Avalia
        livro.Tipo = Tipo

        val uriLivro = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_LIVROS,
            livro.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLivro,
            livro.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_livro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.livro_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaLivros()
    }


    companion object {
        const val ID_LOADER_MANAGER_LIVROS = 0
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_LIVROS,
            TabelaLivros.TODOS_CAMPOS,
            null, null,
            TabelaLivros.NOME_LIVRO
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        //TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }
}