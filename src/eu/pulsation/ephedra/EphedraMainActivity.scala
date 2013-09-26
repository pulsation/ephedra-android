package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.concurrent._
import ExecutionContext.Implicits.global

import android.app.ListActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.util.Log

class EphedraMainActivity extends ListActivity 
{

  final private val TAG="EphedraMainActivity"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  implicit def toRunnable[F](f: => F): Runnable = new Runnable() { def run() = f }

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle)
  {
    val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1/*, new Array()rssTitles*/)
    lazy val ephedraAlarmHelper = new EphedraAlarmHelper(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Launch periodic updates
    // ephedraAlarmHelper.startAlarm()

    this.setListAdapter(adapter)
    
    val promise: Future[EphedraRSSFeed] = future {
      new EphedraRSSFeed(this.getResources().getString(R.string.rss_feed))
    }

    promise onSuccess {
      case rssFeed => {
        runOnUiThread({
          adapter.addAll(rssFeed.items.map(item => item.title))
        })
      }
    }

    promise onFailure {
      case error => { 
        // TODO: toast on error
        Log.v(TAG, error.getMessage)
      }
    }
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }
}
