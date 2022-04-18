package kz.nurkaydarov097.airpvl.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng




data class Zone(
    var id:Int = -1,
    var station: String = "",
    var street: String = "",
    var aqi:Int = -1,
    var latLng: LatLng = LatLng(0.0,0.0),
    @DrawableRes var markerRes:Int = -1,
    var markerId:String = "",
    var status:String = ""
    )