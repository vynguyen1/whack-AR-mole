package com.example.mosy.objects

import org.json.JSONException
import org.json.JSONObject

object JsonObjectCreator {

    fun toJSON(pan: Int, tilt: Int): String {

        val jsonObject = JSONObject()
        try {
            //jsonObject.put("cmd", "move")
            jsonObject.put("Pan", pan)
            jsonObject.put("Tilt", tilt)

        } catch (e: JSONException) {
            e.printStackTrace()
            return ""
        }
        return jsonObject.toString()
    }
}