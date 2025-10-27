package com.example.myapplication.ui.home

import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.data.LiveStream
import com.example.myapplication.data.TwitchClip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _liveStreams = MutableStateFlow<List<LiveStream>>(emptyList())
    val liveStreams: StateFlow<List<LiveStream>> = _liveStreams.asStateFlow()

    private val _clips = MutableStateFlow<List<TwitchClip>>(emptyList())
    val clips: StateFlow<List<TwitchClip>> = _clips.asStateFlow()

    init {
        carregarConteudo()
    }

    private fun carregarConteudo() {
        _liveStreams.value = listOf(
            LiveStream("Gaules", R.drawable.streamer1, R.drawable.cs2, "o Major esta chegando !!!", "CS2", "50,2 mil", listOf("Engraçado")),
            LiveStream("alanzoka", R.drawable.streamer4, R.drawable.valorant, "VALORANT com a galera", "Valorant", "23,5 mil", listOf("FPS", "Engraçado")),
            LiveStream("BaianoTV", R.drawable.streamer3, R.drawable.lol, "CBLOL - Cobertura completa", "League of Legends", "18,1 mil", listOf("eSports", "CBLOL"))
        )

        _clips.value = listOf(
            TwitchClip(R.drawable.telalive, "0:15", "22,3 mil", "há 6 dias", R.drawable.streamer2, "Maka hits nasty AK one-tap", "betboom_cs_a", "kingdempz"),
            TwitchClip(R.drawable.lol, "0:30", "15,8 mil", "há 6 horas", R.drawable.streamer3, "kensizor 1v5 ninja", "inkmate0", "luismmira"),
            TwitchClip(R.drawable.valorant, "0:17", "14,2 mil", "há 2 dias", R.drawable.streamer4, "kyousuke 2 HS", "betboom_cs_a", "luismmira")
        )
    }
}