package kz.nurkaydarov097.airpvl.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.nurkaydarov097.airpvl.adapters.InfoAdapter
import kz.nurkaydarov097.airpvl.models.Zone
import kz.nurkaydarov097.airpvl.viewModels.JsoupDataViewModel
import kz.nurkaydarov097.airpvlapp.R
import kz.nurkaydarov097.airpvlapp.databinding.FragmentListBinding
import kz.nurkaydarov097.airpvlapp.utils.CheckInternet


class ListFragment: Fragment() {
    private lateinit var binding : FragmentListBinding
    private lateinit var infoAdapter: InfoAdapter
    private val viewModel by lazy{ ViewModelProvider(this).get(JsoupDataViewModel::class.java)}
    private lateinit var zones:List<Zone>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        infoAdapter = InfoAdapter(requireContext())
        binding.recyclerView.adapter = infoAdapter

        if(CheckInternet(requireContext()).isConnected())
        {

            /*Loading*/
            binding.loadingContainer?.visibility = VISIBLE
            binding.progressBar?.visibility = VISIBLE
            /***/

            viewModel.fetchData().observe(viewLifecycleOwner, Observer {

                infoAdapter.setListData(it)
                binding.loadingContainer.visibility = INVISIBLE
                binding.progressBar.visibility = INVISIBLE
                Log.d("MADINA", "SENDED")
            })

            viewModel.fetchDetailInfo().observe(viewLifecycleOwner, Observer{
                binding.averageIndexAQICountTextView.text = it.get(0).indexAir
                binding.currentWeatherLocationTextView.text = getString(R.string.mainPopulant, it.get(0).mainPopulant)


                when(it.get(0).indexAir.toInt()){

                    in 0..50 -> binding.mainBanner.setBackgroundResource(R.drawable.bd_round_good)
                    in 51..100 -> binding.mainBanner.setBackgroundResource(R.drawable.bg_round_normal)
                    in 101..150 ->  binding.mainBanner.setBackgroundResource(R.drawable.bg_round_bad)
                    in 151..200 -> binding.mainBanner.setBackgroundResource(R.drawable.bg_round_danger)
                    else -> binding.mainBanner.setBackgroundResource(R.drawable.bg_round_danger)

                }

                binding.loadingContainer?.visibility = INVISIBLE
                binding.progressBar?.visibility = INVISIBLE

                Log.d("MADINA", "SENDED")
            })
            binding.noInternetContainer.visibility = INVISIBLE
            binding.containerContent.visibility = VISIBLE
        }
        else{
            binding.noInternetContainer.visibility = VISIBLE
            binding.containerContent.visibility = INVISIBLE
        }

        binding.reloadBtn.setOnClickListener { it ->
            if(CheckInternet(requireContext()).isConnected())
            {
                viewModel.fetchData().observe(viewLifecycleOwner, Observer {

                    infoAdapter.setListData(it)
                    Log.d("MADINA", "SENDED")
                })
                binding.noInternetContainer.visibility = INVISIBLE
                binding.containerContent.visibility = VISIBLE
            }
            else{
                Toast.makeText(requireContext(), getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show()
            }

        }




        return binding.root

    }
}