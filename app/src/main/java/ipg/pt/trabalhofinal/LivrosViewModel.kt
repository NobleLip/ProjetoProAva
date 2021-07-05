package ipg.pt.trabalhofinal
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LivrosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Livros Fragment"
    }
    val text: LiveData<String> = _text
}