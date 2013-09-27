package eu.pulsation.ephedra

import scala.collection.JavaConversions._

import android.widget.{ArrayAdapter,TextView}
import android.view.{View, ViewGroup}
import android.content.Context

class RssItemsArrayAdapter(context: Context, itemViewResourceId: Int)  
  extends ArrayAdapter[RSSItem](context, itemViewResourceId) {

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

    val rssItem = getItem(position)

    if (rssItem != null) {
      textView.setText(rssItem.title)
    }
    view
  }
}

