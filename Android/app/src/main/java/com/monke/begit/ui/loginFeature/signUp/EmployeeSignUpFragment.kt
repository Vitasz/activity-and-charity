package com.monke.begit.ui.loginFeature.signUp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentEmployeeSignUpBinding
import com.monke.begit.domain.exceptions.NoSubdivisionFoundException
import com.monke.begit.ui.uiModels.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmployeeSignUpFragment : Fragment() {

    @Inject
    lateinit var factory: EmployeeSignUpViewModel.Factory
    private val viewModel: EmployeeSignUpViewModel by viewModels { factory }

    private var binding: FragmentEmployeeSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeSignUpBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignUp?.setOnClickListener {
            viewModel.signUp()
        }

        setupNameEditText()
        setupPasswordEditText()
        setupSurnameEditText()
        setupRepeatedPasswordEditText()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        // В случае ошибки показывает toast
                        is UiState.Error -> {
                            val message = when (state.exception) {
                                is NoSubdivisionFoundException ->
                                    getString(R.string.incorrect_subdiv_code)
                                else -> state.exception.message
                            }
                            Toast.makeText(
                                requireContext(),
                                message,
                                Toast.LENGTH_SHORT).show()
                        }
                        // В случае загрузки показывает диалог с прогрессом
                        UiState.Loading -> {
                            //showLoadingDialog()
                        }
                        // В случае успеха переходит на следующий фрагмет
                        is UiState.Success -> {
                            view.findNavController()
                                .navigate(R.id.action_employeeSignUpFragment_to_mainFragment)
                        }
                        // При отсутствующем состоянии ничего не делает
                        null -> {}
                    }
                }
            }
        }
    }

    private fun setupNameEditText() {
        binding?.inputName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.name = text.toString() }
            }
        })
    }

    private fun setupSurnameEditText() {
        binding?.inputSurname?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.surname = text.toString() }
            }
        })
    }

    private fun setupPasswordEditText() {
        binding?.inputPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.password = text.toString() }
            }
        })
    }

    private fun setupRepeatedPasswordEditText() {
        binding?.inputRepeatedPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.repeatedPassword = text.toString() }
            }
        })
    }



}