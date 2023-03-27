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
import com.example.digiit.R
import com.example.digiit.data.UserProvider
import com.example.digiit.menus.*
import com.example.digiit.utils.getCurrentMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MenuScreen(auth: UserProvider) {
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            MenuContent(padding, auth)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu",
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

@Composable
fun ColorChangingSlider(data: MutableState<Float>, maxValue: Float) {
    if(data.value > maxValue) {
        data.value = maxValue
    }
    val color = getSliderColor(data.value, maxValue)
    Slider(
        value = data.value,
        onValueChange = {
        },
        valueRange = 0f..maxValue,
        steps = 100,
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = color,
            inactiveTrackColor = color,
            activeTickColor = color,
            inactiveTickColor = color
        ),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .fillMaxWidth()
    )
    Text(
        text = "Dépenses: $${data.value.toInt()} - $${maxValue.toInt()}  |  Mois : ${getCurrentMonth()}",
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    )
}

private fun getSliderColor(data: Float, maxValue: Float): Color {
    val percentage = data / maxValue
    val red = when {
        percentage < 0.1f -> 0.0f
        percentage < 0.2f -> 0.1f
        percentage < 0.3f -> 0.2f
        percentage < 0.4f -> 0.3f
        percentage < 0.5f -> 0.4f
        percentage < 0.6f -> 0.5f
        percentage < 0.7f -> 0.6f
        percentage < 0.8f -> 0.7f
        percentage < 0.9f -> 0.8f
        else -> 0.9f
    }
    val green = when {
        percentage < 0.2f -> 0.9f
        percentage < 0.2f -> 0.8f
        percentage < 0.3f -> 0.7f
        percentage < 0.4f -> 0.6f
        percentage < 0.5f -> 0.5f
        percentage < 0.6f -> 0.4f
        percentage < 0.7f -> 0.3f
        percentage < 0.8f -> 0.2f
        percentage < 0.9f -> 0.1f
        percentage < 0.9f -> 0.1f
        else -> 0.0f
    }
    val blue = 0.0f
    return Color(red, green, blue)
}

private val optionsList: ArrayList<OptionsData> = ArrayList()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuContent(paddingValues: PaddingValues, auth: UserProvider) {
    val context = LocalContext.current.applicationContext
    var listPrepared by remember { mutableStateOf(false) }
    val showDialogAccount = remember { mutableStateOf(false) }
    val showConfidentialiteDialog = remember { mutableStateOf(false) }
    val showDialogHelp = remember { mutableStateOf(false) }
    val showDialogSettings = remember { mutableStateOf(false) }
    val showDialogBilan = remember { mutableStateOf(false) }
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
                UserDetails(context = context, auth)
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
                    "Aides" -> OptionsItemStyle(item = item, context = context, onClick = {
                        showDialogHelp.value = it
                    })
                    "Réglages" -> OptionsItemStyle(item = item, context = context, onClick = {
                        showDialogSettings.value = it
                    })
                    else -> OptionsItemStyle(item = item, context = context, onClick = {
                        showDialogBilan.value = it
                    })
                }
            }
        }
        if(showDialogAccount.value)
        {
            EditAccount(onDismiss = {
                showDialogAccount.value = it
            }, auth)
        }
        if(showConfidentialiteDialog.value)
        {
            Confidentiality(onDismiss = {
                showConfidentialiteDialog.value = it
            })
        }
        if(showDialogHelp.value)
        {
            Help(onDismiss = {
                showDialogHelp.value = it
            })
        }
        if(showDialogSettings.value)
        {
            SettingsElement(onDismiss = {
                showDialogSettings.value = it
            })
        }
        if(showDialogBilan.value)
        {
            // Change the two values here with the User data
            // TODO
            Bilan(onDismiss = {
                showDialogBilan.value = it
            }, dataOnMonth = 123f, maxValueData = 1000f)
        }
    }
}

@Composable
private fun UserDetails(context: Context, auth: UserProvider) {
    val showDialogAccount = remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.profile),
                contentDescription = "logo profile",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colors.primary)
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
                        text = if (auth.user != null) auth.user!!.name else "No Name",
                        style = TextStyle(
                            fontSize = 22.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (auth.user != null) auth.user!!.email else "No Email",
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
                        auth.user?.logout {  }
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
                    }, auth)
                }
            }
        }
        // Change by the real data from the current month
        // TODO
        var data = remember {
            mutableStateOf(123f)
        }
        ColorChangingSlider(data = data, 1000.0f)
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
            icon = R.drawable.add_chart,
            title = "Bilan",
            subTitle = "Regardez votre bilan"
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
            icon = R.drawable.help,
            title = "Aides",
            subTitle = "Obtenir de l'aide"
        )
    )
}