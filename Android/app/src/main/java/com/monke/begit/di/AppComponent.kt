package com.monke.begit.di

import android.app.Application
import com.monke.begit.ui.loginFeature.EmployeeSignUpFragment
import com.monke.begit.ui.loginFeature.SignUpFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component
interface AppComponent {

    fun inject(signUpFragment: SignUpFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Application): Builder

        fun build() : AppComponent
    }
}