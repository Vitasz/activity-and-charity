package com.monke.begit.domain

import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.model.Fund
import com.monke.begit.domain.model.SportActivity
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
        name = "Maxim",
        surname = "Boolean",
        login = "BigBoolean",
        password = "boolean",
        email = "booleam@org.com",
        accountType = AccountType.Employee),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "Mathematic",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "Graph",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "SosiskaKiller",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "NinjaTurtle",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "MonkeMaster",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "Androider",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "SmallCoroutine",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "AsyncTask228",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),
    User(
        name = "Роман",
        surname = "Дзержинский",
        login = "Platonova",
        password = "platonovGraph",
        email = "1234567@org.com",
        accountType = AccountType.Supervisor,
    ),

)

val mockedFunds = listOf(
    Fund(
        name = "Помощь Животным"
    ),
    Fund(
        name = "Наркотикам - нет!"
    ),
    Fund(
        name = "Помощь Донбассу"
    ),
    Fund(
        name = "Фонд борьбы с ВТ им. Беркова"
    )
)