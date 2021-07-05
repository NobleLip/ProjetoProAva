package ipg.pt.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ipg.pt.trabalhofinal.databinding.FragmentHomeBinding


class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLivros.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomee_to_fragmentLivros)
        }

        binding.buttonPaises.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomee_to_fragmentPaises)
        }

        binding.buttonPessoas.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomee_to_fragmentPessoas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}