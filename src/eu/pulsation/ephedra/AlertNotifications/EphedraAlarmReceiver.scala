package eu.pulsation.ephedra

import android.content.{BroadcastReceiver, Context, Intent}
import android.util.Log
import scala.util.matching.Regex
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.language.implicitConversions

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

    lazy val rssStoredData = new RSSStoredData(context)

    val promise : Future[List[RSSItem]] = future {
      (new RSSFeed(context.getResources().getString(R.string.rss_feed))).items.filter(item => !rssStoredData.viewedRSSEntries.contains(item.guid))
    }

    promise onSuccess {
      case items => {
        val unviewedItems = items
        if (!unviewedItems.isEmpty) {
          new NotificationDisplayer(context).displayRSSNotification(unviewedItems)
        }
      }
    }
  }
}
