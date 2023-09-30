package com.monke.begit.domain.model

import com.monke.begit.data.remote.GetUserRemote
import com.monke.begit.data.remote.LoginUserRemote
import com.monke.begit.data.remote.PostUserRemote
import com.monke.begit.hashString

data class User(
    val name: String,
    val surname: String,
    val login: String,
    val password: String? = null,
    val email: String,
    val accountType: AccountType,
    val subdivisionId: Int? = null,
    val selectedFundId: Int? = null
){
    companion object{
        fun fromRemote(getUserRemote: GetUserRemote, accountType: AccountType) = User(
            login = getUserRemote.username,
            name = getUserRemote.name.split(" ")[0],
            surname = getUserRemote.name.split(" ").getOrNull(1) ?: "",
            subdivisionId = getUserRemote.idSubdivision,
            selectedFundId = getUserRemote.selectedFund,
            email = getUserRemote.email,
            accountType = accountType
        )

        fun toRemote(user: User) = PostUserRemote(
            username = user.login,
            password = hashString(user.password ?: "", "SHA-256"),
            email = user.email,
            name = user.name + " " + user.surname
        )

        fun toLoginUser(user: User) = LoginUserRemote(
                username = user.login,
                password = hashString(user.password ?: "", "SHA-256")
            )

    }
}
