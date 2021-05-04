package com.hieuminh.chatapp.utils

fun String.lastName(): String {
    val list = this.split(" ")
    return list.last()
}
