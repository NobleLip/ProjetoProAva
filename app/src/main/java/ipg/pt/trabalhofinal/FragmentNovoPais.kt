package ipg.pt.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ipg.pt.trabalhofinal.databinding.FragmentNovoPaisBinding

class FragmentNovoPais : Fragment() {
    private var _binding: FragmentNovoPaisBinding? = null


    private lateinit var editTextNome: EditText
    private lateinit var editTextAvaliaPre: EditText
    private lateinit var editTextPreco: EditText

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this

        _binding = FragmentNovoPaisBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextPaisesNome)
        editTextAvaliaPre = view.findViewById(R.id.editTextPaisesAvaliaPre)
        editTextPreco = view.findViewById(R.id.editTextPaisesPreco)

        binding.buttonAdicionarPais.setOnClickListener {
            AdicionaPais()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaPais() {
        findNavController().navigate(R.id.action_fragmentNovoPais_to_fragmentPaises)

    }

    fun AdicionaPais() {
        val Nome = editTextNome.text.toString()
        if (Nome.isEmpty()) {
            editTextNome.setError(getString(R.string.Necessario_NomePais))
            return
        }

        val Preco = editTextPreco.text.toString()
        if (Preco.isEmpty()) {
            editTextPreco.setError(getString(R.string.Necessario_Pre))
            return
        }

        val AvaliaPre = editTextAvaliaPre.text.toString()
        if (AvaliaPre.isEmpty()) {
            editTextAvaliaPre.setError(getString(R.string.Necessario_AvaPre))
            return
        }


        val pais = Paises(Nome = Nome, AvaliaPre = AvaliaPre, Preco = Preco)

        val uri = activity?.contentResolver?.insert(
            ContentProviderApp.ENDERECO_PAISES,
            pais.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.ErroAdicionarPais),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        navegaPais()
    }
}
