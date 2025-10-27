package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PesquisaRecenteDAO {

    @Insert
    suspend fun inserir(pesquisa: PesquisaRecente)

    @Update
    suspend fun atualizar(pesquisa: PesquisaRecente)

    @Query("SELECT * FROM pesquisas_recentes ORDER BY id DESC")
    fun buscarTodas(): Flow<List<PesquisaRecente>>

    @Delete
    suspend fun deletar(pesquisa: PesquisaRecente)
}