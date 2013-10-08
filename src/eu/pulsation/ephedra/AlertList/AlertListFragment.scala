package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.concurrent._
import scala.language.implicitConversions
import ExecutionContext.Implicits.global

import android.app.{ListFragment}
import android.os.Bundle
import android.view.{Menu, View}
import android.widget.{Toast, ListView}
import android.util.Log

import android.view.LayoutInflater
import android.view.ViewGroup

class AlertListFragment(val preferences: Preferences) extends ListFragment {

  final private val TAG="eu.pulsation.ephedra.AlertListFragment"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  implicit def toRunnable[F](f: => F): Runnable = new Runnable() { def run() = f }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.alert_list, container, false)
  }

  lazy val activity = this.getActivity()

  // The RSS item that has been selected
  lazy val selectedRSSItem = new SelectedRSSItem()

  // The RSS items that have been displayed in the list, not to be notified next time
  lazy val viewedRSSItems = new ViewedRSSItems()

  override def onStart() {

    super.onStart()
  }

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    viewedRSSItems.subscribe(preferences.viewedRSSItemsSubscriber)

    val adapter = new RSSItemsArrayAdapter(activity, android.R.layout.simple_list_item_1, preferences)

    this.setListAdapter(adapter)
    
    val promise: Future[List[RSSItem]] = future {
      (new RSSFeed(this.getResources().getString(R.string.rss_feed))).items
    }

    promise onSuccess {
      case items => {
        activity.runOnUiThread({
          adapter.addAll(items)
        })
        viewedRSSItems.update(items)
      }
    }

    promise onFailure {
      case error => {
        Log.e(TAG, error.getMessage)
        Toast
          .makeText(activity.getApplicationContext(), 
            this.getString(R.string.failed_loading_feed),
            Toast.LENGTH_LONG)
          .show()
      }
    }
    
    activity match {
      case mainActivity: EphedraMainActivity => selectedRSSItem.subscribe(mainActivity)
      case _ => Log.e(TAG, "Could not subscribe to main activity")
    }
  }

  override def onListItemClick(lv: ListView, v: View, position: Int, id: Long) {
    val rssItem : RSSItem = lv.getItemAtPosition(position) match {
      case item: RSSItem => item
      case _ => throw new ClassCastException
    }

    selectedRSSItem.update(rssItem)
  }
}
