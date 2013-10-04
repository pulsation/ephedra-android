package eu.pulsation.ephedra

import android.content.{BroadcastReceiver, Context, Intent}
import android.util.Log
import scala.util.matching.Regex

/**
* The alarm receiver is triggered when a scheduled alarm is fired. This class
* reads the information in the intent and displays this information in the
* Android notification bar. The notification uses the default notification
* sound and it vibrates the phone.
*
*/
class EphedraAlarmReceiver extends BroadcastReceiver {

  private final val TAG = "eu.pulsation.ephedra.EphedraAlarmReceiver"

  override def onReceive(context: Context, intent: Intent) {

    lazy val preferences = new Preferences(context)

    val rssFeed = new RSSFeed(context.getResources().getString(R.string.rss_feed))

    val unviewedItems : List[RSSItem] = rssFeed.items.filter(item => !preferences.viewedRSSEntries.contains(item.guid))

    if (BuildConfig.DEBUG) {
      Log.v(TAG, "About to build notification")
    }
    if (!unviewedItems.isEmpty) {
      new NotificationDisplayer(context).displayRSSNotification(unviewedItems)
      rssFeed.items.foreach(item => preferences.addNotifiedRSSEntry(item.guid))
    }
  }
}
