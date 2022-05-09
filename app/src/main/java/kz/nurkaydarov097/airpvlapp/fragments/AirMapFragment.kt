package kz.nurkaydarov097.airpvl.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kz.nurkaydarov097.airpvl.models.Zone
import kz.nurkaydarov097.airpvl.viewModels.JsoupDataViewModel
import kz.nurkaydarov097.airpvl.viewModels.PopullantViewModel
import kz.nurkaydarov097.airpvlapp.R
import kz.nurkaydarov097.airpvlapp.databinding.FragmentAirMapBinding
import kz.nurkaydarov097.airpvlapp.utils.CheckInternet

class AirMapFragment: Fragment(), OnMapReadyCallback {

    private  var _binding: FragmentAirMapBinding? = null
    private lateinit var zones:List<Zone>

    private lateinit var marker:Marker
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val viewModel by lazy{ViewModelProvider(this).get(JsoupDataViewModel::class.java)}
    private val listViewModel by lazy{ViewModelProvider(this).get(PopullantViewModel::class.java)}
    private var  mMap: GoogleMap? = null
    private var mapReady = false
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAirMapBinding.inflate(inflater, container, false)

        val internetConnect = CheckInternet(requireContext())
        if(internetConnect.isConnected()){
            binding.loadingContainer.visibility = VISIBLE
            binding.progressBar.visibility = VISIBLE
            viewModel.fetchData().observe(viewLifecycleOwner, Observer{
                    zones ->
                this.zones = zones
                updateMap()
                binding.loadingContainer.visibility = INVISIBLE
                binding.progressBar.visibility = INVISIBLE
            })
            binding.noInternetContainer.visibility = INVISIBLE
            binding.mapFragmentContainer.visibility = VISIBLE
        }
        else{
            binding.noInternetContainer.visibility = VISIBLE
            binding.mapFragmentContainer.visibility = INVISIBLE
        }
        binding.reloadBtn.setOnClickListener {

            if(internetConnect.isConnected()){
                viewModel.fetchData().observe(viewLifecycleOwner, Observer{
                        zones ->
                    this.zones = zones
                    updateMap()
                })
                binding.noInternetContainer.visibility = INVISIBLE
                binding.mapFragmentContainer.visibility = VISIBLE
            }
            else{
                Toast.makeText(requireContext(), getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show()
            }
        }

        /**/

        /*Иницализация карты*/
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync (object : OnMapReadyCallback{
            override fun onMapReady(googleMap: GoogleMap) {
                mMap = googleMap
                mapReady = true
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(startMarker, CITYSCALE))

                updateMap()
            }
        })

        /****************/

        binding.updateBtn.setOnClickListener{


            if(internetConnect.isConnected()){
                updateMap()
                Toast.makeText(requireContext(), "Данные обновлены", Toast.LENGTH_SHORT).show()
                Log.d("AKTAN", "Данные обновлены")
            }
            else{
                binding.mapFragmentContainer.visibility = INVISIBLE
                binding.noInternetContainer.visibility = VISIBLE
            }

        }



        val view = binding.root
        return view
    }

   override fun onMapReady(googleMap: GoogleMap) {
       mMap = googleMap
       mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL

   }

    private fun updateMap() {
        mMap?.clear()
        if(mapReady && ::zones.isInitialized  )
        {
            zones.forEach{
                    zone ->
                if(zone.latLng != null)
                {
                    val lanLng = LatLng(zone.latLng.latitude,zone.latLng.longitude)

                  val markerOptions: MarkerOptions =  MarkerOptions().
                    position(lanLng).
                    title(zone.street).
                    icon(bitmapDescriptorFromVector(requireActivity(),zone.markerRes))
                    Log.d("marker_id", zone.markerId)
                    marker = mMap?.addMarker(markerOptions)!!

                    // Обновляет id в обьекте на текущий
                    zone.markerId = marker.id // Подсказал Дмитрий Владимиров
                    ///for(zone:Zone in zones){
                     //   Log.d("Updated", zone.markerId)
                   // }
                }
            }
            mMap?.setOnMarkerClickListener { marker ->
                for(zone:Zone in zones){
                    if(zone.markerId.equals(marker.id)){
                        showDialog(zone.street, "AQI = " + zone.aqi.toString() + "\nСостояние AQI: " + zone.status)
                    }
                }
                false
            }
        }
    }


    private fun showDialog(text:String, message:String){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(text)
            .setMessage(message)
            .setPositiveButton("OK"){
                    dialog, which->
            }
            .create()
        dialog.show()
    }


    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object{
        @JvmStatic val CITYSCALE:Float = 13F
        @JvmStatic val startMarker:LatLng = LatLng(52.2821354,76.9591792)
    }
}