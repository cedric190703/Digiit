package com.example.digiit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

//For the toasts
//Toasty.error(ctx, "This is an error toast.", Toast.LENGTH_SHORT, true).show()
//Toasty.success(ctx, "This is a Success toast.", Toast.LENGTH_SHORT, true).show()
//Toasty.info(ctx, "This is a Info toast.", Toast.LENGTH_SHORT, true).show()
//Toasty.warning(ctx, "This is a Warning toast.", Toast.LENGTH_SHORT, true).show()
//Toasty.normal(ctx, "This is a Normal toast.").show()

@Composable
fun DialogState(
    stateUser: Boolean,
    showDialog: Boolean,
    cornerRadius: Dp = 12.dp,
    titleTextStyle: TextStyle = TextStyle(
        color = Color.Black.copy(alpha = 0.87f),
        fontSize = 20.sp
    ),
    buttonTextStyle: TextStyle = TextStyle(
        fontSize = 16.sp
    ),
    onDismiss: (Boolean) -> Unit
) {
    if (showDialog) {
        val context = LocalContext.current.applicationContext

        val interactionSource = remember {
            MutableInteractionSource()
        }

        val buttonCorner = 6.dp

        Dialog(
            onDismissRequest = {
                onDismiss(false)
            }
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = cornerRadius)
            ) {

                Column(modifier = Modifier.padding(all = 16.dp)) {
                    if(stateUser)
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 6.dp,
                                alignment = Alignment.Start
                            )
                        ) {
                            Icon(
                                modifier = Modifier.size(70.dp).padding(13.dp),
                                painter = painterResource(id = R.drawable.valid),
                                contentDescription = "Valid Icon"
                            )
                            Text(
                                text ="Vous êtes bien connecté",
                                style = titleTextStyle,
                                fontSize = 25.sp
                            )
                        }
                    }
                    else
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 6.dp,
                                alignment = Alignment.Start
                            )
                        ) {
                            Icon(
                                modifier = Modifier.size(80.dp),
                                painter = painterResource(id = R.drawable.incorrect),
                                contentDescription = "Not valid Icon"
                            )
                            Text(
                                text ="Problème lors de la connexion",
                                style = titleTextStyle,
                                fontSize = 30.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(23.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.End
                        )
                    ) {
                        if(stateUser)
                        {
                            Box(
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource
                                    ) {
                                        Toast
                                            .makeText(context, "Fermer", Toast.LENGTH_SHORT)
                                            .show()
                                        onDismiss(false)
                                    }
                                    .border(
                                        width = 2.dp,
                                        color = Color.Green,
                                        shape = RoundedCornerShape(buttonCorner)
                                    )
                                    .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                            ) {
                                Text(
                                    text = "Fermer",
                                    style = buttonTextStyle,
                                    color = Color.Green
                                )
                            }
                        }
                        else
                        {
                            Box(
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource
                                    ) {
                                        Toast
                                            .makeText(context, "Fermer", Toast.LENGTH_SHORT)
                                            .show()
                                        onDismiss(false)
                                    }
                                    .border(
                                        width = 2.dp,
                                        color = Color.Red,
                                        shape = RoundedCornerShape(buttonCorner)
                                    )
                                    .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                            ) {
                                Text(
                                    text = "Fermer",
                                    style = buttonTextStyle,
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun DialogDelete(
    showDialog: Boolean,
    cornerRadius: Dp = 12.dp,
    deleteButtonColor: Color = Color(0xFFD10303),
    cancelButtonColor: Color = Color(0xFF0A46AD),
    titleTextStyle: TextStyle = TextStyle(
        color = Color.Black.copy(alpha = 0.87f),
        fontSize = 20.sp
    ),
    messageTextStyle: TextStyle = TextStyle(
        color = Color.Black.copy(alpha = 0.95f),
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    buttonTextStyle: TextStyle = TextStyle(
        fontSize = 16.sp
    ),
    onDismiss: (Boolean) -> Unit
) {
    if (showDialog) {
        val context = LocalContext.current.applicationContext

        val interactionSource = remember {
            MutableInteractionSource()
        }

        val buttonCorner = 6.dp

        Dialog(
            onDismissRequest = {
                onDismiss(false)
            }
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = cornerRadius)
            ) {

                Column(modifier = Modifier.padding(all = 16.dp)) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 6.dp,
                            alignment = Alignment.Start
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                            tint = deleteButtonColor
                        )
                        Text(
                            text ="Supprimer cet élément ?",
                            style = titleTextStyle,
                            fontSize = 30.sp
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 20.dp),
                        text = "Êtes vous sûr de supprimer cet élément de la liste ?",
                        style = messageTextStyle,
                        fontSize = 20.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.End
                        )
                    ) {

                        // Cancel button
                        Box(
                            modifier = Modifier
                                .clickable(
                                    // This is to disable the ripple effect
                                    indication = null,
                                    interactionSource = interactionSource
                                ) {
                                    Toast
                                        .makeText(context, "Fermer", Toast.LENGTH_SHORT)
                                        .show()
                                    onDismiss(false)
                                }
                                .border(
                                    width = 1.dp,
                                    color = cancelButtonColor,
                                    shape = RoundedCornerShape(buttonCorner)
                                )
                                .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                        ) {
                            Text(
                                text = "Fermer",
                                style = buttonTextStyle,
                                color = cancelButtonColor
                            )
                        }

                        // Delete button
                        Box(
                            modifier = Modifier
                                .clickable(
                                    // This is to disable the ripple effect
                                    indication = null,
                                    interactionSource = interactionSource
                                ) {
                                    Toast
                                        .makeText(context, "Supprimer", Toast.LENGTH_SHORT)
                                        .show()
                                    onDismiss(false)
                                }
                                .background(
                                    color = deleteButtonColor,
                                    shape = RoundedCornerShape(buttonCorner)
                                )
                                .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                        ) {
                            Text(
                                text = "Delete",
                                style = buttonTextStyle,
                                color = Color.White
                            )
                        }
                    }
                }
            }

        }
    }
}