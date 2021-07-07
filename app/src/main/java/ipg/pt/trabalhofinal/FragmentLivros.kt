package ipg.pt.trabalhofinal

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipg.pt.trabalhofinal.databinding.FragmentLivrosBinding


class FragmentLivros : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    private lateinit var livrosViewModel: LivrosViewModel
    private var _binding: FragmentLivrosBinding? = null

    private var adapterLivros: AdapterLivros? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this
        (activity as MainActivity)
        livrosViewModel =
            ViewModelProvider(this).get(LivrosViewModel::class.java)

        _binding = FragmentLivrosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewLivros = view.findViewById<RecyclerView>(R.id.RecyclerLivros)
        adapterLivros = AdapterLivros(this)
        recyclerViewLivros.adapter = adapterLivros
        recyclerViewLivros.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)
        loaderManager.initLoader(ID_LOADER_MANAGER_LIVROS, null, this)

        binding.buttonAdicionar.setOnClickListener {
            navegaNovoLivro()
        }

        binding.buttonHome.setOnClickListener {
            navegaHome()
        }
    }

    fun navegaNovoLivro() {
        findNavController().navigate(R.id.action_fragmentLivros_to_fragmentNovoLivro)
    }

    fun navegaHome(){
        findNavController().navigate(R.id.action_fragmentLivros_to_fragmentHomee)
    }

    fun navegaAlterarLivro() {
        //todo: navegar para o fragmento da edição de um enfermeiro
    }

    fun navegaEliminarLivro() {
        //todo: navegar para o fragmento para confirmar eliminação de um enfermeiro
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        adapterLivros!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterLivros!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_LIVROS = 0
    }
}

