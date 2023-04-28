

package com.example.digiit.graphs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digiit.data.TradeKinds
import com.example.digiit.data.UserProvider
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalTime

val icons = listOf<TypeGraph>(
    TypeGraph.LineChart,
    TypeGraph.CubicLine,
    TypeGraph.BarChart,
    TypeGraph.HorizontalBarChart,
    TypeGraph.RadarChartData,
    TypeGraph.PieChart)

var StartDate: LocalDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
var EndDate: LocalDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
val selectedOptions = mutableStateListOf<String>()
var ListOfType=mutableStateListOf<TradeKinds>()
fun addandcheckifin(str : TradeKinds)
{
    if (!ListOfType.contains(str)) {
        ListOfType.add(str)
    }
    println("plussssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss LAIDE")
}
fun removeandcheckifin(str : TradeKinds)
{
    val index = ListOfType.indexOfFirst { it.title == str.title }
    if (index != -1) {
        ListOfType.removeAt(index)
    }
    println(ListOfType.size)
}
@Composable
fun MultiSelectDropdownList(
    options: List<TradeKinds>,
    selectedOptions: MutableList<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Bouton de sélection
        Button(
            onClick = { expanded = true },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (selectedOptions.isNotEmpty()) {
                    if (selectedOptions.size==1)
                    {
                        "${selectedOptions.size} filtre sélectionné"
                    }
                    else
                    "${selectedOptions.size} filtres sélectionnés"
                } else {
                    "Filtres avancés"
                }
            )
        }

        // Liste des options
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                val isChecked = selectedOptions.contains(option.title)
                DropdownMenuItem(
                    onClick = {
                        if (isChecked) {
                            selectedOptions.remove(option.title)
                            removeandcheckifin(option)
                            println("JesaisPas plus" + option.title)
                        } else {
                            selectedOptions.add(option.title)
                            addandcheckifin(option)
                            println("ddadadada plus" + option.title)
                        }
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                if (isChecked) {
                                    selectedOptions.remove(option.title)
                                    println("delete" + option.title)
                                } else {
                                    selectedOptions.add(option.title)
                                    println("add" + option.title)
                                }
                            }
                        )

                        Text(
                            text = option.title,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun MyScreen(auth: UserProvider) {
    var reallist= listOf<TradeKinds>().toMutableList()
    auth.user!!.tickets.forEach(){
        if (!reallist.contains(it.type)){
            reallist.add(it.type)

        }

    }
    val selectedOptions = selectedOptions
    MultiSelectDropdownList(options = reallist, selectedOptions = selectedOptions)
}
@Composable
fun DialogGraph(auth: UserProvider,setShowDialog: (Boolean) -> Unit) {
    val dateDialogState = rememberMaterialDialogState()
    var pickedDateDebut by remember {
        mutableStateOf(LocalDateTime.now())
    }
    if (StartDate!= LocalDateTime.now())
    {
        pickedDateDebut= StartDate
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDateDebut)
        }
    }
    val dateDialogState2 = rememberMaterialDialogState()
    var pickedDateDebut2 by remember {
        mutableStateOf(LocalDateTime.now())
    }
    if (EndDate!= LocalDateTime.now())
    {
        pickedDateDebut2= EndDate
    }
    val formattedDate2 by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDateDebut2)
        }
    }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(23.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            color = MaterialTheme.colors.primary,
                            thickness = 5.dp,
                            modifier = Modifier
                                .height(1.5.dp)
                                .weight(1f)
                        )
                        IconButton(onClick = { setShowDialog(false) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(45.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "   De",
                            fontSize = 22.sp,
                            modifier = Modifier
                                .weight(1f)
                                .width(5.dp)
                        )
                        Text(
                            text = "  Jusqu'à",
                            fontSize = 22.sp,
                            modifier = Modifier
                                .weight(1f)
                                .width(5.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    )

                    {
                        Row {
                            Row()
                            {
                                Button(
                                    onClick = {
                                        dateDialogState.show()
                                    }
                                ) {
                                    Card(
                                        elevation = 0.dp,
                                        border = BorderStroke(0.dp, MaterialTheme.colors.primary),
                                        modifier = Modifier.padding(0.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(3.dp),
                                            text = formattedDate,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = {
                                        dateDialogState2.show()
                                    }
                                ) {
                                    Card(
                                        elevation = 0.dp,
                                        border = BorderStroke(0.dp, MaterialTheme.colors.primary),
                                        modifier = Modifier.padding(0.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(3.dp),
                                            text = formattedDate2,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                MaterialDialog(
                                    dialogState = dateDialogState,
                                    buttons = {
                                        positiveButton(text = "Ok")
                                        negativeButton(text = "Fermer")
                                    }
                                ) {
                                    datepicker(
                                        initialDate = StartDate.toLocalDate(),
                                        title = "Sélectionner une date",

                                        ) {
                                        pickedDateDebut = it.atStartOfDay()
                                        StartDate=pickedDateDebut
                                    }
                                }
                                MaterialDialog(
                                    dialogState = dateDialogState2,
                                    buttons = {
                                        positiveButton(text = "Ok")
                                        negativeButton(text = "Fermer")
                                    }
                                ) {
                                    datepicker(
                                        initialDate =EndDate.toLocalDate(),
                                        title = "Sélectionner une date",
                                    ) {
                                        pickedDateDebut2 = it.atStartOfDay()
                                        EndDate=pickedDateDebut2
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        color = MaterialTheme.colors.primary,
                        thickness = 5.dp,
                        modifier = Modifier
                            .height(1.5.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MyScreen(auth)
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        color = MaterialTheme.colors.primary,
                        thickness = 5.dp,
                        modifier = Modifier
                            .height(1.5.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Sélectionner un type de graphique à ajouter: ",
                        fontSize = 27.sp
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    repeat(3) { index ->
                        val firstIconIndex = index * 2
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ExtendedFloatingActionButton(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    onClick = {
                                        setShowDialog(false)
                                        when (firstIconIndex){
                                            0->auth.user!!.listGraphs.add(TypeGraph.CubicLine)
                                            2->auth.user!!.listGraphs.add(TypeGraph.BarChart)
                                            4->auth.user!!.listGraphs.add(TypeGraph.RadarChartData)
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = icons[firstIconIndex].icon),
                                            contentDescription = "Favorite",
                                            tint = Color.White
                                        )
                                    },
                                    text = {
                                        Text(
                                            icons[firstIconIndex].title,
                                            color = Color.White,
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                val secondIconIndex = firstIconIndex + 1
                                ExtendedFloatingActionButton(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    onClick = {
                                        setShowDialog(false)
                                        when (secondIconIndex){
                                            1->auth.user!!.listGraphs.add(TypeGraph.LineChart)
                                            5->auth.user!!.listGraphs.add(TypeGraph.PieChart)
                                            3->auth.user!!.listGraphs.add(TypeGraph.HorizontalBarChart)
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = icons[secondIconIndex].icon),
                                            contentDescription = "Favorite",
                                            tint = Color.White
                                        )
                                    },
                                    text = {
                                        Text(
                                            icons[secondIconIndex].title,
                                            color = Color.White
                                        )
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

