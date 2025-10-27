package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pesquisas_recentes")
data class PesquisaRecente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val termo: String
)