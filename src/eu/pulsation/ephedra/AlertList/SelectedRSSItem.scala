package eu.pulsation.ephedra

import scala.collection.mutable.Publisher

/**
 * An RSS item has been selected in the list screen.
 */
case class RSSItemSelectedEvent (rssItem: RSSItem)

class SelectedRSSItem extends Publisher[RSSItemSelectedEvent] {

  def update(rssItem: RSSItem) {
    this.publish(RSSItemSelectedEvent(rssItem))
  }
}
