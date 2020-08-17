package com.vclinic.ui.parser

import com.vclinic.ui.constant.ApplicationConstant
import com.vclinic.ui.model.Settings
import org.json.JSONException
import org.json.JSONObject

object SettingsParser {
    fun parseSucessResponse(response: String?): Settings? {
        try {
            val jsonObject = JSONObject(response)
            val obj = jsonObject.getJSONObject(ApplicationConstant.TAG_SETTINGS)
            return Settings(obj.getBoolean(ApplicationConstant.TAG_IS_CALL_ENABLED),
                    obj.getBoolean(ApplicationConstant.TAG_IS_CHAT_ENABLED),
                    obj.getString(ApplicationConstant.TAG_WORK_HOURS),
                "Office Hours: "+obj.getString(ApplicationConstant.TAG_WORK_HOURS))

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}