package kz.nurkaydarov097.airpvl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.nurkaydarov097.airpvl.adapters.PollutionInfoAdapter
import kz.nurkaydarov097.airpvl.models.DataSource
import kz.nurkaydarov097.airpvlapp.databinding.FragmentPollutionBinding


class PollutionFragment: Fragment() {

    private lateinit var binding: FragmentPollutionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPollutionBinding.inflate(inflater,container, false)
        val dataSet = DataSource(requireContext()).getPollutions()
        binding.populantsRecyclerView.adapter = PollutionInfoAdapter(requireContext(), dataSet)

        return binding.root
    }
}