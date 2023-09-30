package com.monke.begit.di

import android.app.Application
import android.app.admin.NetworkEvent
import com.monke.begit.data.SportRepositoryImpl
import com.monke.begit.ui.loginFeature.SignInFragment
import com.monke.begit.ui.loginFeature.signUp.EmployeeSignUpFragment
import com.monke.begit.ui.loginFeature.signUp.SignUpFragment
import com.monke.begit.ui.loginFeature.signUp.SupervisorSignUpFragment
import com.monke.begit.ui.mainFeature.activityFeature.SelectActivityFragment
import com.monke.begit.ui.mainFeature.profileFragment.ProfileFragment
import com.monke.begit.ui.mainFeature.trackActivityFeature.TrackActivityFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [
    UserModule::class,
    NetworkModule::class,
    SubdivisionModule::class,
    SportModule::class
])
interface AppComponent {

    fun inject(signUpFragment: SignUpFragment)

    fun inject(signInFragment: SignInFragment)

    fun inject(employeeSignUpFragment: EmployeeSignUpFragment)

    fun inject(supervisorSignUpFragment: SupervisorSignUpFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(selectActivityFragment: SelectActivityFragment)

    fun inject(trackActivityFragment: TrackActivityFragment)



    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Application): Builder

        fun build() : AppComponent
    }
}