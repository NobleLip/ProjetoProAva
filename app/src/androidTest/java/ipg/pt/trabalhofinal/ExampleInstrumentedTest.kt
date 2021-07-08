package ipg.pt.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

        private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
        fun getBDOpenHP() = BDOpenHP(getAppContext())

        //Livros
        private fun getTabelaLivros(db: SQLiteDatabase) = TabelaLivros(db)

        private fun insertLivro(tabelaLivros: TabelaLivros, livro: Livros): Long {
            val id = tabelaLivros.insert(livro.toContentValues())
            assertNotEquals(-1, id)

            return id
        }

        private fun GetLivroBd(tabelaLivros: TabelaLivros, id: Long): Livros {
            val cursor = tabelaLivros.query(
                TabelaLivros.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            assertNotNull(cursor)
            assert(cursor!!.moveToNext())

            return Livros.fromCursor(cursor)

        }

        private fun criarLivro(): Livros {
            val livro = Livros(Nome = "BookTest", Avalia = "4", Tipo = "Ficção")

            return livro
        }

        //Pessoas
        private fun getTabelaPessoas(db: SQLiteDatabase) = TabelaPessoas(db)

        private fun insertPessoas(tabelaPessoas: TabelaPessoas, pessoa: Pessoas): Long {
            val id = tabelaPessoas.insert(pessoa.toContentValues())
            assertNotEquals(-1, id)

            return id
        }

        private fun GetPessoasBd(tabelaPessoas: TabelaPessoas, id: Long): Pessoas {
            val cursor = tabelaPessoas.query(
                TabelaPessoas.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            assertNotNull(cursor)
            assert(cursor!!.moveToNext())

            return Pessoas.fromCursor(cursor)

        }

        private fun criarPessoa(): Pessoas {
            val pessoa = Pessoas(
                Nome = "Filipe", DataNascimento = "11/08/1999", Morada = "Morada", CarCida = "CC",
                Contacto = "Contacto", Numero_Vacinas = "NumVacina"
            )

            return pessoa
        }

        //Pais

        private fun getTabelaPais(db: SQLiteDatabase) = TabelaPaises(db)

        private fun insertPais(tabelaPaises: TabelaPaises, pais: Paises): Long {
            val id = tabelaPaises.insert(pais.toContentValues())
            assertNotEquals(-1, id)

            return id
        }

        private fun GetPaisesBd(tabelaPaises: TabelaPaises, id: Long): Paises {
            val cursor = tabelaPaises.query(
                TabelaPaises.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            assertNotNull(cursor)
            assert(cursor!!.moveToNext())

            return Paises.fromCursor(cursor)

        }

        private fun criarPais(): Paises {
            val pais = Paises(
                Nome = "PaisTest", AvaliaPre = "5", Preco = "300"
            )

            return pais
        }



        @Test
        fun consegueAbrirBaseDados() {

            val db = getBDOpenHP().readableDatabase
            assert(db.isOpen)
            db.close()
        }
//TEST LIVROS
        @Test
        fun consegueInserirLivros(){
            val db = getBDOpenHP().writableDatabase
            val livro = criarLivro()
            livro.id = insertLivro(getTabelaLivros(db), livro)
            assertEquals(livro, GetLivroBd(getTabelaLivros(db), livro.id))
            db.close()

        }

    @Test
    fun consegueAlterarLivros(){
        val db = getBDOpenHP().writableDatabase
        val livro = criarLivro()
        livro.id = insertLivro(getTabelaLivros(db), livro)
        livro.Nome = "UpdateTest"
        val registosAlterados = getTabelaLivros(db).update(
            livro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(livro.id.toString())
        )
        assertEquals(1, registosAlterados)
        db.close()
    }

    @Test
    fun consegueApagarLivros() {
        val db = getBDOpenHP().writableDatabase
        val livro = criarLivro()
        livro.id = insertLivro(getTabelaLivros(db), livro)
        val registosApagados = getTabelaLivros(db).delete("${BaseColumns._ID}=?",arrayOf(livro.id.toString()))
        assertEquals(1, registosApagados)
        db.close()
    }

    @Test
    fun consegueLerLivros() {
        val db = getBDOpenHP().writableDatabase
        val livro = criarLivro()
        livro.id = insertLivro(getTabelaLivros(db), livro)
        val livroBd = GetLivroBd(getTabelaLivros(db), livro.id)
        assertEquals(livro, livroBd)
        db.close()
    }
//TEST PAIS

    @Test
    fun consegueInserirPais(){
        val db = getBDOpenHP().writableDatabase
        val pais = criarPais()
        pais.id = insertPais(getTabelaPais(db), pais)
        assertEquals(pais, GetPaisesBd(getTabelaPais(db), pais.id))
        db.close()

    }

    @Test
    fun consegueAlterarPais(){
        val db = getBDOpenHP().writableDatabase
        val pais = criarPais()
        pais.id = insertPais(getTabelaPais(db), pais)
        pais.Nome = "UpdateTest"
        val registosAlterados = getTabelaPais(db).update(
            pais.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pais.id.toString())
        )
        assertEquals(1, registosAlterados)
        db.close()
    }

    @Test
    fun consegueApagarPais() {
        val db = getBDOpenHP().writableDatabase
        val pais = criarPais()
        pais.id = insertPais(getTabelaPais(db), pais)
        val registosApagados = getTabelaPais(db).delete("${BaseColumns._ID}=?",arrayOf(pais.id.toString()))
        assertEquals(1, registosApagados)
        db.close()
    }

    @Test
    fun consegueLerPais() {
        val db = getBDOpenHP().writableDatabase
        val pais = criarPais()
        pais.id = insertPais(getTabelaPais(db), pais)
        val paisBd = GetPaisesBd(getTabelaPais(db), pais.id)
        assertEquals(pais, paisBd)
        db.close()
    }

    //TEST Pessoas

    @Test
    fun consegueInserirPessoa(){
        val db = getBDOpenHP().writableDatabase
        val pessoa= criarPessoa()
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)
        assertEquals(pessoa, GetPessoasBd(getTabelaPessoas(db), pessoa.id))
        db.close()

    }

    @Test
    fun consegueAlterarPessoa(){
        val db = getBDOpenHP().writableDatabase
        val pessoa = criarPessoa()
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)
        pessoa.Nome = "UpdateTest"
        val registosAlterados = getTabelaPessoas(db).update(
            pessoa.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pessoa.id.toString())
        )
        assertEquals(1, registosAlterados)
        db.close()
    }

    @Test
    fun consegueApagarPessoa() {
        val db = getBDOpenHP().writableDatabase
        val pessoa = criarPessoa()
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)
        val registosApagados = getTabelaPessoas(db).delete("${BaseColumns._ID}=?",arrayOf(pessoa.id.toString()))
        assertEquals(1, registosApagados)
        db.close()
    }

    @Test
    fun consegueLerPessoa() {
        val db = getBDOpenHP().writableDatabase
        val pessoa = criarPessoa()
        pessoa.id = insertPessoas(getTabelaPessoas(db), pessoa)
        val paisBd = GetPessoasBd(getTabelaPessoas(db), pessoa.id)
        assertEquals(pessoa, paisBd)
        db.close()
    }

        @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ipg.pt.trabalhofinal", appContext.packageName)
    }
}