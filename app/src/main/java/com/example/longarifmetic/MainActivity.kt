package com.example.longarifmetic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Реализуйте программу вычисления 1!! + 2!! + 3!! + . . . + n!! со всеми
//десятичными знаками, где n ∈ [1 . . . 10000]


class CalculatingCoroutineAndActivity private constructor(private var activity:MainActivity?,
                                                          private var calculating: Calculating
                                                            ){


    var result: Int? = null
    fun start(){
        CoroutineScope(Dispatchers.Main).launch {
            var j:Int? = null
            withContext(Dispatchers.Default){
                j = calculating.calculate()
            }
            if(j!=null) {
               // activity?.viewResult(j!!)

            }
            result = j
        }
    }
    fun cleanActivity(){
        activity=null
    }

    fun setActivity(mainActivity: MainActivity) {
        activity = mainActivity
    }

    companion object{
        private var calculatingCoroutineAndActivity: CalculatingCoroutineAndActivity? = null
        fun getInstance(activity: MainActivity, calculating: Calculating):CalculatingCoroutineAndActivity{
            if(calculatingCoroutineAndActivity==null) {
                calculatingCoroutineAndActivity = CalculatingCoroutineAndActivity(activity,calculating)
            }
            else calculatingCoroutineAndActivity!!.activity=activity
            return calculatingCoroutineAndActivity!!
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,getString(R.string.calculator),
                NotificationManager.IMPORTANCE_DEFAULT)

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }

        findViewById<Button>(R.id.load).setOnClickListener{

            //запуск сервиса
            val intent = Intent(this, CalculatingService::class.java)
            val intentNull = Intent()
            //PendingIntent
            val pendingIntent = createPendingResult(REQUEST_CODE_CALCULATING,intentNull,0)

            val intentActivity = Intent(this,MainActivity::class.java)

            val pendingIntentToNotification =
                PendingIntent.getActivity(this, 0 ,intentActivity, 0)


            intent.putExtra(CalculatingService.PENDING_INTENT_PARAM,pendingIntent)
            intent.putExtra(CalculatingService.PENDING_INTENT_NOTIFICATION_PARAM,pendingIntentToNotification)


            startService(intent)

            //calculating.start()
        }
    }

    companion object{

        val REQUEST_CODE_CALCULATING = 1
        val CHANNEL_ID = "TEST"
    }
//    fun viewResult(j: Int) {
//            findViewById<TextView>(R.id.result).text = j.toString()
//    }

   // val calculating = CalculatingCoroutineAndActivity.getInstance(this,Calculating())

//    override fun onResume() {
//        super.onResume()
//        calculating.setActivity(this)
//        val result = calculating.result
//        if(result!=null) viewResult(result)
//
//    }
//
//    override fun onPause() {
//        super.onPause()
//        calculating.cleanActivity()
//    }
}