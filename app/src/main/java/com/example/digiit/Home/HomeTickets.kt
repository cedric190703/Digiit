package com.example.digiit.Home

import com.example.digiit.Cards.tags
import com.example.digiit.Cards.ticketsCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.digiit.R
import com.example.digiit.photos.SelectOption
import com.example.digiit.scrollbar
import kotlin.collections.ArrayList

@Composable
fun HomeScreen() {
    val showDialog =  remember { mutableStateOf(false) }
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
                onClick = { showDialog.value = true },
                Modifier.size(70.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add,
                    "add button",
                    modifier = Modifier.fillMaxSize(0.5F))
            }
            if(showDialog.value)
                SelectOption(setShowDialog = {
                    showDialog.value = it
                })
        }
    )
}

val listItems = getMenuItemsList()
val listOrders = getMenuItemsFilter()

data class ticket(val typeCommerce: tags,
                  val tag: String,
                  val titre: String,
                  val prix: Int,
                  val dateTime: String,
                  val dateDate: String,
                  val colorIcon: Color,
                  val colorTag: Color,
                  val colorText: Color,
                  val rating: Int,
                  val comment: String)

var listTickets = arrayListOf<ticket>()

@Composable
fun HomeTicketContent(paddingValues: PaddingValues) {
    val listState = rememberLazyListState()
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth()) {
        SearchView()
        if(listTickets.size == 0)
        {
            Image(painter = painterResource(id = R.drawable.tickets_image),
                contentDescription = "image for no data")
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pas de tickets",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                )
            }
        }
        else
        {
            LazyColumn(state = listState,
                modifier = Modifier.scrollbar(state = listState)) {
                items(listTickets) { item ->
                    ticketsCard(item)
                }
            }
        }
    }
}

@Composable
fun SearchView() {
    var filterItem = remember { mutableStateOf("Enseigne") }
    var filterOrder = remember { mutableStateOf("Croissant") }
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
            onClick = {
                      expanded = true
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter logo")
        }
        DropdownMenu(
            modifier = Modifier
                .width(width = 190.dp)
                .border(1.5.dp, MaterialTheme.colors.primary),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            offset = DpOffset(x = (-102).dp, y = (-15).dp),
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
                    modifier = Modifier.background(menuItemData.backGround),
                    onClick = {
                        listItems.forEach{ e -> e.iconColor = Color.Blue
                        e.backGround = Color.White}
                        filterItem.value = menuItemData.text
                        menuItemData.backGround = Color(0xFF0139CE)
                        menuItemData.iconColor = Color.White
                        expanded = false
                    },
                    enabled = true
                ) {
                    Icon(
                        painter = painterResource(id = menuItemData.icon),
                        contentDescription = menuItemData.text,
                        tint = menuItemData.iconColor
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(
                        text = menuItemData.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = menuItemData.iconColor
                    )
                }
            }
            Divider(color = Color.Black)
            Text(text = "Par ordre",
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
            listOrders.forEach { menuItemFilter ->
                DropdownMenuItem(
                    modifier = Modifier.background(menuItemFilter.backGround),
                    onClick = {
                        expanded = false
                        listOrders.forEach{ e -> e.iconColor = Color.Blue
                            e.backGround = Color.White}
                        menuItemFilter.backGround = Color(0xFF0139CE)
                        menuItemFilter.iconColor = Color.White
                        filterOrder.value = menuItemFilter.text
                    },
                    enabled = true
                ) {
                    Icon(
                        painter = painterResource(id = menuItemFilter.icon),
                        contentDescription = menuItemFilter.text,
                        tint = menuItemFilter.iconColor
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(
                        text = menuItemFilter.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = menuItemFilter.iconColor
                    )
                }
            }
        }
    }
}

data class MenuItemData(val text: String, val icon: Int, var backGround: Color, var iconColor: Color)

fun getMenuItemsList(): ArrayList<MenuItemData> {
    var listItems = ArrayList<MenuItemData>()
    listItems.add(MenuItemData(text = "Enseigne", icon = R.drawable.sort_by_alphabet, Color(0xFF0139CE), Color.White))
    listItems.add(MenuItemData(text = "Date", icon = R.drawable.date, Color.White, Color(0xFF0139CE)))
    listItems.add(MenuItemData(text = "Amont", icon = R.drawable.money, Color.White, Color(0xFF0139CE)))
    listItems.add(MenuItemData(text = "Type", icon = R.drawable.store, Color.White, Color(0xFF0139CE)))

    return listItems
}

fun getMenuItemsFilter(): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()
    listItems.add(MenuItemData(text = "Croissant", icon = R.drawable.ascending, Color(0xFF0139CE), Color.White))
    listItems.add(MenuItemData(text = "Décroissant", icon = R.drawable.descending, Color.White, Color(0xFF0139CE)))

    return listItems
}

fun ClearAllColor(listElement: ArrayList<MenuItemData>)
{
    for(item in listElement)
    {

    }
}