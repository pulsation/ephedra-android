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

import android.app.{Notification, PendingIntent, NotificationManager}
import android.util.Log
import android.content.{Context, Intent}

class NotificationDisplayer (context: Context) {

  private final val TAG = "eu.pulsation.ephedra.NotificationDisplayer"

  lazy val notificationIntent : Intent = new Intent(context, classOf[EphedraMainActivity])
  lazy val contentIntent : PendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
  lazy val builder : Notification.Builder = new Notification.Builder(context)

  lazy val notificationManager : NotificationManager = {
    context.getSystemService(Context.NOTIFICATION_SERVICE) match {
      case nm: NotificationManager => nm
      case _ => throw new ClassCastException
    }
  }

  def displayRSSNotification(items: List[RSSItem]) {

    Log.v(TAG, "About to display notifications.")

    val notificationBuilder = builder
    .setSmallIcon(R.drawable.ic_launcher)
    .setContentIntent(contentIntent)
    .setAutoCancel(true)

    if (items.length > 1) {
      val inboxNotification = new Notification.InboxStyle(
        notificationBuilder.setContentTitle(
          items.length.toString() + " " + context.getResources().getString(R.string.new_alerts)
        )
      )
      items.foreach(item => inboxNotification.addLine(item.title))

      notificationManager.notify(R.drawable.ic_launcher, inboxNotification.build())
    } else if (items.length == 1) {
      notificationBuilder.setContentTitle(context.getResources().getString(R.string.new_alert))
      .setContentText(items.head.title)

      notificationManager.notify(R.drawable.ic_launcher, notificationBuilder.build())
    }
  }
}
