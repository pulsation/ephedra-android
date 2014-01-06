package eu.pulsation.ephedra

// import scala.collection.mutable.{Publisher, Subscriber}
import rx.lang.scala.subjects._

trait ReadRSSItemsSubscriber {
  self =>

  /**
   * Use composition for subscriber as using twice the same trait is not allowed
   */

  /*
  val readRSSItemsSubscriber = new Subscriber[RSSItemReadEvent, Publisher[RSSItemReadEvent]] {

    def notify(pub: Publisher[RSSItemReadEvent], event: RSSItemReadEvent) {
      self.notifyReadRSSItem(pub, event)
    }
  }
  */

  val readRSSItems = PublishSubject[RSSItem]() // new ReadRSSItems()

  readRSSItems.subscribe(item => notifyReadRSSItem(item))

  def notifyReadRSSItem(/*pub: Publisher[RSSItemReadEvent], */readRSSItem: RSSItem)

}

/**
 * Some RSS items have been viewed in the list screen.
 */
case class RSSItemReadEvent (rssItem: RSSItem)
/*
class ReadRSSItems extends ReplaySubject/*[RSSItemReadEvent]*/ {

  def add(rssItem: RSSItem) {
    //this.publish(RSSItemReadEvent(rssItem))
    this.onNext(RSSItemReadEvent(rssItem).asInstanceOf)
  }
}
*/
