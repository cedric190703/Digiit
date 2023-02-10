package com.example.digiit.Home

import android.widget.Toast
import com.example.digiit.Cards.tags
import com.example.digiit.Cards.ticketsCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.digiit.DialogDelete
import com.example.digiit.DialogState
import com.example.digiit.R
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen() {
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    val context = LocalContext.current.applicationContext
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            HomeTicketContent(padding)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tickets",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.tickets),
                            contentDescription = "Logo Home"
                        )
                    }
                },

                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //Test for the toast
                    Toasty.success(context, "Vous êtes bien connecté", Toast.LENGTH_SHORT, true).show() },
                Modifier.size(70.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add,
                    "add button",
                    modifier = Modifier.fillMaxSize(0.5F))
            }
            //Tests for the Dialog
            //DialogState(true,showDialog, onDismiss = setShowDialog)
            //DialogDelete(showDialog, onDismiss = setShowDialog)
        }
    )
}

@Composable
fun HomeTicketContent(paddingValues: PaddingValues) {
    val formatterDate = SimpleDateFormat("dd-MM-yyyy")
    val formatterTime = SimpleDateFormat("HH:mm")
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth()) {
        SearchView()
        LazyColumn(modifier = Modifier) {
            item {
                ticketsCard(typeCommerce = tags.Alimentation,
                    tag = "Test",
                    titre = "Carrefour",
                    prix = 42,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Blue,
                    colorTag = Color.Red,
                    colorText = Color.Black)
            }
            item {
                ticketsCard(typeCommerce = tags.Artisan,
                    tag = "Test2",
                    titre = "Wallemart",
                    prix = 42,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Gray,
                    colorTag = Color.Blue,
                    colorText = Color.Black)
            }
            item {
                ticketsCard(typeCommerce = tags.Culture,
                    tag = "Test3",
                    titre = "U&W",
                    prix = 42000,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Red,
                    colorTag = Color.Blue,
                    colorText = Color.Black)
            }
            item {
                ticketsCard(typeCommerce = tags.Habillement,
                    tag = "Test3",
                    titre = "U&W",
                    prix = 42000,
                    dateTime = formatterTime.format(Date()),
                    dateDate = formatterDate.format(Date()),
                    colorIcon = Color.Black,
                    colorTag = Color.DarkGray,
                    colorText = Color.Black)
            }
        }
    }
}

@Composable
fun SearchView() {
    val listItems = getMenuItemsList()
    val listOrders = getMenuItemsFilter()
    var expanded by remember {
        mutableStateOf(false)
    }
    var searchText by remember { mutableStateOf("") }
    val isVisible by remember {
        derivedStateOf {
            searchText.isNotBlank()
        }
    }
    Row (modifier = Modifier
        .padding(top = 10.dp, bottom = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.account),
            contentDescription = "profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(8.dp)
        )
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
        Button(
            onClick = { expanded = true },
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter logo")
        }
        DropdownMenu(
            modifier = Modifier.width(width = 190.dp),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            offset = DpOffset(x = (-102).dp, y = (-64).dp),
            properties = PopupProperties()
        ) {
            Text(text = "Filtrer par :",
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
            listItems.forEach { menuItemData ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                    },
                    enabled = true
                ) {
                    Icon(
                        painter = painterResource(id = menuItemData.icon),
                        contentDescription = menuItemData.text,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(
                        text = menuItemData.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
            Text(text = "Par ordre",
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
            listOrders.forEach { menuItemFilter ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                    },
                    enabled = true
                ) {
                    Icon(
                        painter = painterResource(id = menuItemFilter.icon),
                        contentDescription = menuItemFilter.text,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(
                        text = menuItemFilter.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

data class MenuItemData(val text: String, val icon: Int)

fun getMenuItemsList(): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()
    listItems.add(MenuItemData(text = "Enseigne", icon = R.drawable.sort_by_alphabet))
    listItems.add(MenuItemData(text = "Date", icon = R.drawable.date))
    listItems.add(MenuItemData(text = "Amont", icon = R.drawable.money))
    listItems.add(MenuItemData(text = "Type", icon = R.drawable.store))

    return listItems
}

fun getMenuItemsFilter(): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()
    listItems.add(MenuItemData(text = "Croissant", icon = R.drawable.ascending))
    listItems.add(MenuItemData(text = "Décroissant", icon = R.drawable.descending))

    return listItems
}
