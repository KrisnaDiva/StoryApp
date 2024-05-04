package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.databinding.FragmentHomeBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.view.adapter.LoadingStateAdapter
import com.krisna.diva.storyapp.ui.view.adapter.StoryAdapter
import com.krisna.diva.storyapp.ui.viewmodel.HomeViewModel
import com.krisna.diva.storyapp.utils.NetworkUtils

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(requireContext(), AddStoryActivity::class.java))
        }

        binding.rvStory.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }

        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.no_internet)
                .setMessage(R.string.no_internet_description)
                .setPositiveButton(R.string.ok) { _, _ ->
                    requireActivity().finish()
                }
                .show()
        } else {
            getData()
        }
    }

    private fun getData() {
        val adapter = StoryAdapter { story, optionsCompat ->
            startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_STORY, story)
            }, optionsCompat.toBundle())
        }
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.listStory.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
