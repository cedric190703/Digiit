package com.example.digiit.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.digiit.R
import com.example.digiit.data.UserProvider

@Composable
fun SearchViewHomeTicket(auth: UserProvider) {
    val filterItem = remember { mutableStateOf("Enseigne") }
    val filterOrder = remember { mutableStateOf("Croissant") }
    var expanded by remember { mutableStateOf(false) }
    val userTickets = auth.user!!.tickets

    Row (modifier = Modifier
        .padding(top = 10.dp, bottom = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile image",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(8.dp),
            tint = MaterialTheme.colors.primary
        )
        SearchTickets(auth.user!!.tickets,
            filter = filterItem.value,
            order = filterOrder.value)
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
            listItemsHome.forEach { menuItemData ->
                DropdownMenuItem(
                    modifier = Modifier.background(menuItemData.backGround),
                    onClick = {
                        listItemsHome.forEach{ e -> e.iconColor = Color.Blue
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
            listOrdersHome.forEach { menuItemFilter ->
                DropdownMenuItem(
                    modifier = Modifier.background(menuItemFilter.backGround),
                    onClick = {
                        expanded = false
                        listOrdersHome.forEach{ e -> e.iconColor = Color.Blue
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

@Composable
fun SearchViewHomeWallet(auth: UserProvider) {
    val typeItem = remember { mutableStateOf("Tout") }
    val filterItem = remember { mutableStateOf("Enseigne") }
    val filterOrder = remember { mutableStateOf("Croissant") }
    var expanded by remember { mutableStateOf(false) }
    var expandedState by remember { mutableStateOf(false) }
    Row (modifier = Modifier
        .padding(top = 10.dp, bottom = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile image",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(8.dp),
            tint = MaterialTheme.colors.primary
        )
        SearchWallets(auth.user!!.wallets,
            filter = filterItem.value,
            order = filterOrder.value,
            commercialType = typeItem.value)
        Row() {
            Button(
                onClick = {
                    expanded = true
                    expandedState = false
                },
                modifier = Modifier.padding(3.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter logo")
            }
            Button(
                onClick = {
                    expandedState = true
                    expanded = false
                },
                modifier = Modifier.padding(3.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFCA7800))
            ) {
                Icon(painter = painterResource(id = R.drawable.type),
                    contentDescription = "Filter logo")
            }
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
            listItemsWallet.forEach { menuItemData ->
                DropdownMenuItem(
                    modifier = Modifier.background(menuItemData.backGround),
                    onClick = {
                        listItemsWallet.forEach{ e -> e.iconColor = Color.Blue
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
            listOrdersWallet.forEach { menuItemFilter ->
                DropdownMenuItem(
                    modifier = Modifier.background(menuItemFilter.backGround),
                    onClick = {
                        expanded = false
                        listOrdersWallet.forEach{ e -> e.iconColor = Color.Blue
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
        DropdownMenu(
            modifier = Modifier
                .width(width = 190.dp)
                .border(1.5.dp, Color(0xFFCA7800)),
            expanded = expandedState,
            onDismissRequest = {
                expandedState = false
            },
            offset = DpOffset(x = (-102).dp, y = (-15).dp),
            properties = PopupProperties()
        ) {
            Text(
                text = "Choisir le type :",
                modifier = Modifier.padding(8.dp),
                color = Color(0xFFCA7800),
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
            listItemsForWallet.forEach { menuItemData ->
                DropdownMenuItem(
                    modifier = Modifier.background(menuItemData.backGround),
                    onClick = {
                        listItemsForWallet.forEach { e ->
                            e.iconColor = Color(0xFFCA7800)
                            e.backGround = Color.White
                        }
                        typeItem.value = menuItemData.text
                        menuItemData.backGround = Color(0xFFCA7800)
                        menuItemData.iconColor = Color.White
                        expandedState = false
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
        }
    }
}

//For Home
val listItemsHome = getMenuItemsList()
val listOrdersHome = getMenuItemsFilter()

//For Wallet
val listItemsWallet = getMenuItemsList()
val listOrdersWallet = getMenuItemsFilter()
val listItemsForWallet = getMenuItemForWallet()

data class MenuItemData(val text: String, val icon: Int, var backGround: Color, var iconColor: Color)

fun getMenuItemsList(): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()
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

fun getMenuItemForWallet(): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()
    listItems.add(MenuItemData(text = "Tout", icon = R.drawable.all, Color(0xFFCA7800), Color.White))
    listItems.add(MenuItemData(text = "Carte fidélité", icon = R.drawable.wallet, Color.White, Color(0xFFCA7800)))
    listItems.add(MenuItemData(text = "Facture", icon = R.drawable.money, Color.White, Color(0xFFCA7800)))
    listItems.add(MenuItemData(text = "Bon commande", icon = R.drawable.shop, Color.White, Color(0xFFCA7800)))
    listItems.add(MenuItemData(text = "Contrat vente", icon = R.drawable.contract, Color.White, Color(0xFFCA7800)))

    return listItems
}
