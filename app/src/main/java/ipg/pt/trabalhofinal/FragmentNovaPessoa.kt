package ipg.pt.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ipg.pt.trabalhofinal.databinding.FragmentNovaPessoaBinding

class FragmentNovaPessoa : Fragment() {
    private var _binding: FragmentNovaPessoaBinding? = null


    private lateinit var editTextNome: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var editTextCC: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextNumVacinas: EditText

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this

        _binding = FragmentNovaPessoaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextPessoaNome)
        editTextMorada = view.findViewById(R.id.editTextPessoaMorada)
        editTextDataNascimento = view.findViewById(R.id.editTextDataNascimento)
        editTextCC= view.findViewById(R.id.editTextPessoaCC)
        editTextContacto = view.findViewById(R.id.editTextPessoaContacto)
        editTextNumVacinas = view.findViewById(R.id.editTextPessoaNumPessoa)

        binding.buttonAdicionarPessoa.setOnClickListener {
            AdicionaPessoa()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaPessoas() {
        findNavController().navigate(R.id.action_fragmentNovaPessoa_to_fragmentPessoas)

    }

    fun AdicionaPessoa() {
        val Nome = editTextNome.text.toString()
        if (Nome.isEmpty()) {
            editTextNome.setError(getString(R.string.Necessario_NomePessoa))
            return
        }

        val Morada = editTextMorada.text.toString()
        if (Morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.Necessario_Morada))
            return
        }

        val DataNascimento = editTextDataNascimento.text.toString()
        if (DataNascimento.isEmpty()) {
            editTextDataNascimento.setError(getString(R.string.Necessario_Data))
            return
        }

        val CC = editTextCC.text.toString()
        if (CC.isEmpty()) {
            editTextCC.setError(getString(R.string.Necessario_CC))
            return
        }

        val Contacto = editTextContacto.text.toString()
        if (Contacto.isEmpty()) {
            editTextContacto.setError(getString(R.string.Necessario_Contacto))
            return
        }

        val NumVacina = editTextNumVacinas.text.toString()
        if (NumVacina.isEmpty()) {
            editTextNumVacinas.setError(getString(R.string.Necessario_NumVacina))
            return
        }


        val pessoa = Pessoas(
            Nome = Nome, DataNascimento = DataNascimento, Morada = Morada, CarCida = CC,
            Contacto = Contacto, Numero_Vacinas = NumVacina)

        val uri = activity?.contentResolver?.insert(
            ContentProviderApp.ENDERECO_PESSOAS,
            pessoa.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.ErroAdicionarPessoa),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        navegaPessoas()
    }
}