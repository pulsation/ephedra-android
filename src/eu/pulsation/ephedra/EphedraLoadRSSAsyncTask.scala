package eu.pulsation.ephedra

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import scala.collection.JavaConversions._

class EphedraLoadRSSAsyncTask(callback : (EphedraRSSFeed) => Unit) extends AsyncTask[String, Unit, EphedraRSSFeed] {
  final private val TAG = "EphedraLoadRSSAsyncTask"

  override protected def doInBackground(url: String*) : EphedraRSSFeed = {
    Log.v(TAG, "In doInBackground")
    new EphedraRSSFeed(url(0))
  }

  override def onPostExecute(result : EphedraRSSFeed) {
    super.onPostExecute(result)
    callback(result)
  }
}
