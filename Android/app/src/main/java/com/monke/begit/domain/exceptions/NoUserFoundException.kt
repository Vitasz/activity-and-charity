package com.monke.begit.domain.exceptions

class NoUserFoundException: Throwable() {

    override val message: String?
        get() = "No user with this email found"
}