package com.example.longarifmetic

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CalculatingService : IntentService("CalculatingService") {

    override fun onHandleIntent(intent: Intent?) {
            val result = Calculating().calculate()

        if(intent!=null){
            val intentResult = Intent()
            intentResult.putExtra(RESULT,result)

            val pendingIntent = intent.getParcelableExtra<PendingIntent>(PENDING_INTENT_PARAM)
            pendingIntent?.send(this,RESULT_OK,intentResult)

            val pendingIntentToNotification = intent.getParcelableExtra<PendingIntent>(PENDING_INTENT_NOTIFICATION_PARAM)

            val builder = NotificationCompat.Builder(this,MainActivity.CHANNEL_ID).
                    setSmallIcon(R.mipmap.ic_launcher).
                    setContentTitle(getString(R.string.notification_title)).
                    setContentText(result.toString()).
                    setPriority(NotificationCompat.PRIORITY_DEFAULT).
                    setContentIntent(pendingIntentToNotification).
                    setAutoCancel(true)

            val notification = builder.build()
            NotificationManagerCompat.from(this).notify(++ID, notification)
            //notifications


        }


    }

    companion object {
        val PENDING_INTENT_NOTIFICATION_PARAM = "PENDING_INTENT_NOTIFICATION_PARAM"
        val PENDING_INTENT_PARAM = "PENDING_INTENT"
        val RESULT = "RESULT"
        val RESULT_OK = 1

        var ID=1
    }


}
