package kz.nurkaydarov097.airpvl.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kz.nurkaydarov097.airpvl.models.Popullant


class PopullantViewModel: ViewModel() {
    //val popullantsGetJSOUP = PopullantsGetJSOUP()
    val popullants:MutableLiveData<MutableList<Popullant>> = MutableLiveData()

    fun init(context: Context){
        if (popullants != null){
            return
        }
    }



    fun getData():MutableLiveData<MutableList<Popullant>>{
       viewModelScope.launch(IO){
          // popullants.postValue(popullantsGetJSOUP.getDataFromJsoup())
        }
        return popullants
    }

}