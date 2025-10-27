package com.example.myapplication.data.repository



import com.example.myapplication.data.local.CategoriaDAO
import com.example.myapplication.data.local.CategoriaDb
import com.example.myapplication.data.local.PesquisaRecente
import com.example.myapplication.data.local.PesquisaRecenteDAO
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val categoriaDAO: CategoriaDAO,
    private val pesquisaRecenteDAO: PesquisaRecenteDAO
) {

    val todasCategorias: Flow<List<CategoriaDb>> = categoriaDAO.buscarTodas()
    val todasPesquisasRecentes: Flow<List<PesquisaRecente>> = pesquisaRecenteDAO.buscarTodas()

    suspend fun inserirCategoria(categoria: CategoriaDb) {
        categoriaDAO.inserir(categoria)
    }

    suspend fun atualizarCategoria(categoria: CategoriaDb) {
        categoriaDAO.atualizar(categoria)
    }

    suspend fun deletarCategoria(categoria: CategoriaDb) {
        categoriaDAO.deletar(categoria)
    }

    suspend fun inserirPesquisa(pesquisa: PesquisaRecente) {
        pesquisaRecenteDAO.inserir(pesquisa)
    }

    suspend fun deletarPesquisa(pesquisa: PesquisaRecente) {
        pesquisaRecenteDAO.deletar(pesquisa)
    }

    suspend fun atualizarPesquisa(pesquisa: PesquisaRecente) {
        pesquisaRecenteDAO.atualizar(pesquisa)
    }

    suspend fun popularCategoriasIniciais() {
        if (categoriaDAO.contar() == 0) {
            categoriaDAO.inserir(CategoriaDb(nome = "League of Legends", tipo = "MOBA", espectadores = "123k"))
            categoriaDAO.inserir(CategoriaDb(nome = "CS2", tipo = "FPS", espectadores = "98k"))
        }
    }
}