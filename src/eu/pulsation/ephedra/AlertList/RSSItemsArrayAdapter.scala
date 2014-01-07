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

import android.widget.{ArrayAdapter,TextView}
import android.view.{View, ViewGroup}
import android.content.Context
import android.text.Html
import android.util.Log

class RSSItemsArrayAdapter(context: Context, itemViewResourceId: Int, rssStoredData: RSSStoredData)  
  extends ArrayAdapter[RSSItem](context, itemViewResourceId) {

  final private val TAG="eu.pulsation.ephedra.RSSItemsArrayAdapter"

  override def getView(position: Int, convertView: View, parent: ViewGroup) : View = {
    val view = {
      if (convertView == null) {
        View.inflate(context, itemViewResourceId, null)
      } else {
        convertView
      }
    }

    lazy val textView : TextView = view match {
      case tv: TextView => tv
      case _ => throw new ClassCastException
    }

    lazy val readRSSEntries = rssStoredData.readRSSEntries

    val rssItem = getItem(position)

    if (rssItem != null) {
      val displayedText = {
        if (readRSSEntries.exists(rssItem.guid == _)) {
          rssItem.title
        } else {
          "<b>" + rssItem.title + "</b>"
        }
      }
      textView.setText(Html.fromHtml(displayedText))
    }
    view
  }
}

