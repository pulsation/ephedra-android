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

  def getRSSEntries(entriesKind : String)() : Set[String] = {
    sharedPreferences.getStringSet(entriesKind, Set[String]()).asScala.toSet
  }

  def addRSSEntry(entryKind: String)(rssEntry: String){
    editor.putStringSet(entryKind, getRSSEntries(entryKind) + rssEntry)
    editor.apply()
  }

  /**
   * Returns RSS entries that have already been viewed
   */
  lazy val viewedRSSEntries : Set[String] = {
    getRSSEntries(context.getResources().getString(R.string.viewed_rss_entries))
  }

  /**
   * Adds an RSS to the list of viewed entries
   */
  def addNotifiedRSSEntry(rssEntry : String) {
   addRSSEntry(context.getResources().getString(R.string.viewed_rss_entries))(rssEntry)
  }

  /**
   * Returns RSS entries that have already been read
   */
  lazy val readRSSEntries : Set[String] = {
    getRSSEntries(context.getResources().getString(R.string.read_rss_entries))
  }

  /**
   * Adds an RSS to the list of read entries
   */
  def addReadRSSEntry(rssEntry : String) {
   addRSSEntry(context.getResources().getString(R.string.read_rss_entries))(rssEntry)
  }
}
