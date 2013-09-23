package eu.pulsation.ephedra

import android.app.{Notification, PendingIntent}
import android.util.Log
import android.content.{Context, Intent}

class EphedraNotificationDisplayer (context: Context) {

  private final val TAG = "EphedraNotificationDisplayer"

  /*
  lazy val builder : Notification.Builder = new Notification.Builder(context)
  lazy val notificationIntent : Intent = new Intent(context, classOf[EphedraMainActivity])
  lazy val contentIntent : PendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
*/

  def displayRSSNotification(items: List[EphedraRSSItem]) {

    val title = {
      if (items.length > 1) {
        items.length.toString() + " " + context.getResources().getString(R.string.new_alerts)
      } else {
        context.getResources().getString(R.string.new_alert)
      }
    }

    val content = {
      val crlf = System.getProperty("line.separator") 

      items.foldLeft("") {
        (acc, item) => acc + crlf + item
      }
    }
    if (BuildConfig.DEBUG) {
      Log.v(TAG, "Notification")
      Log.v(TAG, "Title: " + title)
      Log.v(TAG, "Content:")
      Log.v(TAG, content)
    }
/*
    builder
    .setContentTitle(title)
    .setContentText(contentText)
//      .setContentInfo("ContentInfo")
    .setTicker("You were asked to review documents")
    .setLights(0xFFFF0000, 500, 500) //setLights (int argb, int onMs, int offMs)
    .setContentIntent(contentIntent)
    .setAutoCancel(true)
    */
  }
}
