package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable.{Subscriber, Publisher}

import android.content.Context
import android.util.Log

class Preferences(context: Context) extends ViewedRSSItemsSubscriber
                                    with ReadRSSItemsSubscriber {

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
    Log.v(TAG, "Adding RSS entries. Kind: " + entryKind + "; entries: ")
    entries.foreach(Log.v(TAG, _))
    editor.putStringSet(entryKind, entries)
    Log.v(TAG, "Entries added.")
    editor.apply()
    Log.v(TAG, "Editor applied.")
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
  def addViewedRSSEntries(rssEntries : Set[String]) {
   addRSSEntries(context.getResources().getString(R.string.viewed_rss_entries))(rssEntries)
  }

  /**
   * Returns RSS entries that have already been read
   */
  lazy val readRSSEntries : Set[String] = {
    Log.v(TAG, "Read RSS entries:")
    val result = getRSSEntries(context.getResources().getString(R.string.read_rss_entries))
    result.foreach(Log.v(TAG, _))
    result
  }

  /**
   * Adds an RSS to the list of read entries
   */
  def addReadRSSEntries(rssEntries : Set[String]) {
    addRSSEntries(context.getResources().getString(R.string.read_rss_entries))(rssEntries)
    Log.v(TAG, "Added RSS entries to read list:")
    rssEntries.foreach(Log.v(TAG, _))
  }

  /**
   * Some RSS items have been viewed. We must store which ones.
   */
  override def notifyViewedRSSItems(pub: Publisher[RSSItemsViewedEvent], event: RSSItemsViewedEvent) {
    addViewedRSSEntries(event.rssItems.map(item => item.guid).toSet)
    Log.v(TAG, "Entries added to viewed RSS items")
  }

  override def notifyReadRSSItem(pub: Publisher[RSSItemReadEvent], event: RSSItemReadEvent) {
    addReadRSSEntries(Set(event.rssItem.guid))
    Log.v(TAG, "notifyReadRSSItem: " + event.rssItem.guid)
  }
}
