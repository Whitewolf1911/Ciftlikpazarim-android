package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SentMessageBox(
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 120.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(color = 0xffDEF6D3), shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
            )
        }
    }
}
