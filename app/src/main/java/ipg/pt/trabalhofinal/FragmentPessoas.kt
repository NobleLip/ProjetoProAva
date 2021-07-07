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
import ipg.pt.trabalhofinal.databinding.FragmentPessoasBinding


class FragmentPessoas: Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    private lateinit var pessoasViewModel: PessoasViewModel
    private var _binding: FragmentPessoasBinding? = null

    private var adapterPessoas: AdapterPessoa? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dados.fragment = this
        (activity as MainActivity)
        pessoasViewModel =
            ViewModelProvider(this).get(PessoasViewModel::class.java)

        _binding = FragmentPessoasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewPessoas = view.findViewById<RecyclerView>(R.id.RecyclerPessoas)
        adapterPessoas = AdapterPessoa(this)
        recyclerViewPessoas.adapter = adapterPessoas
        recyclerViewPessoas.layoutManager = LinearLayoutManager(requireContext())

        val loaderManager = LoaderManager.getInstance(this)

        loaderManager.initLoader(ID_LOADER_MANAGER_PESSOAS, null, this)

        binding.buttonAdicionarPessoa.setOnClickListener {
            navegaNovoPessoa()
        }

        binding.buttonHomePessoa.setOnClickListener {
            navegaHomePessoa()
        }

        binding.buttonEdit3.setOnClickListener {
            navegaEditDeletePessoa()
        }
    }

    fun navegaNovoPessoa() {
        findNavController().navigate(R.id.action_fragmentPessoas_to_fragmentNovaPessoa)
    }

    fun navegaHomePessoa(){
        findNavController().navigate(R.id.action_fragmentPessoas_to_fragmentHomee)
    }

    fun navegaEditDeletePessoa(){
        findNavController().navigate(R.id.action_fragmentPessoas_to_fragmentEditDeletePessoa)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        adapterPessoas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterPessoas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_PESSOAS= 0
    }
}

