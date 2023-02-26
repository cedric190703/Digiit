package com.example.digiit.home

import android.content.Context
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.menus.Confidentiality
import com.example.digiit.menus.EditAccount
import com.example.digiit.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MenuScreen() {
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            MenuContent(padding)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "com/example/digiit/menus",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Logo"
                        )
                    }
                },

                contentColor = Color.White,
                elevation = 12.dp
            )
        }
    )
}

private val optionsList: ArrayList<OptionsData> = ArrayList()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuContent(paddingValues: PaddingValues) {
    val context = LocalContext.current.applicationContext
    var listPrepared by remember { mutableStateOf(false) }
    val showDialogAccount = remember { mutableStateOf(false) }
    val showConfidentialiteDialog = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            optionsList.clear()

            // Add the data to optionsList
            prepareOptionsData()
            listPrepared = true
        }
    }
    if (listPrepared) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                UserDetails(context = context)
            }
            items(optionsList) { item ->
                when(item.title)
                {
                    "Compte" ->  OptionsItemStyle(item = item, context = context, onClick = {
                        showDialogAccount.value = it
                    })
                    "Confidentialité" -> OptionsItemStyle(item = item, context = context, onClick = {
                        showConfidentialiteDialog.value = it
                    })
                    else -> OptionsItemStyle(item = item, context = context, onClick = {
                        //nothing
                    })
                }
            }
        }
        if(showDialogAccount.value)
        {
            EditAccount(onDismiss = {
                showDialogAccount.value = it
            })
        }
        if(showConfidentialiteDialog.value)
        {
            Confidentiality(onDismiss = {
                showConfidentialiteDialog.value = it
            })
        }
    }
}

@Composable
private fun UserDetails(context: Context, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    val showDialogAccount = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.profile),
            contentDescription = "logo profile")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = if (auth.currentUser?.displayName.toString() != "null") auth.currentUser?.displayName.toString() else "No Name",
                    style = TextStyle(
                        fontSize = 22.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = auth.currentUser?.email.toString(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        letterSpacing = (0.8).sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                onClick = {
                    auth.signOut()
                    //TODO : go to login screen

                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Deconnexion",
                    tint = MaterialTheme.colors.primary
                )
            }
            if(showDialogAccount.value)
            {
                EditAccount(onDismiss = {
                    showDialogAccount.value = it
                })
            }
        }
    }
}

@Composable
private fun OptionsItemStyle(
    item: OptionsData,
    context: Context,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true
            ) {
                onClick(true)
            }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp),
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = MaterialTheme.colors.primary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subTitle,
                    style = TextStyle(
                        fontSize = 14.sp,
                        letterSpacing = (0.8).sp,
                        color = Color.Gray
                    )
                )
            }
            Icon(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                imageVector = Icons.Default.ArrowForward,
                contentDescription = item.title,
                tint = Color.Black.copy(alpha = 0.70f)
            )
        }
    }
}

data class OptionsData(val icon: Int, val title: String, val subTitle: String)

private fun prepareOptionsData() {
    optionsList.add(
        OptionsData(
            icon = R.drawable.account,
            title = "Compte",
            subTitle = "Gérez votre compte"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.safety,
            title = "Confidentialité",
            subTitle = "Gérez votre confidentialité"

        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.settings,
            title = "Réglages",
            subTitle = "Les réglages"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.folders,
            title = "Vos dossiers",
            subTitle = "Regardez vos dossiers"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.style,
            title = "Personnalisation",
            subTitle = "Personnalisez votre application"
        )
    )
}