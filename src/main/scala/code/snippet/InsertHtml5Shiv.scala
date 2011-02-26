package code.snippet

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 1/1/11
 * Time: 8:36 AM
 * To change this template use File | Settings | File Templates.
 */

import scala.xml._
import _root_.net.liftweb.util._
import code.lib._
import Helpers._

object InsertHtml5Shiv {
  def render = "*" #> Unparsed(
    """<!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></ script>
    <![endif]-->
    """)
}