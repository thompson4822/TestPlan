package code {
package snippet {

import net.liftweb._
import http._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import code.lib.DependencyFactory

class HelloWorldTest extends FlatSpec with ShouldMatchers {
  val session = new LiftSession("", randomString(20), Empty)
  val stableTime = now

  // Use this to set up and tear down
  override def withFixture(test: NoArgTest) = S.initIfUninitted(session) {
      DependencyFactory.time.doWith(stableTime) { test() }
    }

  "HelloWorld Snippet" should "Put the time in the node" in {
    val hello = new HelloWorld
    Thread.sleep(1000) // make sure the time changes

    val str = hello.howdy(<span>Hello at
        <span id="time"/>
    </span>).toString

    str.indexOf(stableTime.toString) should be >= (0)
    str.indexOf("Hello at") should be >= (0)
  }
}

}}
