package kz.nurkaydarov097.airpvl.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kz.nurkaydarov097.airpvl.models.Information
import kz.nurkaydarov097.airpvl.models.JsoupData
import kz.nurkaydarov097.airpvl.models.Zone


class JsoupDataViewModel: ViewModel() {
    private val jsoupData = JsoupData()
    val items:MutableLiveData<MutableList<Zone>> = MutableLiveData()
    val detailItem:MutableLiveData<MutableList<Information>> = MutableLiveData()

    fun init(context: Context){
        if (items != null){
            return
        }
    }

    fun fetchData():MutableLiveData<MutableList<Zone>>{
        // Загружаем данные асинхронно
        viewModelScope.launch (IO){
            // Для асинхронной загрузки использую .postValue
            items.postValue(jsoupData.getDataFromJsoup())

        }
        Log.d("Async",items.toString() )
        return items
    }

    fun fetchDetailInfo():MutableLiveData<MutableList<Information>>{
        viewModelScope.launch(IO){
            detailItem.postValue(jsoupData.getInformationData())
            Log.d("Async",detailItem.toString() )
        }

        return detailItem
    }



}