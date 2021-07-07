package ipg.pt.trabalhofinal
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaisesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Paises Fragment"
    }
    val text: LiveData<String> = _text
}