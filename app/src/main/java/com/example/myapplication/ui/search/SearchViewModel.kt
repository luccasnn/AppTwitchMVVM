package com.example.myapplication.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.CategoriaDb
import com.example.myapplication.data.local.PesquisaRecente
import com.example.myapplication.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchText: String = "",
    val isSearchBarFocused: Boolean = false,
    val showDialog: Boolean = false,
    val categoriaParaEditar: CategoriaDb? = null,
    val pesquisasRecentes: List<PesquisaRecente> = emptyList(),
    val categorias: List<CategoriaDb> = emptyList()
)

class SearchViewModel(private val repository: AppRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _isSearchBarFocused = MutableStateFlow(false)
    private val _showDialog = MutableStateFlow(false)
    private val _categoriaParaEditar = MutableStateFlow<CategoriaDb?>(null)

    private val _pesquisasRecentes = repository.todasPesquisasRecentes

    private val _categorias = repository.todasCategorias.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val flow1 = combine(_searchText, _isSearchBarFocused, _showDialog) { text, focused, dialog ->
        Triple(text, focused, dialog)
    }

    private val flow2 = combine(_categoriaParaEditar, _pesquisasRecentes, _categorias) { categoria, pesquisas, categorias ->
        Triple(categoria, pesquisas, categorias)
    }

    val uiState: StateFlow<SearchUiState> = combine(flow1, flow2) { triple1, triple2 ->
        val (searchText, isFocused, showDialog) = triple1
        val (categoria, pesquisas, categoriasList) = triple2

        SearchUiState(
            searchText = searchText,
            isSearchBarFocused = isFocused,
            showDialog = showDialog,
            categoriaParaEditar = categoria,
            pesquisasRecentes = pesquisas,
            categorias = categoriasList.filter { categoriaDb ->
                categoriaDb.nome.contains(searchText, ignoreCase = true)
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SearchUiState()
    )

    init {
        viewModelScope.launch {
            repository.popularCategoriasIniciais()
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onFocusChanged(isFocused: Boolean) {
        _isSearchBarFocused.value = isFocused
    }

    fun clearSearchText() {
        _searchText.value = ""
    }

    fun performSearch() {
        if (_searchText.value.isNotBlank()) {
            viewModelScope.launch {
                repository.inserirPesquisa(PesquisaRecente(termo = _searchText.value))
                _searchText.value = ""
                _isSearchBarFocused.value = false
            }
        }
    }

    fun cancelSearch() {
        _isSearchBarFocused.value = false
    }

    fun deletePesquisa(pesquisa: PesquisaRecente) {
        viewModelScope.launch {
            repository.deletarPesquisa(pesquisa)
        }
    }

    fun onOpenDialog(categoria: CategoriaDb? = null) {
        _categoriaParaEditar.value = categoria
        _showDialog.value = true
    }

    fun onCloseDialog() {
        _showDialog.value = false
        _categoriaParaEditar.value = null
    }

    fun onSaveCategoria(categoria: CategoriaDb) {
        viewModelScope.launch {
            if (categoria.id == 0) {
                repository.inserirCategoria(categoria)
            } else {
                repository.atualizarCategoria(categoria)
            }
            onCloseDialog()
        }
    }

    fun onDeleteCategoria(categoria: CategoriaDb) {
        viewModelScope.launch {
            repository.deletarCategoria(categoria)
            onCloseDialog()
        }
    }
}