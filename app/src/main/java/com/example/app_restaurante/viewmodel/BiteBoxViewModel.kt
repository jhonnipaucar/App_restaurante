package com.example.app_restaurante.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.app_restaurante.Platillo

class BiteBoxViewModel : ViewModel() {

    // 1. Lista del menú "quemada" (No va a cambiar)
    val menu = listOf(
        Platillo(1, "Pizza Margarita", "Clásica con queso mozzarella y salsa de tomate.", 10.50, "Pizzas", "https://ejemplo.com/pizza1.jpg"),
        Platillo(2, "Pizza Pepperoni", "Doble pepperoni con bordes crujientes.", 12.00, "Pizzas", "https://ejemplo.com/pizza2.jpg"),
        Platillo(3, "Pizza BBQ", "Pollo, tocino y abundante salsa BBQ.", 14.50, "Pizzas", "https://ejemplo.com/pizza3.jpg"),
        Platillo(4, "Burger Clásica", "Carne de res, lechuga, tomate y queso cheddar.", 8.50, "Hamburguesas", "https://ejemplo.com/burger1.jpg"),
        Platillo(5, "Burger Bacon", "Doble carne, tocino crujiente y salsa especial.", 11.00, "Hamburguesas", "https://ejemplo.com/burger2.jpg"),
        Platillo(6, "Burger Veggie", "Medallón de lentejas con vegetales frescos.", 9.00, "Hamburguesas", "https://ejemplo.com/burger3.jpg")
    )

    // 2. El Carrito reactivo (Sí va a cambiar)
    val carrito = mutableStateListOf<Platillo>()

    // 3. Funciones de negocio
    fun agregarAlCarrito(platillo: Platillo) {
        carrito.add(platillo)
    }

    fun eliminarDelCarrito(platillo: Platillo) {
        carrito.remove(platillo)
    }

    fun vaciarCarrito() {
        carrito.clear()
    }

    fun calcularTotal(): Double {
        return carrito.sumOf { it.precio }
    }

    fun obtenerPlatilloPorId(id: Int): Platillo? {
        return menu.find { it.id == id }
    }
}