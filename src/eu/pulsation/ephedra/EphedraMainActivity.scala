package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.concurrent.ops._

import android.app.ListActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.util.Log

class EphedraMainActivity extends ListActivity 
{

  final private val TAG="EphedraMainActivity"
    
  lazy val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1/*, new Array()rssTitles*/)

  
   implicit def toRunnable[F](f: => F): Runnable =
new Runnable() { def run() = f }

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle)
  {
    lazy val ephedraAlarmHelper = new EphedraAlarmHelper(this)
/*
    lazy val rssItems = new EphedraRSSFeed(this.getResources().getString(R.string.rss_feed)).items
    lazy val rssTitles = rssItems.map(item => item.title).toArray
*/

    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    // Launch periodic updates
    // ephedraAlarmHelper.startAlarm()
    

    // Log.v(TAG, rssItems.length + " RSS items loaded")

    this.setListAdapter(adapter)

    /*
    def populateArray(rssFeed : EphedraRSSFeed) = {
      val items = rssFeed.items

      adapter.addAll(items.map(item => item.title));
    }
    */

    /*
    val task = new EphedraLoadRSSAsyncTask(populateArray)
    task.execute(this.getResources().getString(R.string.rss_feed))
    */

  }

  spawn {
    val rssFeed = new EphedraRSSFeed(this.getResources().getString(R.string.rss_feed))
    runOnUiThread {
      this.adapter.addAll(rssFeed.items.map(item => item.title))
    }
  }

  override def onCreateOptionsMenu(menu: Menu) : Boolean = {
    this.getMenuInflater().inflate(R.menu.menu, menu)
    true
  }
}
