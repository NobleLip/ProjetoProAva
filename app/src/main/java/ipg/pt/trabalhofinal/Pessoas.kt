package ipg.pt.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import android.text.Editable


data class Pessoas(var id: Long = -1, var Nome: String, var DataNascimento: String, var Morada: String, var CarCida: String, var Contacto: String, var Numero_Vacinas: String) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaPessoas.NOME_PESSOA, Nome)
            put(TabelaPessoas.DATA_NASCIMENTO, DataNascimento)
            put(TabelaPessoas.MORADA, Morada)
            put(TabelaPessoas.CC, CarCida)
            put(TabelaPessoas.CONTACTO, Contacto)
            put(TabelaPessoas.NUM_VACINAS, Numero_Vacinas)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Pessoas {

            val posCampoId = cursor.getColumnIndex(BaseColumns._ID)
            val posCampoNomePessoa= cursor.getColumnIndex(TabelaPessoas.NOME_PESSOA)
            val posCampoDataNascimento = cursor.getColumnIndex(TabelaPessoas.DATA_NASCIMENTO)
            val posCampoMorada = cursor.getColumnIndex(TabelaPessoas.MORADA)
            val posCampoCarCida= cursor.getColumnIndex(TabelaPessoas.CC)
            val posCampoContacto = cursor.getColumnIndex(TabelaPessoas.CONTACTO)
            val posCampoNumVacinas = cursor.getColumnIndex(TabelaPessoas.NUM_VACINAS)


            val id = cursor.getLong(posCampoId)
            val Nome = cursor.getString(posCampoNomePessoa)
            val DataNascimento = cursor.getString(posCampoDataNascimento)
            val Morada = cursor.getString(posCampoMorada)
            val CarCida = cursor.getString(posCampoCarCida)
            val Contacto = cursor.getString(posCampoContacto)
            val Numero_Vacinas = cursor.getString(posCampoNumVacinas)

            return Pessoas(id, Nome, DataNascimento, Morada, CarCida, Contacto, Numero_Vacinas)
        }
    }
}