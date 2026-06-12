package com.example.app_restaurante.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_restaurante.viewmodel.BiteBoxViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: BiteBoxViewModel,
    nombreUsuario: String
) {
    // Colores del diseño
    val fondoOscuro = Color(0xFF151515)
    val verdeNeon = Color(0xFF39FF14)
    val textoBlanco = Color.White
    val grisOscuro = Color(0xFF222222)

    // ESTADO: Guardamos qué filtro tocó el usuario (Todos, Pizzas o Hamburguesas)
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    // LÓGICA: Filtramos la lista del ViewModel según el botón que tocó
    val platillosMostrados = if (categoriaSeleccionada == "Todos") {
        viewModel.menu
    } else {
        viewModel.menu.filter { it.categoria == categoriaSeleccionada }
    }

    Scaffold(
        topBar = {
            // Barra superior que saluda al usuario con su nombre
            TopAppBar(
                title = { Text("Hola, $nombreUsuario", color = textoBlanco, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = fondoOscuro)
            )
        },
        floatingActionButton = {
            // Botón flotante para ir al carrito
            FloatingActionButton(
                onClick = { navController.navigate("carrito") },
                containerColor = verdeNeon,
                contentColor = fondoOscuro
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
            }
        },
        containerColor = fondoOscuro
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // FILA DE FILTROS
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Todos", "Pizzas", "Hamburguesas").forEach { categoria ->
                    FilterChip(
                        selected = categoriaSeleccionada == categoria,
                        onClick = { categoriaSeleccionada = categoria },
                        label = { Text(categoria, color = if (categoriaSeleccionada == categoria) fondoOscuro else textoBlanco) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = verdeNeon,
                            containerColor = grisOscuro
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = categoriaSeleccionada == categoria,
                            borderColor = verdeNeon
                        )
                    )
                }
            }

            // LISTA DE PLATILLOS (LazyColumn)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp) // Espacio para que el botón flotante no tape
            ) {
                items(platillosMostrados) { platillo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                // NAVEGACIÓN: Al hacer clic, enviamos el ID del platillo a la pantalla 3
                                navController.navigate("detalle/${platillo.id}")
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = grisOscuro)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            // IMAGEN DESDE INTERNET CON COIL
                            AsyncImage(
                                model = platillo.urlImagen,
                                contentDescription = platillo.nombre,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(Color.DarkGray, RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = platillo.nombre, color = textoBlanco, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(text = platillo.categoria, color = Color.Gray, fontSize = 14.sp)
                                Text(text = "$${platillo.precio}", color = verdeNeon, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}