package com.monke.begit.data.remote

data class User(val id:Int,
           val username: String,
           val email: String,
           val name: String,
           val selectedFund: Int,
           val idSubdivision: Int) {
}