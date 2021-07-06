package ipg.pt.trabalhofinal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BDOpenHP(context: Context?)
    : SQLiteOpenHelper(context, NOME_BD, null, VERSAO) {

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            TabelaLivros(db).cria()
            TabelaPessoas(db).cria()
            TabelaPaises(db).cria()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val NOME_BD = "CovidInformer.db"
        const val VERSAO = 1
    }
}