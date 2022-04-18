package kz.nurkaydarov097.airpvl.models

import androidx.annotation.DrawableRes

data class Popullant(val id: Int = -1,
                val type: String = "",
                @DrawableRes val indicator: Int = -1,
                val category: String = "",
                val concentration: String = "")