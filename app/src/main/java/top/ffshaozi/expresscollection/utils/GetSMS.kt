package top.ffshaozi.expresscollection.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import java.util.*


object GetSMS {

    @SuppressLint("Range")
    fun getSMS(context: Context): List<SMSData> {
        val smsList = mutableListOf<SMSData>()
        val uri = Uri.parse("content://sms/inbox")
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        var intBox = 0
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val address = cursor.getString(cursor.getColumnIndex("address"))
                val body = cursor.getString(cursor.getColumnIndex("body"))
                val date = cursor.getLong(cursor.getColumnIndex("date"))
                smsList.add(SMSData(address, body, date))
                intBox++
            } while (cursor.moveToNext() && intBox<200)
            cursor.close()
        }

        return smsList
    }

    data class SMSData(val address: String?, val body: String?, val date: Long?)


}