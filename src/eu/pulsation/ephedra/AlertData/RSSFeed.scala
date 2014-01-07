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
 
import scala.xml._

import android.util.Log

class RSSFeed(val url: String) {

  final private val TAG = "eu.pulsation.ephedra.RSSFeed"

  val namespaces = new Object {
    final val DUBLIN_CORE = "http://purl.org/dc/elements/1.1/"
    final val RSS = "http://web.resource.org/rss/1.0/modules/content/"
    final val CONTENT = "http://purl.org/rss/1.0/modules/content/"
  }

  /**
   * TODO: Filter items according to user preferences
   */
  def filterItem(item : RSSItem) = {
//      ("ACTU ALERTE".r findFirstIn item.description) != ""
    true
  }

  lazy val items: List[RSSItem] = {
    try {
      val root = XML.load(url)
      val allItems = (root \\ "item").map(buildItem(_)).toList
      allItems.filter(this.filterItem)
    } catch {
      // Couldn't load RSS feed.
      case ioe: java.io.IOException => { 
        Log.v(TAG, "Couldn't load RSS feed: " + ioe)
        ioe.printStackTrace()
        Nil
      }
    }
  }

  def buildItem(node: Node): RSSItem = {
    new RSSItem(this,
      (node \\ "title").text,
      (node \\ "guid").text,
      (node \\ "description").text,
      ((node \\ "encoded") filter (n => n.namespace == this.namespaces.CONTENT):NodeSeq).text,
      ((node \\ "date") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text,
      ((node \\ "subject") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text,
      (node \\ "link").text)
  }
}
