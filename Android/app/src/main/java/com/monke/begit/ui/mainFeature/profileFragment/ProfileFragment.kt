package com.monke.begit.ui.mainFeature.profileFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentProfileBinding
import com.monke.begit.databinding.FragmentSignUpBinding
import com.monke.begit.domain.model.AccountType
import javax.inject.Inject

class ProfileFragment : Fragment() {


    @Inject
    lateinit var factory: ProfileViewModel.Factory
    private val viewModel: ProfileViewModel by viewModels { factory }

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.txtName?.text = viewModel.user.name

        binding?.txtAccountType?.text = when(viewModel.user.accountType) {
            AccountType.Employee -> getString(R.string.employee)
            AccountType.Supervisor -> getString(R.string.supervisor)
        }

        binding?.txtSubdivision?.text = viewModel.user.subdivision.name
    }

}