package com.ufla.superuflaanotaes
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    private var CHANNEL_ID: String = "Default 1"
    private var NAME: String = "Teste"
    private var DESCRIPTION = "Ahhhh"

    override fun onReceive(context: Context?, intent: Intent?){

        var msg: String? = intent?.getStringExtra("msg")
        var title: String? = intent?.getStringExtra("title")
        var nota_id: Long? = intent?.getLongExtra("id", -1)

        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        var builder: Notification.Builder;
        //checking if android version is greater than oreo(API 26) or not

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, NAME, importance).apply {
            description = DESCRIPTION
        }
        // Register the channel with the system

        notificationManager.createNotificationChannel(channel)

        builder = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notebook_leather_background)
            .setContentTitle(title)
            .setContentText(msg)
            .setContentIntent(pendingIntent)

        notificationManager.notify(nota_id!!.toInt(), builder.build())
    }
}