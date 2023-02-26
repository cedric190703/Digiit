package com.example.digiit.showfeatures

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.digiit.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class DataFeatures(@RawRes val resId: Int, val title: String, val description: String)
    val listData = listOf(
        DataFeatures(
            R.raw.looking,
            "Centralisation des documents",
            "Permettant à l'utilisateur d'avoir un contrôle total sur ses documents.",
        ),
        DataFeatures(
            R.raw.pictures,
            "Capture et/ou enregistrement d’un nouveau documents",
            "Permettre à l'utilisateur de sélectionner la meilleure option qui lui convient.",
        ),
        DataFeatures(
            R.raw.data_analyse,
            "Suivi budgétaire",
            "Permettant aux utilisateurs de définir et de suivre leurs revenus et leurs dépenses avec des graphiques simples et précis.",
        ),
        DataFeatures(
            R.raw.bilan,
            "Bilan selon des périodes",
            "Permettant aux utilisateurs à la fin d’une certaine période de faire un bilan sur les différentes dépenses.",
        ),
        DataFeatures(
            R.raw.evaluation,
            "Evaluation et annotation des documents",
            "Permettant aux utilisateurs de noter les différents produits ou marchands afin d’en évaluer les qualités et les défauts.",
        )
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Features(
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val (selectedPage, setSelectedPage) = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            setSelectedPage(page)
        }
    }
    Column {
        HorizontalPager(
            count = listData.size,
            state = pagerState,
            modifier = Modifier.weight(0.6f)
        ) { page ->
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(listData[page].resId))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                LottieAnimation(
                    composition,
                    /// looping the animation
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    listData[page].title,
                    style = MaterialTheme.typography.h4,
                )
                Box(modifier = Modifier.height(24.dp))
                Text(
                    listData[page].description,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            for (i in listData.indices) {
                Box(
                    modifier = Modifier
                        .padding(end = if (i == listData.size - 1) 0.dp else 5.dp)
                        .width(if (i == selectedPage) 20.dp else 10.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (i == selectedPage) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(
                                alpha = 0.1f
                            )
                        )
                )
            }
        }

        if (selectedPage != listData.size - 1) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(listData.size - 1)
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(text = "Passer")
                }

                Button(
                    onClick = {
                        scope.launch {
                            /// iterate to next screen
                            val nextPage = selectedPage + 1
                            pagerState.animateScrollToPage(nextPage)
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(text = "Page suivante")
                }

            }
        }
        //last page
        if (selectedPage == listData.size - 1) {
            Button(
                onClick = {
                    println("here1")
                    onClick()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = "Commencer")
            }
        }
    }
}
