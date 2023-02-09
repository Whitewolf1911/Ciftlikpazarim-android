package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReceivedMessageBox(
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(start = 8.dp, end = 120.dp, top = 2.dp, bottom = 2.dp)
            .background(Color(color = 0xffECEFF1), shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            fontSize = 16.sp
        )
    }
}
