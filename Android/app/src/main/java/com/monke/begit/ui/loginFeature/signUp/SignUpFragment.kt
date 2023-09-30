package com.monke.begit.ui.loginFeature.signUp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSignUpBinding
import com.monke.begit.domain.model.AccountType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

        setupEmailEditText()
        setupLoginEditText()
    }

    private fun setupToolbar() {
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun setupEmailEditText() {
        binding?.inputEditTxtEmail?.setText(viewModel.email.value)
        binding?.inputEditTxtEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.setEmail(text.toString()) }

            }
        })
    }

    private fun setupLoginEditText() {
        binding?.inputEditTxtLogin?.setText(viewModel.login.value)
        binding?.inputEditTxtLogin?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.setLogin(text.toString()) }
            }
        })
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataIsValid.collect { binding?.btnNext?.isEnabled = it }
            }
        }
        binding?.btnNext?.setOnClickListener { btnNext ->
            viewModel.saveData()
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