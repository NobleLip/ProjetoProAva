package ipg.pt.trabalhofinal
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PessoasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pessoas Fragment"
    }
    val text: LiveData<String> = _text
}