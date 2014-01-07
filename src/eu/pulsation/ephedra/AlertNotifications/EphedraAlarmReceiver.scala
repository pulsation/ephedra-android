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

import android.content.{BroadcastReceiver, Context, Intent}
import android.util.Log
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
      val rssFeed = new RSSFeed(context.getResources().getString(R.string.rss_feed))
      rssFeed.items.filter(item => !rssStoredData.viewedRSSEntries.contains(item.guid)) 
    }

    promise onSuccess {
      case unviewedItems => {
        if (!unviewedItems.isEmpty) {
          new NotificationDisplayer(context).displayRSSNotification(unviewedItems)
        }
      }
    }
  }
}
