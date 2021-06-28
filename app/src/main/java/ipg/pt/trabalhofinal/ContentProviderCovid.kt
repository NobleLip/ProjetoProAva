package pt.ipg.trabalhofinal

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderCovid : ContentProvider() {

    private var BDOpenHelper : BDOpenHP? = null

    override fun onCreate(): Boolean {
        BDOpenHelper = BDOpenHP(context)
        return true
    }


    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = BDOpenHelper!!.readableDatabase

        return when (getUriMatcher().match(uri)){

            URI_LIVROS -> TabelaLivros(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_LIVROS_ESPECIFICO -> TabelaLivros(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_PAISES -> TabelaPaises(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_PAISES_ESPECIFICO -> TabelaPaises(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_PESSOAS -> TabelaPessoas(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_PESSOAS_ESPECIFICA-> TabelaPessoas(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)){
            URI_LIVROS -> "$MULTIPLOS_ITEMS/$LIVROS"
            URI_LIVROS_ESPECIFICO -> "$UNICO_ITEM/$LIVROS"
            URI_PESSOAS -> "$MULTIPLOS_ITEMS/$PESSOAS"
            URI_PESSOAS_ESPECIFICA -> "$UNICO_ITEM/$PESSOAS"
            URI_PAISES -> "$MULTIPLOS_ITEMS/$PAISES"
            URI_PAISES_ESPECIFICO -> "$UNICO_ITEM/$PAISES"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = BDOpenHelper!!.writableDatabase

        val id =  when (getUriMatcher().match(uri)){

            URI_LIVROS -> TabelaLivros(bd).insert(values!!)

            URI_PESSOAS -> TabelaPessoas(bd).insert(values!!)

            URI_PAISES -> TabelaPaises(bd).insert(values!!)

            else -> -1L
        }

        if(id == -1L) return null

        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = BDOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){

            URI_LIVROS_ESPECIFICO -> TabelaLivros(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_PESSOAS_ESPECIFICA -> TabelaPessoas(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_PAISES_ESPECIFICO -> TabelaPaises(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = BDOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){

            URI_LIVROS_ESPECIFICO -> TabelaLivros(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_PESSOAS_ESPECIFICA -> TabelaPessoas(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_PAISES_ESPECIFICO -> TabelaPaises(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0
        }
    }

    companion object{

        const val AUTHORITY = "pt.ipg.trabalhofinal"
        const val LIVROS = "Livros"
        const val PESSOAS = "Pessoas"
        const val PAISES = "Paises"

        private const val URI_LIVROS= 100
        private const val URI_LIVROS_ESPECIFICO = 101
        private const val URI_PESSOAS = 200
        private const val URI_PESSOAS_ESPECIFICA = 201
        private const val URI_PAISES = 300
        private const val URI_PAISES_ESPECIFICO= 301


        private const val MULTIPLOS_ITEMS = "vnd.adroid.cursor.dir"
        private const val UNICO_ITEM = "vnd.android.cursor.item"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        public val ENDERECO_LIVROS = Uri.withAppendedPath(ENDERECO_BASE, LIVROS)
        public val ENDERECO_PESSOAS = Uri.withAppendedPath(ENDERECO_BASE, PESSOAS)
        public val ENDERECO_PAISES= Uri.withAppendedPath(ENDERECO_BASE, PAISES)

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)



            uriMatcher.addURI(AUTHORITY, LIVROS, URI_LIVROS)
            uriMatcher.addURI(AUTHORITY,"$LIVROS/#", URI_LIVROS_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, PESSOAS, URI_PESSOAS)
            uriMatcher.addURI(AUTHORITY, "$PESSOAS/#", URI_PESSOAS_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, PAISES, URI_PAISES)
            uriMatcher.addURI(AUTHORITY,"$PAISES/#", URI_PAISES_ESPECIFICO)

            return uriMatcher
        }
    }
}