package com.monke.begit.ui.mainFeature.ratingFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.begit.App
import com.monke.begit.databinding.FragmentLeaderBoardBinding
import com.monke.begit.domain.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LeaderBoardFragment: Fragment() {


    @Inject
    lateinit var factory: LeaderBoardViewModel.Factory
    private val viewModel: LeaderBoardViewModel by viewModels { factory }

    private var binding: FragmentLeaderBoardBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeaderBoardBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LeaderboardRW(viewModel.leaderBoardUsers.value)
        binding?.listLeaderboard?.adapter = adapter
        binding?.listLeaderboard?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.leaderBoardUsers.collect {
                    adapter.setItems(it)
                }
            }
        }

    }







}