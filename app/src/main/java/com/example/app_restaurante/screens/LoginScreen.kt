package com.example.app_restaurante.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    // ESTADO: Aquí guardamos lo que el usuario escribe en tiempo real
    var nombre by remember { mutableStateOf("") }

    // COLORES DEL DISEÑO DIFERENCIADOR: Fondo oscuro y botón verde neón
    val fondoOscuro = Color(0xFF151515)
    val verdeNeon = Color(0xFF39FF14)
    val textoBlanco = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoOscuro) // Aplicamos el fondo oscuro
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. EL LOGO (Texto grande en lugar de imagen, como permite el requerimiento)
        Text(
            text = "BiteBox",
            fontSize = 54.sp,
            fontWeight = FontWeight.ExtraBold,
            color = verdeNeon,
            modifier = Modifier.padding(bottom = 60.dp)
        )

        // 2. CAMPO DE TEXTO
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it }, // Actualiza el estado cada vez que tecleas
            label = { Text("Ingresa tu nombre", color = Color.Gray) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = verdeNeon,
                unfocusedBorderColor = Color.DarkGray,
                focusedTextColor = textoBlanco,
                unfocusedTextColor = textoBlanco
            ),
            shape = RoundedCornerShape(16.dp), // Bordes curvos modernos
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 3. BOTÓN DE ENTRADA
        Button(
            onClick = {
                // NAVEGACIÓN: Va a la ruta 'menu' y le adjunta el nombre escrito
                navController.navigate("menu/$nombre")
            },
            // LÓGICA DE NEGOCIO: Habilita el botón solo si hay más de 3 letras
            enabled = nombre.length > 3,
            colors = ButtonDefaults.buttonColors(
                containerColor = verdeNeon,
                contentColor = fondoOscuro, // Texto oscuro dentro del botón neón
                disabledContainerColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Entrar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}