package ipg.pt.trabalhofinal

import androidx.fragment.app.Fragment

class Dados {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var livroSelecionado : Livros? = null
        var pessoaSelecionada : Pessoas? = null
        var paisSelecionado : Paises? = null
    }
}