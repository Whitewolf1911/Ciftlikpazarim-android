package com.alibasoglu.ciftlikpazarimandroid.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.alibasoglu.ciftlikpazarimandroid.MainActivity
import kotlin.random.Random

private const val CHANNEL_ID = "ciftlikpazarim channel"

fun showNotification(
    context: Context,
    title: String?,
    content: String?,
    @DrawableRes smallIconResId: Int
) {
    val intent = Intent(context, MainActivity::class.java)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationID = Random.nextInt()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(notificationManager)
    }

    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(smallIconResId)
        .setColor(Color.GREEN)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(notificationID, notification)
}


@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(notificationManager: NotificationManager) {
    val channelName = "channelName"
    val channel = NotificationChannel(
        CHANNEL_ID,
        channelName,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "Ciftlikpazarim channel description"
        enableLights(true)
        lightColor = Color.GREEN
    }
    notificationManager.createNotificationChannel(channel)
}
