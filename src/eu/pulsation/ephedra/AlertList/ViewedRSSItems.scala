package eu.pulsation.ephedra

import scala.collection.mutable.{Publisher, Subscriber}

trait ViewedRSSItemsSubscriber {

  self =>

  val viewedRSSItemsSubscriber = new Subscriber[RSSItemsViewedEvent, Publisher[RSSItemsViewedEvent]] {

    def notify(pub: Publisher[RSSItemsViewedEvent], event: RSSItemsViewedEvent) {
      self.notifyViewedRSSItems(pub, event)
    }
  }

  def notifyViewedRSSItems(pub: Publisher[RSSItemsViewedEvent], event: RSSItemsViewedEvent)

}

/**
 * Some RSS items have been viewed in the list screen.
 */
case class RSSItemsViewedEvent (rssItems: List[RSSItem])

class ViewedRSSItems extends Publisher[RSSItemsViewedEvent] {
  
  def update(rssItems: List[RSSItem]) {
    this.publish(RSSItemsViewedEvent(rssItems))
  }
}

