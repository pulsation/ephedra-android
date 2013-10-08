package eu.pulsation.ephedra

import scala.collection.JavaConversions._

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

