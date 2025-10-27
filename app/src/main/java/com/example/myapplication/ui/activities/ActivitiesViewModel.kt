package com.example.myapplication.ui.activities

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.CrudItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ActivitiesViewModel : ViewModel() {

    private val _notificacoes = MutableStateFlow<List<CrudItem>>(emptyList())
    val notificacoes: StateFlow<List<CrudItem>> = _notificacoes.asStateFlow()

    private val _sussurros = MutableStateFlow<List<CrudItem>>(emptyList())
    val sussurros: StateFlow<List<CrudItem>> = _sussurros.asStateFlow()

    init {
        carregarDadosIniciais()
    }

    private fun carregarDadosIniciais() {
        _notificacoes.value = listOf(
            CrudItem(text = "É o dia do video game! Compartilhe um clipe para desbloquear o distintivo GGWP!"),
            CrudItem(text = "Sua inscrição no canal do Alanzoka foi renovada."),
            CrudItem(text = "Gaules entrou ao vivo: 'CS2 - RUMO AO MAJOR!'")
        )
        _sussurros.value = listOf(
            CrudItem(text = "user1: E aí, tudo bem?"),
            CrudItem(text = "user2: Vamos jogar mais tarde?"),
        )
    }

    fun addNotificacao(text: String) {
        val item = CrudItem(text = text)
        _notificacoes.update { listOf(item) + it }
    }

    fun updateNotificacao(item: CrudItem, newText: String) {
        _notificacoes.update { currentList ->
            currentList.map {
                if (it.id == item.id) it.copy(text = newText) else it
            }
        }
    }

    fun deleteNotificacao(item: CrudItem) {
        _notificacoes.update { currentList ->
            currentList.filterNot { it.id == item.id }
        }
    }

    fun addSussurro(text: String) {
        val item = CrudItem(text = text)
        _sussurros.update { listOf(item) + it }
    }

    fun updateSussurro(item: CrudItem, newText: String) {
        _sussurros.update { currentList ->
            currentList.map {
                if (it.id == item.id) it.copy(text = newText) else it
            }
        }
    }

    fun deleteSussurro(item: CrudItem) {
        _sussurros.update { currentList ->
            currentList.filterNot { it.id == item.id }
        }
    }
}