package com.example.digiit.cards

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.example.digiit.R


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
