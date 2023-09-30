package com.monke.begit.domain

import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.model.Subdivision
import com.monke.begit.domain.model.User

val mockedSubdivisions = listOf(
    Subdivision(
        id = 1,
        name = "Yandex",
        code = "Верхний Ларс"
    ),
    Subdivision(
        id = 2,
        name = "Soviet Games",
        code = "BaZa"
    ),
    Subdivision(
        id = 3,
        name = "Cocos group",
        code = "Kokain"
    )
)

val mockedUsers = listOf(
    User(
        id = 1,
        name = "Maxim",
        surname = "Boolean",
        login = "bigBoolean",
        password = "boolean",
        email = "booleam@org.com",
        accountType = AccountType.Employee,
        subdivision = mockedSubdivisions[1]),
    User(
        id = 2,
        name = "Роман",
        surname = "Дзержинский",
        login = "BigBoss",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
        subdivision = mockedSubdivisions[0])
)