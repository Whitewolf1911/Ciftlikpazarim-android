package com.alibasoglu.ciftlikpazarimandroid.core

import com.alibasoglu.ciftlikpazarimandroid.User

object UserObject {
    var id: String = ""
    var name: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var type: String = ""
    var token: String = ""
    var deviceToken: String = ""
    var favorites: List<String> = emptyList()
    var contacts: List<String> = emptyList()
    var blockedUsers: List<String> = emptyList()
}

fun setUserObjectFromModel(user: User) {
    with(user) {
        UserObject.id = _id ?: ""
        UserObject.name = name
        UserObject.email = email
        UserObject.phoneNumber = phoneNumber
        UserObject.type = type
        UserObject.token = token
        UserObject.deviceToken = deviceToken
        UserObject.favorites = favorites
        UserObject.contacts = contacts
        UserObject.blockedUsers = blockedUsers
    }
}
