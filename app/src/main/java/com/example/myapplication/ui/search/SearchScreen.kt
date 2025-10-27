package com.example.myapplication.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.local.CategoriaDb

fun getImagemCategoria(nome: String): Int {
    return when {
        nome.equals("League of Legends", ignoreCase = true) -> R.drawable.lol
        nome.equals("CS2", ignoreCase = true) -> R.drawable.cs2
        nome.equals("Dota 2", ignoreCase = true) -> R.drawable.dota
        nome.equals("Valorant", ignoreCase = true) -> R.drawable.valorant
        nome.equals("GTA V", ignoreCase = true) -> R.drawable.gta
        nome.equals("IRL", ignoreCase = true) -> R.drawable.irl
        else -> R.drawable.ic_launcher_background
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwitchSearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val performSearch = {
        viewModel.performSearch()
        focusManager.clearFocus()
    }

    val cancelSearch = {
        viewModel.cancelSearch()
        focusManager.clearFocus()
    }

    if (uiState.showDialog) {
        CategoriaDialog(
            categoria = uiState.categoriaParaEditar,
            onDismiss = { viewModel.onCloseDialog() },
            onConfirm = { categoria ->
                viewModel.onSaveCategoria(categoria)
            },
            onDelete = { categoria ->
                viewModel.onDeleteCategoria(categoria)
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onOpenDialog(null) },
                containerColor = Color(0xFF9147FF)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Categoria", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f).background(Color.DarkGray, shape = CircleShape).padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(painter = painterResource(id = android.R.drawable.ic_menu_search), contentDescription = "Search Icon", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        BasicTextField(
                            value = uiState.searchText,
                            onValueChange = { viewModel.onSearchTextChanged(it) },
                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                            cursorBrush = SolidColor(Color.White),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { performSearch() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    viewModel.onFocusChanged(focusState.isFocused)
                                }
                        )
                        if (uiState.searchText.isEmpty()) {
                            Text("Procurar", color = Color.Gray, fontSize = 16.sp)
                        }
                    }
                    if (uiState.searchText.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                            contentDescription = "Clear Search",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.clearSearchText() }
                        )
                    }
                }

                if (uiState.isSearchBarFocused) {
                    val (buttonText, buttonAction) = if (uiState.searchText.isNotBlank()) {
                        "Buscar" to performSearch
                    } else {
                        "Cancelar" to cancelSearch
                    }

                    TextButton(onClick = buttonAction) {
                        Text(buttonText, color = Color.White)
                    }
                }
            }

            if (uiState.isSearchBarFocused && uiState.searchText.isBlank()) {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    item { Text("PESQUISAS RECENTES", color = Color.Gray, fontWeight = FontWeight.Bold) }
                    items(uiState.pesquisasRecentes) { pesquisa ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(pesquisa.termo, color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
                            IconButton(onClick = { viewModel.deletePesquisa(pesquisa) }) {
                                Icon(Icons.Default.Clear, contentDescription = "Remover pesquisa", tint = Color.Gray)
                            }
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.categorias, key = { it.id }) { categoria ->
                        Card(
                            modifier = Modifier.pointerInput(Unit) {
                                detectTapGestures(onLongPress = { viewModel.onOpenDialog(categoria) })
                            },
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = getImagemCategoria(categoria.nome)),
                                    contentDescription = "Capa de ${categoria.nome}",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.aspectRatio(3f / 5f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(categoria.nome, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(categoria.espectadores, color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaDialog(
    categoria: CategoriaDb?,
    onDismiss: () -> Unit,
    onConfirm: (CategoriaDb) -> Unit,
    onDelete: (CategoriaDb) -> Unit
) {
    var nome by remember(categoria) { mutableStateOf(categoria?.nome ?: "") }
    var tipo by remember(categoria) { mutableStateOf(categoria?.tipo ?: "") }
    var espectadores by remember(categoria) { mutableStateOf(categoria?.espectadores ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (categoria == null) "Adicionar Categoria" else "Editar Categoria") },
        text = {
            Column {
                TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome da Categoria") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo (Ex: FPS, MOBA)") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = espectadores, onValueChange = { espectadores = it }, label = { Text("Espectadores (Ex: 98k)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val novaOuEditadaCategoria = categoria?.copy(nome = nome, tipo = tipo, espectadores = espectadores)
                    ?: CategoriaDb(nome = nome, tipo = tipo, espectadores = espectadores)
                onConfirm(novaOuEditadaCategoria)
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Row {
                if (categoria != null) {
                    Button(onClick = { onDelete(categoria) }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text("Deletar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        }
    )
}