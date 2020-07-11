package com.ufla.superuflaanotaes.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ufla.superuflaanotaes.NotificationReceiver
import com.ufla.superuflaanotaes.enums.Periodicidade
import com.ufla.superuflaanotaes.models.Nota
import java.time.LocalDateTime
import java.util.*

object Utils {

    fun createAlarm(context: Context, title: String, msg: String, nota: Nota){
        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(context, NotificationReceiver::class.java)

        broadcastIntent.putExtra("title", title)
        broadcastIntent.putExtra("msg", msg)
        broadcastIntent.putExtra("id", nota.id)

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            nota.id!!.toInt(),
            broadcastIntent,
            Intent.FILL_IN_DATA
        )
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }

        var periodicidade: Long = 0
        when(nota.periodicidade){
            Periodicidade.UNICA -> periodicidade = 0
            Periodicidade.HORARIO -> periodicidade = AlarmManager.INTERVAL_HOUR
            Periodicidade.DIARIO -> periodicidade = AlarmManager.INTERVAL_DAY
            Periodicidade.MENSAL -> periodicidade = AlarmManager.INTERVAL_DAY * 30
            Periodicidade.ANUAL -> periodicidade = AlarmManager.INTERVAL_DAY * 365
        }

        var alertDate: LocalDateTime = LocalDateTime.of(nota.dataAlerta, nota.hora)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, alertDate.year)
        calendar.set(Calendar.DAY_OF_MONTH, alertDate.dayOfMonth)
        calendar.set(Calendar.MONTH, alertDate.month.ordinal)
        Log.d("teste", alertDate.toString())
        calendar.set(Calendar.HOUR_OF_DAY, alertDate.hour)
        calendar.set(Calendar.MINUTE, alertDate.minute)
        calendar.set(Calendar.SECOND, 0)

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}