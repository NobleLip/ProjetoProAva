package ipg.pt.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import pt.ipg.trabalhofinal.TabelaPaises


data class Paises (var id: Long = -1, var Nome: String, var AvaliaPre: Int, var Preco: Float) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaPaises.NOME, Nome)
            put(TabelaPaises.AVALIAPRE, AvaliaPre)
            put(TabelaPaises.PRE, Preco)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Paises {

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNome = cursor.getColumnIndex(TabelaPaises.NOME)
            val posCampoAvaliaPre = cursor.getColumnIndex(TabelaPaises.AVALIAPRE)
            val posCampoPre = cursor.getColumnIndex(TabelaPaises.PRE)


            val id = cursor.getLong(posCampoId)
            val Nome = cursor.getString(posCampoNome)
            val AvaliaPre = cursor.getInt(posCampoAvaliaPre)
            val Preco = cursor.getFloat(posCampoPre)

            return Paises(id, Nome, AvaliaPre, Preco)
        }
    }
}