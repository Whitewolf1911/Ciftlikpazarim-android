package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.messagespreviews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview

@Composable
fun MessagePreviewItem(
    messagePreview: MessagePreview,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(border = BorderStroke(1.dp, Color.Green), shape = RoundedCornerShape(50.dp))
            .fillMaxWidth()
            .padding(16.dp)
            .clipToBounds()
    ) {
        Text(messagePreview.username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(messagePreview.lastMessage, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }

}
