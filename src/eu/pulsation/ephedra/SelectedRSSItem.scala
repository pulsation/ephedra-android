package eu.pulsation.ephedra

import scala.collection.mutable.{Publisher, Subscriber}

case class RSSItemSelectedEvent (rssItem: RSSItem)

class SelectedRSSItem extends Publisher[RSSItemSelectedEvent] {

  def update(rssItem: RSSItem) {
    this.publish(RSSItemSelectedEvent(rssItem))
  }
}

