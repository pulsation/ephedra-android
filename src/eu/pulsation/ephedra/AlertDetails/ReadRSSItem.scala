package eu.pulsation.ephedra

import scala.collection.mutable.Publisher

/**
 * Some RSS items have been viewed in the list screen.
 */
case class RSSItemReadEvent (rssItem: RSSItem)

class ReadRSSItem extends Publisher[RSSItemReadEvent] {
  
  def update(rssItem: RSSItem) {
    this.publish(RSSItemReadEvent(rssItem))
  }
}

