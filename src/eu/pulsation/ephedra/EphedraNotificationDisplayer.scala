package eu.pulsation.ephedra

import android.app.{Notification, PendingIntent, NotificationManager}
import android.util.Log
import android.content.{Context, Intent}

class EphedraNotificationDisplayer (context: Context) {

  private final val TAG = "EphedraNotificationDisplayer"

  lazy val notificationIntent : Intent = new Intent(context, classOf[EphedraMainActivity])
  lazy val contentIntent : PendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
  lazy val builder : Notification.Builder = new Notification.Builder(context)

  lazy val notificationManager : NotificationManager = {
    context.getSystemService(Context.NOTIFICATION_SERVICE) match {
      case nm: NotificationManager => nm
      case _ => throw new ClassCastException
    }
  }

  def displayRSSNotification(items: List[EphedraRSSItem]) {
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
