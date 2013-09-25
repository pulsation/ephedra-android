package eu.pulsation.ephedra
 
import scala.xml._

import android.util.Log

class EphedraRSSFeed(val url: String) {

  final private val TAG = "EphedraRSSFeed"

  val namespaces = new Object {
    final val DUBLIN_CORE = "http://purl.org/dc/elements/1.1/"
    final val RSS = "http://web.resource.org/rss/1.0/modules/content/"
  }

  /**
   * TODO: Filter items according to user preferences
   */
  def filterItem(item : EphedraRSSItem) = {
//      ("ACTU ALERTE".r findFirstIn item.description) != ""
    true
  }

  lazy val items: List[EphedraRSSItem] = {
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

  def buildItem(node: Node): EphedraRSSItem = {
    new EphedraRSSItem(this,
      (node \\ "title").text,
      (node \\ "guid").text,
      (node \\ "description").text,
      ((node \\ "encoded") filter (n => n.namespace == this.namespaces.RSS):NodeSeq).text,
      ((node \\ "date") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text,
      ((node \\ "subject") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text)
  }
}
 
class EphedraRSSItem(
  val parent: EphedraRSSFeed,
  val title: String,
  val guid: String,
  val description: String,
  val content: String,
  val date: String,
  val subject : String) {
     
  override def toString(): String = {
    val crlf = System.getProperty("line.separator")

    crlf + "Title : " + title +
    crlf + "Guid: " + guid + 
    crlf + "Date: " + date +
    crlf + "Subject: " + subject
    crlf + content
  }
}
