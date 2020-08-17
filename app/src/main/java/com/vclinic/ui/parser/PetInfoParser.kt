package com.vclinic.ui.parser

import androidx.lifecycle.LiveData
import com.vclinic.ui.constant.ApplicationConstant
import com.vclinic.ui.model.Pet
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

object PetInfoParser {
    fun parseSucessResponse(response: String?): ArrayList<Pet>? {
        var petList: MutableList<Pet>? = null
        try {
            val obj = JSONObject(response)
            var jsonArray: JSONArray
            jsonArray = obj.getJSONArray(ApplicationConstant.TAG_PETS)
            petList = ArrayList()
            val jsonLength: Int = jsonArray.length()
            for (i in 0 until jsonLength) {
                var jsonObject= jsonArray.getJSONObject(i)
                try {
                    petList?.add(Pet(jsonObject.getString(ApplicationConstant.TAG_TITLE),
                        jsonObject.getString(ApplicationConstant.TAG_IMAGE_URL),
                        jsonObject.getString(ApplicationConstant.TAG_CONTENT_URL),
                        jsonObject.getString(ApplicationConstant.TAG_DATE_ADDED)))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return petList as ArrayList<Pet>?
    }
}