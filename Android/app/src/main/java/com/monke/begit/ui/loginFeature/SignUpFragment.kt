package com.monke.begit.ui.loginFeature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSignUpBinding
import com.monke.begit.databinding.FragmentStartBinding
import com.monke.begit.domain.model.AccountType
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null

    @Inject
    lateinit var factory: SignUpViewModel.Factory
    private val viewModel: SignUpViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupNextBtn()
        setupRadioGroup()
    }

    private fun setupToolbar() {
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun setupRadioGroup() {
        when (viewModel.accountType) {
            AccountType.Employee -> binding?.btnEmployee?.isSelected = true
            AccountType.Supervisor -> binding?.btnSupervisor?.isSelected = true
        }
        binding?.btnSupervisor?.setOnClickListener {
            viewModel.accountType = AccountType.Supervisor
        }
        binding?.btnEmployee?.setOnClickListener {
            viewModel.accountType = AccountType.Employee
        }
    }

    private fun setupNextBtn() {
        binding?.btnNext?.setOnClickListener { btnNext ->
            when (viewModel.accountType) {
                AccountType.Supervisor -> btnNext
                    .findNavController()
                    .navigate(R.id.action_signUpFragment_to_supervisorSignUpFragment)
                AccountType.Employee -> btnNext
                    .findNavController()
                    .navigate(R.id.action_signUpFragment_to_employeeSignUpFragment)
            }
        }
    }

}