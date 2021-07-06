package ipg.pt.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ipg.pt.trabalhofinal.databinding.FragmentNovoLivroBinding

class FragmentNovoLivro : Fragment() {
    private var _binding: FragmentNovoLivroBinding? = null


    private lateinit var editTextNome: EditText
    private lateinit var editTextAvalia: EditText
    private lateinit var editTextTipo: EditText

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this

        _binding = FragmentNovoLivroBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextLivrosNome)
        editTextAvalia = view.findViewById(R.id.editTextLivrosAvalia)
        editTextTipo = view.findViewById(R.id.editTextLivrosTipo)

        binding.buttonAdicionarLivro.setOnClickListener {
            AdicionaLivro()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaLivros() {
        findNavController().navigate(R.id.action_fragmentNovoLivro_to_fragmentLivros)

    }

    fun AdicionaLivro() {
        val Nome = editTextNome.text.toString()
        if (Nome.isEmpty()) {
            editTextNome.setError(getString(R.string.Necessario_NomeLivro))
            return
        }

        val Avali = editTextAvalia.text.toString()
        if (Avali.isEmpty()) {
            editTextAvalia.setError(getString(R.string.Necessario_AvaLivro))
            return
        }

        val Tipo = editTextTipo.text.toString()
        if (Tipo.isEmpty()) {
            editTextTipo.setError(getString(R.string.Necessario_TipoLivro))
            return
        }


        val livro = Livros(Nome = Nome, Avalia = Avali, Tipo = Tipo)

        val uri = activity?.contentResolver?.insert(
            ContentProviderApp.ENDERECO_LIVROS,
            livro.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.ErroAdicionarLivro),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        navegaLivros()
    }
}