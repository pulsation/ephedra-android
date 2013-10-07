package eu.pulsation.ephedra

import scala.collection.mutable.Publisher

/**
 * Some RSS items have been viewed in the list screen.
 */
case class RSSItemsViewedEvent (rssItems: List[RSSItem])

class ViewedRSSItems extends Publisher[RSSItemsViewedEvent] {
  
  def update(rssItems: List[RSSItem]) {
    this.publish(RSSItemsViewedEvent(rssItems))
  }
}

