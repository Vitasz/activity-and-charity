package com.monke.begit.di

import android.app.Application
import android.app.admin.NetworkEvent
import com.monke.begit.ui.loginFeature.SignInFragment
import com.monke.begit.ui.loginFeature.signUp.EmployeeSignUpFragment
import com.monke.begit.ui.loginFeature.signUp.SignUpFragment
import com.monke.begit.ui.loginFeature.signUp.SupervisorSignUpFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [UserModule::class, NetworkModule::class, SubdivisionModule::class])
interface AppComponent {

    fun inject(signUpFragment: SignUpFragment)

    fun inject(signInFragment: SignInFragment)

    fun inject(employeeSignUpFragment: EmployeeSignUpFragment)

    fun inject(supervisorSignUpFragment: SupervisorSignUpFragment)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Application): Builder

        fun build() : AppComponent
    }
}