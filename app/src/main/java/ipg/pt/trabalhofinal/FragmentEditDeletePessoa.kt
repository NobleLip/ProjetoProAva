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
import ipg.pt.trabalhofinal.databinding.FragmentEditDeletePessoaBinding
import ipg.pt.trabalhofinal.databinding.FragmentPessoasBinding

class FragmentEditDeletePessoa : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var editTextNome: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextCC: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextNumVacinas: EditText

    private var _binding: FragmentEditDeletePessoaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this
        (activity as MainActivity)
        _binding = FragmentEditDeletePessoaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
        //return inflater.inflate(R.layout.fragment_edit_delete_pais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextEditPessoaNome)
        editTextDataNascimento= view.findViewById(R.id.editTextEditDataNascimento)
        editTextMorada = view.findViewById(R.id.editTextEditPessoaMorada)
        editTextCC = view.findViewById(R.id.editTextEditPessoaCC)
        editTextContacto= view.findViewById(R.id.editTextEditPessoaContacto)
        editTextNumVacinas = view.findViewById(R.id.editTextEditPessoaNumPessoa)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PESSOAS, null, this)

        editTextNome.setText(Dados.pessoaSelecionada!!.Nome)
        editTextDataNascimento.setText(Dados.pessoaSelecionada!!.DataNascimento)
        editTextMorada.setText(Dados.pessoaSelecionada!!.Morada)
        editTextCC.setText(Dados.pessoaSelecionada!!.CarCida)
        editTextContacto.setText(Dados.pessoaSelecionada!!.Contacto)
        editTextNumVacinas.setText(Dados.pessoaSelecionada!!.Numero_Vacinas)

        binding.buttonEditPessoa.setOnClickListener {
            guardar()
        }
        binding.buttonEliminarPessoa.setOnClickListener {
            elimina()
        }
    }

    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_fragmentEditDeletePessoa_to_fragmentPessoas)
    }

    fun elimina() {
        val uriPessoa = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_PESSOAS,
            Dados.pessoaSelecionada!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriPessoa,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_pessoa,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.pessoa_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()
    }

    fun guardar() {
        val Nome = editTextNome.text.toString()
        if (Nome.isEmpty()) {
            editTextNome.setError(getString(R.string.Necessario_NomePessoa))
            editTextNome.requestFocus()
            return
        }

        val DataNascimento = editTextDataNascimento.text.toString()
        if (DataNascimento.isEmpty()) {
            editTextDataNascimento.setError(getString(R.string.Necessario_Data))
            editTextDataNascimento.requestFocus()
            return
        }

        val Morada = editTextMorada.text.toString()
        if (Morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.Necessario_Morada))
            editTextMorada.requestFocus()
            return
        }
        val CC = editTextCC.text.toString()
        if (CC.length < 8 || CC.length > 8){
            editTextCC.setError(getString(R.string.CCInvalido))
            return
        }
        if (CC.isEmpty()) {
            editTextCC.setError(getString(R.string.Necessario_CC))
            editTextCC.requestFocus()
            return
        }

        val Contacto = editTextContacto.text.toString()
        if (Contacto.length < 9 || Contacto.length > 9){
            editTextContacto.setError(getString(R.string.ContactoInvalido))
            return
        }
        if (Contacto.isEmpty()) {
            editTextContacto.setError(getString(R.string.Necessario_Contacto))
            editTextContacto.requestFocus()
            return
        }

        val NumVacinas = editTextNumVacinas.text.toString()
        if (NumVacinas.toInt() > 2 || NumVacinas.toInt() < 0){
            editTextNumVacinas.setError(getString(R.string.Vacina_Max))
            return
        }
        if (NumVacinas.isEmpty()) {
            editTextNumVacinas.setError(getString(R.string.Necessario_NumVacina))
            editTextNumVacinas.requestFocus()
            return
        }

        val pessoa = Dados.pessoaSelecionada!!
        pessoa.Nome = Nome
        pessoa.DataNascimento= DataNascimento
        pessoa.Morada = Morada
        pessoa.CarCida = CC
        pessoa.Contacto = Contacto
        pessoa.Numero_Vacinas = NumVacinas

        val uriPessoa = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_PESSOAS,
            pessoa.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriPessoa,
            pessoa.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_pessoa,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.pessoa_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()
    }


    companion object {
        const val ID_LOADER_MANAGER_PESSOAS = 0
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_PESSOAS,
            TabelaPessoas.TODOS_CAMPOS,
            null, null,
            TabelaPessoas.NOME_PESSOA
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        //TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }
}