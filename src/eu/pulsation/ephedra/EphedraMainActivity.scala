package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.concurrent._
import scala.language.implicitConversions
import ExecutionContext.Implicits.global

import android.app.ListActivity
import android.os.Bundle
import android.view.{Menu, View}
import android.widget.{ArrayAdapter, Toast, ListView}
import android.util.Log

class EphedraMainActivity extends ListActivity 
{

  final private val TAG="EphedraMainActivity"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  implicit def toRunnable[F](f: => F): Runnable = new Runnable() { def run() = f }

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1)
    lazy val ephedraAlarmHelper = new EphedraAlarmHelper(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Launch periodic updates
    // ephedraAlarmHelper.startAlarm()

    this.setListAdapter(adapter)
    
    val promise: Future[List[EphedraRSSItem]] = future {
      (new EphedraRSSFeed(this.getResources().getString(R.string.rss_feed))).items
    }

    promise onSuccess {
      case items => {
        runOnUiThread({
          adapter.addAll(items.map(item => item.title))
        })
      }
    }

    promise onFailure {
      case error => {
        Log.v(TAG, error.getMessage)
        Toast
          .makeText(this.getApplicationContext(), 
            this.getString(R.string.failed_loading_feed),
            Toast.LENGTH_LONG)
          .show()
      }
    }
  }

  override def onListItemClick(lv: ListView, v: View, position: Int, id: Long) {
    Log.v(TAG, "onItemClick - position: " + position + "; id: " + id)
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }
}
