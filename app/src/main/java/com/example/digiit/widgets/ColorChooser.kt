package com.example.digiit.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digiit.ui.theme.Primary
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.color.colorChooser
import com.vanpra.composematerialdialogs.rememberMaterialDialogState


@Composable
fun ColorChooser(text: String, color: MutableState<Color>) {
    val colorDialog = rememberMaterialDialogState()
    val colorValue = remember { mutableStateOf(color.value) }

    MaterialDialog(
        dialogState = colorDialog,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Fermer")
        }
    ) {
        colorChooser(colors = Primary, initialSelection = Primary.indexOf(colorValue.value)) {
            colorValue.value = it
            color.value = it
        }
    }

    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
            .fillMaxWidth()
            .clickable { colorDialog.show() }) {
        Button(modifier = Modifier
            .padding(horizontal = 12.dp)
            .weight(1f), onClick = { }) {
            Text(text = text, fontSize = 18.sp)
        }

        Box(Modifier.padding(end = 12.dp)) {
            Box(
                Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .background(colorValue.value)
                    .border(1.dp, MaterialTheme.colors.onBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {}
        }
    }
}
