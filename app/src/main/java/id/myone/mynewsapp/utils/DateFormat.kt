package id.myone.mynewsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormat {
    fun formatDate(
        inputDateStr: String,
        inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        outputFormat: String = "dd/MM/yyyy",
    ): String {
        return try {
            val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
            val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())

            val date = inputFormatter.parse(inputDateStr)
            outputFormatter.format(date!!)

        } catch (e: Exception) {
            e.printStackTrace()
            inputDateStr
        }
    }
}