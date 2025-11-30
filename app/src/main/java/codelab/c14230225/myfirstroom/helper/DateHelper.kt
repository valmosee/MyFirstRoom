package codelab.c14230225.myfirstroom.helper

import android.icu.util.ULocale
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun getCurrentDate() : String{
        val dateFormat = SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss", Locale.getDefault()
        )
        val date = Date()
        return dateFormat.format(date)
    }
}