package com.monke.begit.domain.exceptions

class NoSubdivisionFoundException: Throwable() {

    override val message: String?
        get() = "No user with this email found"
}