package eu.pulsation.ephedra

import java.util.{Date}
import android.content.{Context, BroadcastReceiver} 
import android.app.{AlarmManager, PendingIntent} 
import android.content.{Intent, IntentFilter}
import android.util.Log

class AlarmHelper(context: Context) {

  private final val TAG = "eu.pulsation.ephedra.AlarmHelper"

  private lazy val alarmManager : AlarmManager = {
    context.getSystemService(Context.ALARM_SERVICE) match {
      case am: AlarmManager => am
      case _ => throw new ClassCastException
    }
  }

  def startAlarm() {

    Log.v(TAG, "Entered startAlarm()")

    val am:AlarmManager = this.alarmManager

    val ephedraBroadcastReceiver:BroadcastReceiver = new EphedraAlarmReceiver()

    context.registerReceiver (ephedraBroadcastReceiver, new IntentFilter ("ephedraAlarm.getNotifications"))

    val receiverIntent:Intent = new Intent()
    receiverIntent.setAction("ephedraAlarm.getNotifications")

    val scheduledReceiver:PendingIntent = PendingIntent.getBroadcast(this.context, 0, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT)

    am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() , 1000 * 120, scheduledReceiver)

  }

  def stopAlarm() = {

    val intent : Intent = new Intent(context, classOf[EphedraAlarmReceiver])
    intent.setAction("my.alarm.action")

    val pi : PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    val am : AlarmManager = this.alarmManager

    am.cancel(pi)
  }


}
