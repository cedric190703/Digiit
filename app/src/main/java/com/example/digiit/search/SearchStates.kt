package com.example.digiit.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.digiit.home.ticket
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.digiit.data.CommercialType
import com.example.digiit.home.wallet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SearchTickets(items: List<ticket>,
           filter: String,
           order: String){
    var searchText by remember { mutableStateOf("") }
    val isVisible by remember {
        derivedStateOf {
            searchText.isNotBlank()
        }
    }
    val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")

    // Filter on elements
    val filteredItems = if (searchText.isNotBlank()) {
        items.filter { ticket ->
            ticket.titre.contains(searchText) || ticket.tag.contains(
                searchText
            )
        }
    } else {
        items
    }

    // Sort filtered items by order and the type of the filter
    val sortedItems = if (order == "Croissant") {
        filteredItems.sortedWith(compareBy { ticket ->
            when (filter) {
                "Enseigne" -> ticket.titre
                "Date" -> LocalDateTime.parse(ticket.dateDate, formatter)
                "Type" -> ticket.typeCommerce.title
                "Amont" -> ticket.prix
                else -> ticket.titre
            }
        })
    } else {
        filteredItems.sortedWith(compareByDescending { ticket ->
            when (filter) {
                "Enseigne" -> ticket.titre
                "Date" -> LocalDateTime.parse(ticket.dateDate, formatter)
                "Type" -> ticket.typeCommerce.title
                "Amont" -> ticket.prix
                else -> ticket.titre
            }
        })
    }

    // TextField to have the searchText from the user
    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search") },
        modifier = Modifier
            .padding(10.dp)
            .width(250.dp),
        leadingIcon = { Icon(Icons.Filled.Search, "search icon") },
        trailingIcon = {
            if (isVisible) {
                IconButton(
                    onClick = { searchText = "" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
    )
}

@Composable
fun SearchWallets(items: List<wallet>,
                  filter: String,
                  order: String,
                  commercialType: String){
    var searchText by remember { mutableStateOf("") }
    val isVisible by remember {
        derivedStateOf {
            searchText.isNotBlank()
        }
    }
    val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")

    // Filter on elements
    val filteredItems = if (searchText.isNotBlank()) {
        items.filter { wallet ->
            wallet.titre.contains(searchText) ||
                    wallet.tag.contains(searchText) &&
                    if (wallet.walletType.title != "Tout") {
                        wallet.walletType.title == commercialType
                    } else {
                        true
                    }
        }
    } else {
        items
    }

    // Sort filtered items by order and the type of the filter
    val sortedItems = if (order == "Croissant") {
        filteredItems.sortedWith(compareBy { ticket ->
            when (filter) {
                "Enseigne" -> ticket.titre
                "Date" -> LocalDateTime.parse(ticket.dateDate, formatter)
                "Type" -> ticket.typeCommerce.title
                "Amont" -> ticket.prix
                else -> ticket.titre
            }
        })
    } else {
        filteredItems.sortedWith(compareByDescending { ticket ->
            when (filter) {
                "Enseigne" -> ticket.titre
                "Date" -> LocalDateTime.parse(ticket.dateDate, formatter)
                "Type" -> ticket.typeCommerce.title
                "Amont" -> ticket.prix
                else -> ticket.titre
            }
        })
    }

    // TextField to have the searchText from the user
    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search") },
        modifier = Modifier
            .padding(10.dp)
            .width(250.dp),
        leadingIcon = { Icon(Icons.Filled.Search, "search icon") },
        trailingIcon = {
            if (isVisible) {
                IconButton(
                    onClick = { searchText = "" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
    )
}