package com.monke.begit

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar

fun String.md5(): String {
    return hashString(this, "MD5")
}

fun String.sha256(): String {
    return hashString(this, "SHA-256")
}

fun hashString(input: String, algorithm: String): String {
    return MessageDigest
        .getInstance(algorithm)
        .digest(input.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })
}

fun getTodayDateString(): String {
    val current = Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(current.time)
}