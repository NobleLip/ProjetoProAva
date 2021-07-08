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
import ipg.pt.trabalhofinal.databinding.FragmentEditDeletePaisBinding
import ipg.pt.trabalhofinal.databinding.FragmentPaisesBinding

class FragmentEditDeletePais : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var editTextNome: EditText
    private lateinit var editTextPre: EditText
    private lateinit var editTextAvaPre: EditText

    private var _binding: FragmentEditDeletePaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this
        (activity as MainActivity)
        _binding = FragmentEditDeletePaisBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
        //return inflater.inflate(R.layout.fragment_edit_delete_pais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextEditPaisesNome)
        editTextPre= view.findViewById(R.id.editTextEditPaisesPreco)
        editTextAvaPre = view.findViewById(R.id.editTextPaisesEditAvaliaPre)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PAISES, null, this)

        editTextNome.setText(Dados.paisSelecionado!!.Nome)
        editTextPre.setText(Dados.paisSelecionado!!.Preco)
        editTextAvaPre.setText(Dados.paisSelecionado!!.AvaliaPre)

        binding.buttonEditPais.setOnClickListener {
            guardar()
        }
        binding.buttonEliminarPais.setOnClickListener {
            elimina()
        }
    }

    fun navegaListaPaises() {
        findNavController().navigate(R.id.action_fragmentEditDeletePais_to_fragmentPaises)
    }

    fun elimina() {
        val uriPais = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_PAISES,
            Dados.paisSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriPais,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_pais,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.pais_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPaises()
    }

    fun guardar() {
        val Nome = editTextNome.text.toString()
        if (Nome.isEmpty()) {
            editTextNome.setError(getString(R.string.Necessario_NomePais))
            editTextNome.requestFocus()
            return
        }

        val Pre = editTextPre.text.toString()
        if (Pre.isEmpty()) {
            editTextPre.setError(getString(R.string.Necessario_Pre))
            editTextPre.requestFocus()
            return
        }

        val AvaliaPre = editTextAvaPre.text.toString()

        if (AvaliaPre.toInt() > 5 || AvaliaPre.toInt() < 0){
            editTextAvaPre.setError(getString(R.string.AvaliaErro))
            return
        }

        if (AvaliaPre.isEmpty()) {
            editTextAvaPre.setError(getString(R.string.Necessario_AvaPre))
            editTextAvaPre.requestFocus()
            return
        }

        val pais = Dados.paisSelecionado!!
        pais.Nome = Nome
        pais.AvaliaPre = AvaliaPre
        pais.Preco = Pre

        val uriPais = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_PAISES,
            pais.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriPais,
            pais.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_pais,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.pais_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPaises()
    }


    companion object {
        const val ID_LOADER_MANAGER_PAISES= 0
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_PAISES,
            TabelaPaises.TODOS_CAMPOS,
            null, null,
            TabelaPaises.NOME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        //TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }
}