package eu.pulsation.ephedra
 
import scala.xml._

class EphedraRSSFeed(val url: String) {

  val namespaces = new Object {
    final val DUBLIN_CORE = "http://purl.org/dc/elements/1.1/"
    final val RSS = "http://web.resource.org/rss/1.0/modules/content/"
  }

  lazy val items: List[EphedraRSSItem] = {
    try {
      val root = XML.load(url)
      (root \\ "item").map(buildItem(_)).toList
    } catch {
      // Couldn't load RSS feed.
      case ioe: java.io.IOException => Nil
    }
  }

  def buildItem(node: Node): EphedraRSSItem = {
    new EphedraRSSItem(this,
      (node \\ "title").text,
      (node \\ "guid").text,
      (node \\ "description").text,
      ((node \\ "encoded") filter (n => n.namespace == this.namespaces.RSS):NodeSeq).text,
      ((node \\ "date") filter (n => n.namespace == this.namespaces.DUBLIN_CORE):NodeSeq).text)
  }
}
 
class EphedraRSSItem(
  val parent: EphedraRSSFeed,
  val title: String,
  val guid: String,
  val description: String,
  val content: String,
  val date: String) {
     
  override def toString(): String = {
    val crlf = System.getProperty("line.separator")

    crlf + "Title : " + title +
    crlf + " Guid: " + guid + 
    crlf + " Date: " + date +
    crlf + content
  }
}
