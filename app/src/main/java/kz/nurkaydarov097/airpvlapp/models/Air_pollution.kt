package kz.nurkaydarov097.airpvl.models

import androidx.annotation.DrawableRes

data class Air_pollution (val id:Int,val airQuality:String ,val numericalValue:String, val meaning:String, @DrawableRes val indicator:Int )