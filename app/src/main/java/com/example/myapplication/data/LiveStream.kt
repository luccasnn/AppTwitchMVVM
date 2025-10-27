package com.example.myapplication.data

data class LiveStream(
    val streamerName: String,
    val streamerProfilePicRes: Int,
    val streamPreviewRes: Int,
    val streamTitle: String,
    val gameName: String,
    val viewerCount: String,
    val tags: List<String>
)