package com.example.app_restaurante.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun DetalleScreen(
    navController: NavController,
    viewModel: BiteBoxViewModel,
    platilloId: Int
) {
    // Mismos colores para mantener la consistencia
    val fondoOscuro = Color(0xFF151515)
    val verdeNeon = Color(0xFF39FF14)
    val textoBlanco = Color.White

    // LÓGICA: Buscamos el platillo usando la función que hicimos en el Paso 1
    val platillo = viewModel.obtenerPlatilloPorId(platilloId)

    // Protección en caso de que el platillo no exista
    if (platillo == null) {
        Box(modifier = Modifier.fillMaxSize().background(fondoOscuro), contentAlignment = Alignment.Center) {
            Text("Platillo no encontrado", color = textoBlanco)
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles", color = textoBlanco) },
                navigationIcon = {
                    // Botón para regresar manualmente
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar", tint = verdeNeon)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = fondoOscuro
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. UI INMERSIVA: Imagen grande en la parte superior
            AsyncImage(
                model = platillo.urlImagen,
                contentDescription = platillo.nombre,
                contentScale = ContentScale.Crop, // Esto hace que la imagen llene el espacio recortando los bordes
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            // 2. INFORMACIÓN DEL PLATILLO
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = platillo.nombre,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = textoBlanco,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$${platillo.precio}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = verdeNeon
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = platillo.descripcion,
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Esto empuja el botón hacia el fondo de la pantalla

            // 3. BOTÓN AÑADIR AL CARRITO
            Button(
                onClick = {
                    // Acción 1: Guardamos el pedido en el ViewModel
                    viewModel.agregarAlCarrito(platillo)
                    // Acción 2: Regresamos a la pantalla anterior automáticamente
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = verdeNeon,
                    contentColor = fondoOscuro
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .height(56.dp)
            ) {
                Text("Añadir al Carrito", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}