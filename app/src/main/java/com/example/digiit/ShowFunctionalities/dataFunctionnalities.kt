package com.example.digiit.ShowFunctionalities

import androidx.annotation.RawRes
import com.example.digiit.R

class GetStartedData(@RawRes val resId: Int, val title: String, val description: String)

    val listData = listOf(
    GetStartedData(
        R.raw.looking,
        "Centraliser tout",
        "Aliquam pharetra tortor nec odio pellentesque dignissim. Etiam ultricies sollicitudin est sit amet rutrum.",
    ),
)

