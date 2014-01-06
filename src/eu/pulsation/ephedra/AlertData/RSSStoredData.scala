package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import android.content.Context

class RSSStoredData(context: Context) {

  final val TAG : String = "eu.pulsation.ephedra.Preferences"

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

  def addRSSEntries(entryKind: String)(rssEntries: Set[String]){
    val entries = getRSSEntries(entryKind) ++ rssEntries
    editor.putStringSet(entryKind, entries)
    editor.apply()
  }

  /**
   * Returns RSS entries that have already been viewed
   */
  def viewedRSSEntries : Set[String] = {
    getRSSEntries(context.getResources().getString(R.string.viewed_rss_entries))
  }

  /**
   * Adds an RSS to the list of viewed entries
   */
  def addViewedRSSEntries(rssEntries : Set[String]) {
    addRSSEntries(context.getResources().getString(R.string.viewed_rss_entries))(rssEntries)
  }

  /**
   * Returns RSS entries that have already been read
   */
  def readRSSEntries : Set[String] = {
    getRSSEntries(context.getResources().getString(R.string.read_rss_entries))
  }

  /**
   * Adds an RSS to the list of read entries
   */
  def addReadRSSEntries(rssEntries : Set[String]) {
    addRSSEntries(context.getResources().getString(R.string.read_rss_entries))(rssEntries)
  }

  /**
   * Some RSS items have been viewed. We must store which ones.
   */
  def notifyViewedRSSItems(rssItems : List[RSSItem]) {
    addViewedRSSEntries(rssItems.map(item => item.guid).toSet)
  }


  /**
   * Some RSS items have been read. We must store which ones.
   */
  def notifyReadRSSItem(rssItem: RSSItem) {
    addReadRSSEntries(Set(rssItem.guid))
  }

}
