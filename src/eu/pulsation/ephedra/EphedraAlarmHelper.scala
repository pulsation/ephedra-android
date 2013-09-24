package eu.pulsation.ephedra

import java.util.{Date}
import android.content.{Context, BroadcastReceiver} 
import android.app.{AlarmManager, PendingIntent} 
import android.content.{Intent, IntentFilter}

class EphedraAlarmHelper(context: Context) {

    private lazy val alarmManager : AlarmManager = {
    context.getSystemService(Context.ALARM_SERVICE) match {
      case am: AlarmManager => am
      case _ => throw new ClassCastException
    }
  }

    
  def startAlarm() {

    val am:AlarmManager = this.alarmManager

    val ephedraBroadcastReceiver:BroadcastReceiver = new EphedraAlarmReceiver()

    context.registerReceiver (ephedraBroadcastReceiver, new IntentFilter ("ephadraAlarm.getNotifications"))

    val receiverIntent:Intent = new Intent()
    receiverIntent.setAction("ephedraAlarm.getNotifications")

    val scheduledReceiver:PendingIntent = PendingIntent.getBroadcast(this.context, 0, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT)

    am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() , 1000 * 30, scheduledReceiver)

  }

  def stopAlarm() = {

    val intent : Intent = new Intent(context, classOf[EphedraAlarmReceiver])
    intent.setAction("my.alarm.action")

    val pi : PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    val am : AlarmManager = this.alarmManager

    am.cancel(pi)
  }


}
