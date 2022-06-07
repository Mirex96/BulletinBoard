package com.example.bulletinboard.utils

import android.content.Context
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception

object CityHelper {
    fun getAllCountries(context: Context): ArrayList<String> {
        var tempArray = ArrayList<String>()
        try {

            val inputStream: InputStream = context.assets.open("countriesToCities.json")


            // Нужно для того, чтобы узнать размер нашего списка
            val size: Int = inputStream.available()
            val bytesArray = ByteArray(size)
            //теперь указаываем куда будут считываться данные массива
            inputStream.read(bytesArray)
            // Превращаем массив в объект String
            val jsonFile = String(bytesArray)
            val jsonObject = JSONObject(jsonFile)
            // Здесь мы получили названия стран по именам
            val countriesNames = jsonObject.names()

            // Запускаем цикл по массиву с именами, чтобы поместить его в спинер
            if (countriesNames != null) {
                for (i in 0 until countriesNames.length()) {
                    tempArray.add(countriesNames.getString(i))
                }
            }

        } catch (e: Exception) {

        }
        return tempArray
    }
}