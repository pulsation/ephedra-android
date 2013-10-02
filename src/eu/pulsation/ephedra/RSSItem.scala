package eu.pulsation.ephedra
 
case class RSSItem(
  parent: RSSFeed,
  title: String,
  guid: String,
  description: String,
  content: String,
  date: String,
  subject : String,
  link: String) {
     
  override def toString(): String = { title }
}
