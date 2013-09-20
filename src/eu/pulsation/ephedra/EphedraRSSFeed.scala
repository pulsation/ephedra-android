package eu.pulsation.ephedra
 
import scala.xml._

class EphedraRSSFeed(val url: String) {
  lazy val items: List[EphedraRSSItem] = {
    val root = XML.load(url)
    (root \\ "item").map(buildItem(_)).toList
  }
  def buildItem(node: Node): EphedraRSSItem = {
    new EphedraRSSItem(this,
      (node \\ "title").text,
      (node \\ "guid").text,
      (node \\ "description").text,
      ((node \\ "encoded") filter (n => n.namespace == "http://purl.org/rss/1.0/modules/content/"):NodeSeq).text,
      ((node \\ "date") filter (n => n.namespace == "http://purl.org/dc/elements/1.1/"):NodeSeq).text)
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
/*   
  object Feed {
    def main(args: Array[String]) = {
      val feed = new Feed("http://agriculture.gouv.fr/spip.php?page=backend&id_rubrique=460")
        val feedList = feed.downloadItems
        feedList.foreach(println)
    }
  }
  */
