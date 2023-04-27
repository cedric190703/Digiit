package com.example.digiit.widgets.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.digiit.data.ticket.Ticket
import com.example.digiit.data.wallet.Wallet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SearchTickets(items: List<Ticket>,
                  filter: String,
                  order: String){
    var searchText by remember { mutableStateOf("") }
    val isVisible by remember {
        derivedStateOf {
            searchText.isNotBlank()
        }
    }

    // Filter on elements
    val filteredItems = if (searchText.isNotBlank()) {
        items.filter { ticket ->
            ticket.title.contains(searchText) || ticket.tag.contains(
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
                "Enseigne" -> ticket.title
                "Date" -> ticket.date
                "Type" -> ticket.type.title
                "Amont" -> ticket.price
                else -> ticket.title
            }
        })
    } else {
        filteredItems.sortedWith(compareByDescending { ticket ->
            when (filter) {
                "Enseigne" -> ticket.title
                "Date" -> ticket.date
                "Type" -> ticket.type.title
                "Amont" -> ticket.price
                else -> ticket.title
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
fun SearchWallets(items: List<Wallet>,
                  filter: String,
                  order: String,
                  commercialType: String){
    var searchText by remember { mutableStateOf("") }
    val isVisible by remember {
        derivedStateOf {
            searchText.isNotBlank()
        }
    }
    // Filter on elements
    val filteredItems = if (searchText.isNotBlank()) {
        items.filter { wallet ->
            wallet.title.contains(searchText) ||
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
                "Enseigne" -> ticket.title
                "Date" -> ticket.date
                "Type" -> ticket.walletType.title
                "Amont" -> ticket.price
                else -> ticket.title
            }
        })
    } else {
        filteredItems.sortedWith(compareByDescending { ticket ->
            when (filter) {
                "Enseigne" -> ticket.title
                "Date" -> ticket.date
                "Type" -> ticket.walletType.title
                "Amont" -> ticket.price
                else -> ticket.title
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
            .width(185.dp),
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