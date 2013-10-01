package eu.pulsation.ephedra
 
import scala.xml._

import android.util.Log

class RSSFeed(val url: String) {

  final private val TAG = "eu.pulsation.ephedra.RSSFeed"

  val namespaces = new Object {
    final val DUBLIN_CORE = "http://purl.org/dc/elements/1.1/"
    final val RSS = "http://web.resource.org/rss/1.0/modules/content/"
  }

  /**
   * TODO: Filter items according to user preferences
   */
  def filterItem(item : RSSItem) = {
//      ("ACTU ALERTE".r findFirstIn item.description) != ""
    true
  }

  lazy val items: List[RSSItem] = {
    try {
      val root = XML.load(url)
      val allItems = (root \\ "item").map(buildItem(_)).toList
      allItems.filter(this.filterItem)
    } catch {
      // Couldn't load RSS feed.
      case ioe: java.io.IOException => { 
        Log.v(TAG, "Couldn't load RSS feed: " + ioe)
        ioe.printStackTrace()
        Nil
      }
    }
  }

  def buildItem(node: Node): RSSItem = {
    new RSSItem(this,
      (node \\ "title").text,
      (node \\ "guid").text,
      (node \\ "description").text,
      ((node \\ "encoded") filter (n => n.namespace == this.namespaces.RSS):NodeSeq).text,
      ((node \\ "date") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text,
      ((node \\ "subject") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text)
  }
}
 
class RSSItem(
  val parent: RSSFeed,
  val title: String,
  val guid: String,
  val description: String,
  val content: String,
  val date: String,
  val subject : String) {
     
  override def toString(): String = { title }
}