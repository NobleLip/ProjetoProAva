package ipg.pt.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import pt.ipg.trabalhofinal.TabelaLivros


data class Livros(var id: Long = -1, var Nome: String, var Avalia: String, var Tipo: String) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaLivros.NOME_LIVRO, Nome)
            put(TabelaLivros.AVALIA, Avalia)
            put(TabelaLivros.TIPO, Tipo)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Livros {

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNomeLivro = cursor.getColumnIndex(TabelaLivros.NOME_LIVRO)
            val posCampoAvalia = cursor.getColumnIndex(TabelaLivros.AVALIA)
            val posCampoTipo = cursor.getColumnIndex(TabelaLivros.TIPO)


            val id = cursor.getLong(posCampoId)
            val Nome = cursor.getString(posCampoNomeLivro)
            val Avalia = cursor.getString(posCampoAvalia)
            val Tipo = cursor.getString(posCampoTipo)

            return Livros(id, Nome, Avalia, Tipo)
        }
    }
}