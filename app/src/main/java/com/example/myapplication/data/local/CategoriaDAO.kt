package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDAO {

    @Insert
    suspend fun inserir(categoria: CategoriaDb)

    @Update
    suspend fun atualizar(categoria: CategoriaDb)

    @Delete
    suspend fun deletar(categoria: CategoriaDb)

    @Query("SELECT * FROM categorias ORDER BY nome ASC")
    fun buscarTodas(): Flow<List<CategoriaDb>>

    @Query("SELECT COUNT(*) FROM categorias")
    suspend fun contar(): Int
}