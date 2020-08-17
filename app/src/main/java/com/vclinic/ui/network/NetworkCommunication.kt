package com.vclinic.ui.network

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vclinic.R
import com.vclinic.ui.constant.ApplicationConstant
import com.vclinic.ui.model.Pet
import com.vclinic.ui.model.Settings
import com.vclinic.ui.parser.PetInfoParser
import com.vclinic.ui.parser.SettingsParser
import kotlinx.android.synthetic.main.activity_petinfo.*
import okhttp3.*
import java.io.IOException

class NetworkCommunication(application: Application) {
    val app = application
    var progress = MutableLiveData<Boolean>()
    var errorResponse = MutableLiveData<String> ()

    /**
     * Method to call request and get response for config
     */
    fun requestSettings(): LiveData<Settings> {
        progress.value = true
        var client = OkHttpClient.Builder().build()
        val settings = MutableLiveData<Settings>()
        val request: Request = Request.Builder()
            .url(ApplicationConstant.SETTINGS)
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                lateinit var errorRes:String
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val parseResponse = response.body!!.string()
                        settings.postValue(SettingsParser.parseSucessResponse(parseResponse)
                        )
                        requestPetInfo();
                    } else {
                        errorRes = response.body?.string().toString()
                        errorResponse.postValue(app.getString(R.string.error_response)+response)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    errorResponse.postValue(app.getString(R.string.error_response)+e.printStackTrace())
                    progress.postValue(false)
                }
            })
        return settings
    }

    /**
     * Method to call request and get response of pet info
     */
    fun requestPetInfo(): LiveData<ArrayList<Pet>> {
        var client = OkHttpClient.Builder().build()
        val data = MutableLiveData<ArrayList<Pet>>()
        val request: Request = Request.Builder()
            .url(ApplicationConstant.PET_INFO)
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val parseResponse = response.body!!.string()
                        data.postValue(PetInfoParser.parseSucessResponse(parseResponse)!!)
                        progress.postValue(false);
                    }else{
                        progress.postValue(false);
                        errorResponse.postValue(app.getString(R.string.error_response)+response)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    errorResponse.postValue(app.getString(R.string.error_response)+e.printStackTrace())
                    progress.postValue(false)
                }
            })
        return data
    }

    /**
     * Method to get pet list
     */
    fun getPetList():LiveData<ArrayList<Pet>> {
        return requestPetInfo()
    }
}