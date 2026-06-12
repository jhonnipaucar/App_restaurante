package com.example.app_restaurante.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_restaurante.viewmodel.BiteBoxViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: BiteBoxViewModel) {
    val fondoOscuro = Color(0xFF151515)
    val verdeNeon = Color(0xFF39FF14)
    val textoBlanco = Color.White
    val grisOscuro = Color(0xFF222222)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tu Carrito", color = textoBlanco) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar", tint = verdeNeon)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = fondoOscuro)
            )
        },
        containerColor = fondoOscuro
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 1. LISTA DE PEDIDOS (Solo lo que está en el carrito)
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.carrito) { platillo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = grisOscuro)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = platillo.nombre, color = textoBlanco, fontSize = 18.sp)
                            Text(text = "$${platillo.precio}", color = verdeNeon, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. CÁLCULO TOTAL (Llamando a la función del ViewModel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total a Pagar:", color = textoBlanco, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("$${viewModel.calcularTotal()}", color = verdeNeon, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. BOTÓN DE CONFIRMAR PEDIDO
            Button(
                onClick = {
                    // Cumpliendo el requisito: Vaciamos carrito y regresamos al menú
                    viewModel.vaciarCarrito()
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = verdeNeon,
                    contentColor = fondoOscuro
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Confirmar Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}