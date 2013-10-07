package eu.pulsation.ephedra

import scala.collection.mutable.{Publisher, Subscriber}

trait ReadRSSItemsSubscriber {
  self =>

  /**
   * Use composition for subscriber as using twice the same trait is not allowed
   */
  val readRSSItemsSubscriber = new Subscriber[RSSItemReadEvent, Publisher[RSSItemReadEvent]] {

    def notify(pub: Publisher[RSSItemReadEvent], event: RSSItemReadEvent) {
      self.notifyReadRSSItem(pub, event)
    }
  }

  def notifyReadRSSItem(pub: Publisher[RSSItemReadEvent], event: RSSItemReadEvent)

}

/**
 * Some RSS items have been viewed in the list screen.
 */
case class RSSItemReadEvent (rssItem: RSSItem)

class ReadRSSItems extends Publisher[RSSItemReadEvent] {
  
  def add(rssItem: RSSItem) {
    this.publish(RSSItemReadEvent(rssItem))
  }
}

