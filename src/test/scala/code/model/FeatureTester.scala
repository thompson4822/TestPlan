package code.model

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import code.lib.DependencyFactory
import org.mockito.Mockito._
import org.mockito.BDDMockito.{given => givenThat}
import org.junit.Assert._
import net.liftweb.common.Empty
import net.liftweb.http.{LiftSession, S}
import net.liftweb.util.StringHelpers._
import org.scalatest.{GivenWhenThen, BeforeAndAfterEach, FeatureSpec}

class FeatureTester extends FeatureSpec with GivenWhenThen with MockitoSugar
with BeforeAndAfterEach with ShouldMatchers {

  val session = new LiftSession("", randomString(20), Empty)

  override def withFixture(test: NoArgTest) = {
    // Do any initialization here
    try {
      // Intialize session state if it hasn't been already
      S.initIfUninitted(session) {
        test()
      }
    } finally {
      // Do any cleanup here
    }
  }


}