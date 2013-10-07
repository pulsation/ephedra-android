package eu.pulsation.ephedra

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable.{Subscriber, Publisher}

import android.content.Context
import android.util.Log

class Preferences(context: Context) extends ViewedRSSItemsSubscriber
                                    with ReadRSSItemSubscriber {

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
    editor.putStringSet(entryKind, getRSSEntries(entryKind) ++ rssEntries)
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
  def addViewedRSSEntries(rssEntries : Set[String]) {
   addRSSEntries(context.getResources().getString(R.string.viewed_rss_entries)) _
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
  def addReadRSSEntries(rssEntry : Set[String]) {
   addRSSEntries(context.getResources().getString(R.string.read_rss_entries)) _
  }

  /**
   * Some RSS items have been viewed. We must store which ones.
   */
  override def notifyViewedRSSItems(pub: Publisher[RSSItemsViewedEvent], event: RSSItemsViewedEvent) {
    addReadRSSEntries(event.rssItems.map(item => item.guid).toSet)
  }

  override def notifyReadRSSItem(pub: Publisher[RSSItemReadEvent], event: RSSItemReadEvent) {
    Log.v(TAG, "TODO: notifyReadItem")
  }
}
