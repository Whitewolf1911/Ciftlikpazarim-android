package com.alibasoglu.ciftlikpazarimandroid.messaging.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alibasoglu.ciftlikpazarimandroid.R


@Composable
fun ErrorComposable(errorText: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.ic_error_24),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 18.dp),
            text = errorText,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}
