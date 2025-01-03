package kz.nurkaydarov097.airpvl.models
import android.content.Context
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import android.util.Log
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import kz.nurkaydarov097.airpvlapp.R
import org.jsoup.Connection

import org.jsoup.nodes.Document

class JsoupData {
    lateinit var myContext: Context
    companion object{
        var instance:JsoupData? = null

    }

    fun getInstance (context: Context): JsoupData? {
        myContext = context

        if(instance == null){
            instance = JsoupData()
        }

        return instance!!
    }

    fun getDataFromJsoup():MutableList<Zone>{
        val listData:MutableList<Zone> = mutableListOf<Zone>()

        var zone: Zone = Zone()
        try{
            var id:Int = 0
            var station:String = ""
            var street:String = ""
            var aqi:Int = 0
            var latLng:LatLng = LatLng(52.2605905, 76.9539889)
            var markerId:String = ""
            var status:String = ""
            @DrawableRes var markerRes:Int = R.drawable.ic_marker_normal

            val url:String = "https://www.iqair.com/ru/kazakhstan/pavlodar"
            val response: Connection.Response = Jsoup.connect(url).ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0")
                .referrer("https://www.google.com")
                .timeout(12000)
                .followRedirects(true)
                .execute();
            val doc = response.parse()
            val table: Elements = doc.select(".ranking__table") // // Создаю массив таблиц
            val stationTable:Elements = table.get(1).children(); // Выбераю втрорую таблицу и ее елементы
            val tbodyStationTable: Element = stationTable.get(1); // Выбераю <tbody>
            val tbodyStationTableSize = tbodyStationTable.childrenSize()
            for(i:Int in 0 until tbodyStationTableSize){
                if(tbodyStationTable.children().get(i).child(1).text().contains("Pavlodar - no.6: st. Zaton"))
                {
                    id = 1
                    station = tbodyStationTable.children().get(i).child(1).text().substring(21)
                    aqi = tbodyStationTable.children().get(i).child(2).text().toInt()
                    street = "ул. Затон 39"
                    latLng = LatLng(52.2587602,76.9455512)
                    markerRes = getStatusAqi(aqi)
                    markerId = ""
                    status = getStatus(aqi).toString()
                }
                else if(tbodyStationTable.children().get(i).child(1).text().contains("Pavlodar - no.7: st. Toraigyrova-Dyusenova"))
                {
                    id = 2
                    station = tbodyStationTable.children().get(i).child(1).text().substring(21)
                    aqi = tbodyStationTable.children().get(i).child(2).text().toInt()
                    street = "ул.Торайгырова-генерала Дюсенова"
                    latLng = LatLng(52.2964403,76.9420633)
                    markerRes = getStatusAqi(aqi)
                    markerId = ""
                    status = getStatus(aqi).toString()
                }
                else if(tbodyStationTable.children().get(i).child(1).text().contains("Pavlodar - no.5: st. Estaya"))
                {
                    id = 3
                    station = tbodyStationTable.children().get(i).child(1).text().substring(21)
                    aqi = tbodyStationTable.children().get(i).child(2).text().toInt()
                    street = "ул.Естая 54"
                    latLng = LatLng(52.2813572,76.9457644)
                    markerRes = getStatusAqi(aqi)
                    markerId = ""
                    status = getStatus(aqi).toString()
                }
                else if(tbodyStationTable.children().get(i).child(1).text().contains("Pavlodar - no.3: st. Lomova"))
                {
                    id = 4
                    station = tbodyStationTable.children().get(i).child(1).text().substring(21)
                    aqi = tbodyStationTable.children().get(i).child(2).text().toInt()
                    street = "ул.Ломова 64"
                    latLng = LatLng(52.2644136,76.9609643)
                    markerRes = getStatusAqi(aqi)
                    markerId = ""
                    status = getStatus(aqi).toString()
                }
                else if(tbodyStationTable.children().get(i).child(1).text().contains("Pavlodar - no.4: st. Kaz. Truth"))
                {
                    id = 5
                    station = tbodyStationTable.children().get(i).child(1).text().substring(20)
                    aqi = tbodyStationTable.children().get(i).child(2).text().toInt()
                    street = "ул.Каз Правды"
                    latLng = LatLng(52.2475485,76.9845023)
                    markerRes = getStatusAqi(aqi)
                    markerId = ""
                    status = getStatus(aqi).toString()
                }
                listData.add(Zone(id, station, street, aqi, latLng, markerRes, markerId, status))
            }
        }
        catch(e: IOException)
        {
            e.printStackTrace()
        }
        var sortedList:MutableList<Zone> = listData.sortedBy { it.id }.toMutableList()
        return sortedList
    }

    fun getInformationData():MutableList<Information>{
        var listData:MutableList<Information> = mutableListOf<Information>()
        var information:Information = Information()

        try{
            val url:String = "https://www.iqair.com/ru/kazakhstan/pavlodar"
            val doc: Document = Jsoup.connect(url).get()
            val table:Elements = doc.select(".aqi-overview-detail__main-pollution-table")
            val detailTable = table.get(0).children()
            val tbody = detailTable.get(1).children().get(0)
            val indexAir = tbody.child(1).text().subSequence(0,2).toString().trim()
            val mainPopulant = tbody.child(2).text()
            Log.d("DETAIL", indexAir)
            information.indexAir  = indexAir
            information.mainPopulant = mainPopulant
            listData.add(information)

        }
        catch(e: IOException){
            e.printStackTrace()
        }

        return listData

    }
    @DrawableRes
    fun getStatusAqi(aqi:Int):Int{
       return when(aqi){
            in 0..50 -> R.drawable.ic_marker_good
            in 51..100 -> R.drawable.ic_marker_normal
            in 101..150 ->  R.drawable.ic_marker_bad
            in 151..200 -> R.drawable.ic_marker_danger
            else ->  R.drawable.ic_marker_dangerous

        }
    }
    fun getStatus(aqi:Int):String{
        return when(aqi){
            in 0..50 -> "Хороший"
            in 51..100 -> "Средний"
            in 101..150 -> "Плохой"
            in 151..200 -> "Очень Плохой"
            else ->  "Опасный!"
        }
    }


}