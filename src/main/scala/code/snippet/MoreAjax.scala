package code.snippet;

import scala.xml.{NodeSeq, Text}
import net.liftweb.util.Helpers._
import net.liftweb.http.{SHtml, SessionVar}
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.http.js.jquery.JqJsCmds.{Show, Hide}

/*
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 1/30/11
 * Time: 9:36 AM
 */

case class Book(reference: String, var title: String)

class MoreAjax {
  object stock extends SessionVar[List[Book]]( List(
    Book("ABCD", "Harry Potter and the Deathly Hallows"),
    Book("EFGH", "Harry Potter and the Goblet of Fire"),
    Book("IJKL", "Scala in Depth"),
    Book("MNOP", "Lift in Action")
  ))

  private val editFormDiv = "edit_display"

  private def bookMarkup(b: Book) =
    ".name" #> b.title &
    ".line [id]" #> b.reference &
    ".edit *" #> ((ns: NodeSeq) => SHtml.a(
      () =>
      SetHtml(editFormDiv, SHtml.ajaxForm(edit(b)(ns), Hide(editFormDiv, 1 seconds))) & Show(editFormDiv, 1 seconds), Text("Edit")
    )

    )

  def render = ".line" #> stock.is.map(bookMarkup)

  def edit(b: Book) =
    "#book_name" #> SHtml.text(b.title, b.title = _) &
    "type=submit" #> SHtml.ajaxSubmit("Update", () => SetHtml(b.reference, Text(b.title)))
}