/**
 *  Ephedra Food Alerts
 *  Copyright (C) 2013-2014 Philippe Sam-Long aka pulsation
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.pulsation.ephedra

import java.util.{Date}
import android.content.Context
import android.app.{AlarmManager, PendingIntent} 
import android.content.Intent
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

    val receiverIntent:Intent = new Intent()
    receiverIntent.setAction("ephedraAlarm.getNotifications")

    val scheduledReceiver:PendingIntent = PendingIntent.getBroadcast(this.context, 0, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT)

    am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() , 1000 * context.getResources().getInteger(R.integer.notifications_frequency), scheduledReceiver)

  }

  def stopAlarm() = {

    val intent : Intent = new Intent(context, classOf[EphedraAlarmReceiver])
    intent.setAction("my.alarm.action")

    val pi : PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    val am : AlarmManager = this.alarmManager

    am.cancel(pi)
  }


}
