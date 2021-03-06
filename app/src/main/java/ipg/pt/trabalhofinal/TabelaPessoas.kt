package ipg.pt.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABLE ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_PESSOA TEXT NOT NULL, $DATA_NASCIMENTO TEXT NOT NULL, $MORADA TEXT NOT NULL, $CC TEXT NOT NULL, $CONTACTO TEXT NOT NULL, $NUM_VACINAS TEXT NOT NULL)")

    }
    fun drop(){
        db.execSQL("DROP TABLE ${TabelaLivros.NOME_TABLE}")
    }
    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABLE , null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABLE , values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABLE , whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABLE , columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABLE = "PessoasVacinadas"
        const val NOME_PESSOA = "Nome"
        const val DATA_NASCIMENTO = "DataNascimento"
        const val MORADA = "Morada"
        const val CC = "CarCida"
        const val CONTACTO = "contacto"
        const val NUM_VACINAS = "Numero_Vacinas" //Numero de Vacinas Tomadas
        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, NOME_PESSOA, DATA_NASCIMENTO, MORADA, CC, CONTACTO, NUM_VACINAS)
    }
}