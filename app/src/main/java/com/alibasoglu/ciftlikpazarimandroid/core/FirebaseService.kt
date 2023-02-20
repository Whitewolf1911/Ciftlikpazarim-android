package com.alibasoglu.ciftlikpazarimandroid.core

import android.content.Intent
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.utils.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(this, message.notification?.title, message.notification?.body, R.drawable.ic_bee)
        sendBroadcast(Intent("msgReceived"))
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}
