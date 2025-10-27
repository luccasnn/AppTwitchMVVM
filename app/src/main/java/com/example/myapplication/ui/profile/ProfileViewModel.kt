package com.example.myapplication.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.CategoriaDb
import com.example.myapplication.data.local.PesquisaRecente
import com.example.myapplication.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AppRepository) : ViewModel() {

    val pesquisasRecentes: StateFlow<List<PesquisaRecente>> = repository.todasPesquisasRecentes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val categorias: StateFlow<List<CategoriaDb>> = repository.todasCategorias
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _showCategoriaDialog = MutableStateFlow(false)
    val showCategoriaDialog: StateFlow<Boolean> = _showCategoriaDialog.asStateFlow()

    private val _categoriaParaEditar = MutableStateFlow<CategoriaDb?>(null)
    val categoriaParaEditar: StateFlow<CategoriaDb?> = _categoriaParaEditar.asStateFlow()

    fun addPesquisa(termo: String) {
        if (termo.isNotBlank()) {
            viewModelScope.launch {
                repository.inserirPesquisa(PesquisaRecente(termo = termo))
            }
        }
    }

    fun updatePesquisa(pesquisa: PesquisaRecente) {
        viewModelScope.launch {
            repository.atualizarPesquisa(pesquisa)
        }
    }

    fun deletePesquisa(pesquisa: PesquisaRecente) {
        viewModelScope.launch {
            repository.deletarPesquisa(pesquisa)
        }
    }

    fun onOpenCategoriaDialog(categoria: CategoriaDb? = null) {
        _categoriaParaEditar.value = categoria
        _showCategoriaDialog.value = true
    }

    fun onCloseCategoriaDialog() {
        _showCategoriaDialog.value = false
        _categoriaParaEditar.value = null
    }

    fun onSaveCategoria(categoria: CategoriaDb) {
        viewModelScope.launch {
            if (categoria.id == 0) {
                repository.inserirCategoria(categoria)
            } else {
                repository.atualizarCategoria(categoria)
            }
            onCloseCategoriaDialog()
        }
    }

    fun onDeleteCategoria(categoria: CategoriaDb) {
        viewModelScope.launch {
            repository.deletarCategoria(categoria)
            onCloseCategoriaDialog()
        }
    }
}