package kz.nurkaydarov097.airpvl.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.nurkaydarov097.airpvl.models.Air_pollution
import kz.nurkaydarov097.airpvlapp.R

class PollutionInfoAdapter(val context: Context, val dataSet:List<Air_pollution>) : RecyclerView.Adapter<PollutionInfoAdapter.PollutionItemViewHolder>()
{

    inner class PollutionItemViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val pollution_quality: TextView = view.findViewById<TextView>(R.id.pollution_quality)
        val pollution_indicator: View = view.findViewById<View>(R.id.pollution_indicator)
        val pollution_numerical_value:TextView = view.findViewById<TextView>(R.id.pollution_numerical_value)
        val pollution_meaning:TextView = view.findViewById<TextView>(R.id.pollution_meaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollutionItemViewHolder {
        val adapterLayout = LayoutInflater.from(context).inflate(R.layout.pollution_item, parent, false)
        return PollutionItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PollutionItemViewHolder, position: Int) {
        val item = dataSet[position]
        holder.pollution_quality.text = item.airQuality
        holder.pollution_indicator.setBackgroundResource(item.indicator)
        holder.pollution_numerical_value.text = item.numericalValue
        holder.pollution_meaning.text = item.meaning
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}