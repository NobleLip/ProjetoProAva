package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaLivros(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABLE( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_LIVRO TEXT NOT NULL, $AVALIA TEXT NOT NULL, $TIPO TEXT NOT NULL)")

    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABLE = "Livros"
        const val NOME_LIVRO = "Nome"
        const val AVALIA = "Avalia"
        const val TIPO = "Tipo"
        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, NOME_LIVRO, AVALIA, TIPO)
    }
}