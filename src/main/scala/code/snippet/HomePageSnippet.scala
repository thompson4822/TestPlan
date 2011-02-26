package code.snippet

import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.java.util.Date
import code.lib._
import Helpers._
import code.domain.PermissionManager

class HomePageSnippet {

  lazy val permissionManager: PermissionManager = DependencyFactory.permissionManager.vend

  def render = {
    ".name" #> "Steve"
  }
}