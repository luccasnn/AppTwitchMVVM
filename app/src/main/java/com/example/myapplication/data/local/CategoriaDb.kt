package com.example.myapplication.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val tipo: String,
    val espectadores: String

)