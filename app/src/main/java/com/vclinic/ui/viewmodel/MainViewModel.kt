package com.vclinic.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.vclinic.ui.model.Pet
import com.vclinic.ui.model.Settings
import com.vclinic.ui.network.NetworkCommunication
import java.util.*

class MainViewModel (application: Application) : AndroidViewModel(application){
    var app = application
    private val networkCommunication:NetworkCommunication = NetworkCommunication(app);
    var settings: LiveData<Settings> = networkCommunication.requestSettings()
    var pet: LiveData<ArrayList<Pet>> = networkCommunication.getPetList()
    var loadingProgressBar : LiveData<Boolean> = networkCommunication.progress
    var error : LiveData<String> = networkCommunication.errorResponse

    /**
     * Method to check Week end
     */
    private fun isWeekEnd():Boolean{
        var result=false
        try {
            val cal = Calendar.getInstance()
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
            )
                result = true
        }catch (e:Exception){
            e.printStackTrace()
        }
        return result
    }

    /**
     * Method to check given time is in office work or not
     */
    private fun isInTime(timeStr:String?):Boolean{
        var result=false
        try {
            val cal = Calendar.getInstance()
            val cHour = cal.get(Calendar.HOUR_OF_DAY)
            val cMinutes = cal.get(Calendar.MINUTE)
            val currentTime = 60 * cHour + cMinutes
            val inputSplit=timeStr?.split( " ")
            inputSplit?.let { val sTime = it[1]
                val eTime = it[3]
                val sSplit = sTime.split(":")
                val eSplit = eTime.split(":")
                val convertedStartTime = (60 * sSplit[0].toInt()) + (sSplit[1].toInt())
                val convertedEndTime = (60 * eSplit[0].toInt()) + (eSplit[1].toInt())
                if (currentTime in convertedStartTime..convertedEndTime)
                    result = true
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
        return result
    }

    /**
     * Method to check weekend and and hours
     */
    fun isOfficeHours(): Boolean {
        if(isInTime(settings.value?.workHours) && !isWeekEnd()){
            return true
        }
        return false
    }
}