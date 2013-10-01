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

class AlertListFragment extends ListFragment {

  final private val TAG="eu.pulsation.ephedra.AlertListFragment"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  implicit def toRunnable[F](f: => F): Runnable = new Runnable() { def run() = f }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.alert_list, container, false)
  }

  lazy val context = this.getActivity()
  lazy val selectedRSSItem = new SelectedRSSItem()

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val adapter = new RssItemsArrayAdapter(context, android.R.layout.simple_list_item_1)

    this.setListAdapter(adapter)
    
    val promise: Future[List[RSSItem]] = future {
      (new RSSFeed(this.getResources().getString(R.string.rss_feed))).items
    }

    promise onSuccess {
      case items => {
        context.runOnUiThread({
          adapter.addAll(items)
        })
      }
    }

    promise onFailure {
      case error => {
        Log.v(TAG, error.getMessage)
        Toast
          .makeText(context.getApplicationContext(), 
            this.getString(R.string.failed_loading_feed),
            Toast.LENGTH_LONG)
          .show()
      }
    }
    
    context match {
      case sub: EphedraMainActivity => selectedRSSItem.subscribe(sub)
      case _ => Log.v(TAG, "Could not subscribe to main activity")
    }
  }

  override def onListItemClick(lv: ListView, v: View, position: Int, id: Long) {
    val rssItem : RSSItem = lv.getItemAtPosition(position) match {
      case item: RSSItem => item
      case _ => throw new ClassCastException
    }

    selectedRSSItem.update(rssItem)

    // FIXME - https://developer.android.com/training/basics/fragments/communicating.html
    /*
    val alertDetailsFragment = context.getFragmentManager().findFragmentById(R.id.alert_details_fragment)
    if (alertDetailsFragment == null) {
      Log.v(TAG, "Details fragment doesn't exist yet, instantiating it")
      val newFragment = new AlertDetailsFragment()
      val args = new Bundle()
      newFragment.setArguments(args)

      val transaction: FragmentTransaction = context.getFragmentManager().beginTransaction()
      transaction.replace(R.id.fragment_container, newFragment)
      transaction.addToBackStack(null)
      transaction.commit()
    } else {
      Log.v(TAG, "Details fragment already exists")
    }*/
  }
}
