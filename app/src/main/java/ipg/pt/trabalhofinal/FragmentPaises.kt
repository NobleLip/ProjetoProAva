package ipg.pt.trabalhofinal

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipg.pt.trabalhofinal.databinding.FragmentPaisesBinding


class FragmentPaises: Fragment() {

    private lateinit var paisesViewModel: PaisesViewModel
    private var _binding: FragmentPaisesBinding? = null

    private var adapterPaises: AdapterPaises? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this
        (activity as MainActivity)
        paisesViewModel =
            ViewModelProvider(this).get(PaisesViewModel::class.java)

        _binding = FragmentPaisesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewPaises = view.findViewById<RecyclerView>(R.id.RecyclerPaises)
        adapterPaises = AdapterPaises(this)
        recyclerViewPaises.adapter = adapterPaises
        recyclerViewPaises.layoutManager = LinearLayoutManager(requireContext())

        //val loaderManager = LoaderManager.getInstance(this)

        //loaderManager.initLoader(ID_LOADER_MANAGER_PAISES, null, this)

        binding.buttonAdicionarPais.setOnClickListener {
            navegaNovoPais()
        }

        binding.buttonHomePais.setOnClickListener {
            navegaHomePais()
        }
    }

    fun navegaNovoPais() {
        findNavController().navigate(R.id.action_fragmentPaises_to_fragmentNovoPais)
    }

    fun navegaHomePais(){
        findNavController().navigate(R.id.action_fragmentPaises_to_fragmentHomee)
    }

    fun navegaAlterarPais() {
        //todo: navegar para o fragmento da edição de um enfermeiro
    }

    fun navegaEliminarPais() {
        //todo: navegar para o fragmento para confirmar eliminação de um enfermeiro
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_PAISES,
            TabelaPaises.TODOS_CAMPOS,
            null, null,
            TabelaPaises.NOME
        )
    }

    fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterPaises!!.cursor = data
    }

    fun onLoaderReset(loader: Loader<Cursor>) {
        adapterPaises!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_PAISES= 0
    }
}

