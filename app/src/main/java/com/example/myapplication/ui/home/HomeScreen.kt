package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.ClipPreviewItem
import com.example.myapplication.ui.HeaderTabs
import com.example.myapplication.ui.StreamPreviewItem

@Composable
fun TwitchHomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    var selectedTab by remember { mutableStateOf("Ao vivo") }

    val liveStreams by viewModel.liveStreams.collectAsState()
    val clips by viewModel.clips.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            HeaderTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        when (selectedTab) {
            "Ao vivo" -> {
                items(liveStreams) { stream ->
                    StreamPreviewItem(stream = stream)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            "Clipes" -> {
                items(clips) { clip ->
                    ClipPreviewItem(clip = clip)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}