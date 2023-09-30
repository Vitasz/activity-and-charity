package com.monke.begit.domain.exceptions

class IncorrectPasswordException: Throwable() {

    override val message: String?
        get() = "Incorrect password"
}