package com.monke.begit.ui.mainFeature.trackActivityFeature

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentProfileBinding
import com.monke.begit.databinding.FragmentTrackActivityBinding
import com.monke.begit.ui.uiModels.SportActivityState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrackActivityFragment : Fragment() {

    @Inject
    lateinit var factory: TrackActivityViewModel.Factory
    private val viewModel: TrackActivityViewModel by viewModels { factory }

    private var binding: FragmentTrackActivityBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackActivityBinding.inflate(inflater, container, false)
        (activity?.application as? App)?.appComponent?.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStopButton()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sportActivityState.collect { state ->
                    when (state) {
                        SportActivityState.Play -> {
                            binding?.btnPlay?.setImageDrawable(
                                ContextCompat.getDrawable
                                    (requireContext(),
                                    R.drawable.ic_pause)
                            )
                            binding?.btnPlay?.setOnClickListener {
                                viewModel.setSportActivityState(SportActivityState.Pause)
                            }
                            binding?.btnStop?.visibility = View.INVISIBLE
                        }
                        SportActivityState.Pause -> {
                            binding?.btnPlay?.setImageDrawable(
                                ContextCompat.getDrawable
                                    (requireContext(),
                                    R.drawable.ic_play_circle)
                            )
                            binding?.btnPlay?.setOnClickListener {
                                viewModel.setSportActivityState(SportActivityState.Play)
                            }
                            binding?.btnStop?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupStopButton() {
        binding?.btnStop?.setOnClickListener {
            viewModel.stopActivity()
        }
    }

}