package eu.pulsation.ephedra

import android.content.{BroadcastReceiver, Intent, Context}

class EphedraBootReceiver extends BroadcastReceiver {
  override def onReceive(context: Context, intent: Intent) {
    val ephedraAlarmHelper = new AlarmHelper(context)
    ephedraAlarmHelper.startAlarm()
  }
}
