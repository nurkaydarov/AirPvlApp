package kz.nurkaydarov097.airpvl.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.nurkaydarov097.airpvl.models.Zone
import kz.nurkaydarov097.airpvlapp.R
import kz.nurkaydarov097.airpvlapp.databinding.ZoneItemBinding


class InfoAdapter(val context: Context): RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

inner class ViewHolder(val binding: ZoneItemBinding):RecyclerView.ViewHolder(binding.root)
    val mContext = context
    private var item:MutableList<Zone> = mutableListOf<Zone>()

    fun setListData(data: MutableList<Zone>){
        item = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ZoneItemBinding.inflate(inflate, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemB: Zone = item[position]

        with(holder.binding){

            if(itemB.street != null)
            {
                zoneStationTextView.text = itemB.street

                when(itemB.aqi){
                    in 0..50 -> zoneAQITextView.setBackgroundResource(R.drawable.bd_round_good)
                    in 51..100 -> zoneAQITextView.setBackgroundResource(R.drawable.bg_round_normal)
                    in 101..150 ->  zoneAQITextView.setBackgroundResource(R.drawable.bg_round_bad)
                    in 151..200 -> zoneAQITextView.setBackgroundResource(R.drawable.bg_round_danger)
                    else -> zoneAQITextView.setBackgroundResource(R.drawable.bg_round_danger)

                }
                zoneAQITextView.text = itemB.aqi.toString()

            }
            else{
                return
            }


        }
        //return holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }
}