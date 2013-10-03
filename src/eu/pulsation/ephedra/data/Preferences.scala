package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import android.content.Context

class Preferences(context: Context) {

  /**
   * Returns shared preferences object
   */
  lazy val sharedPreferences = {
    context.getSharedPreferences(context.getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE)
  }

  lazy val editor = {
    sharedPreferences.edit()
  }

  /**
   * Returns RSS entries that have already been notified
   */
  lazy val notifiedRSSEntries : Set[String] = {
    sharedPreferences.getStringSet(context.getResources().getString(R.string.notified_rss_entries), Set[String]()).asScala.toSet
  }

  /**
   * Adds an RSS to the list of notified entries
   */
  def addNotifiedRSSEntry(rssEntry : String) {
    editor.putStringSet(context.getResources().getString(R.string.notified_rss_entries), this.notifiedRSSEntries + rssEntry)
    editor.apply()
  }

  /**
   * Returns RSS entries that have already been read
   */
  lazy val readRSSEntries : Set[String] = {
    sharedPreferences.getStringSet(context.getResources().getString(R.string.read_rss_entries), Set[String]()).asScala.toSet
  }

  /**
   * Adds an RSS to the list of read entries
   */
  def addReadRSSEntry(rssEntry : String) {
    editor.putStringSet(context.getResources().getString(R.string.read_rss_entries), this.readRSSEntries + rssEntry)
    editor.apply()
  }
}
