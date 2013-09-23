package eu.pulsation.ephedra

import android.content.{BroadcastReceiver, Context, Intent}
import android.util.Log

/**
* The alarm receiver is triggered when a scheduled alarm is fired. This class
* reads the information in the intent and displays this information in the
* Android notification bar. The notification uses the default notification
* sound and it vibrates the phone.
*
*/
class EphedraAlarmReceiver extends BroadcastReceiver 
{

  private final val TAG = "EphedraAlarmReceiver"

  override def onReceive(context: Context, intent: Intent) {

    lazy val preferences = new EphedraPreferences(context)

    val rssFeed = new EphedraRSSFeed(context.getResources().getString(R.string.rss_feed))
    val unreadItems = rssFeed.items.map(item => {
      if (!preferences.readRSSEntries.contains(item.guid)) {
        item
      }
    })

    if (BuildConfig.DEBUG) {
      Log.v(TAG, "Unread notifications:")
      unreadItems.foreach(item => Log.v(TAG, item.toString()))
    }
  }
}
