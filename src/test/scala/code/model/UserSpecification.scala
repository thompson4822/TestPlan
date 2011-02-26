package code.model

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}
import net.liftweb.http.{LiftSession, S}
import net.liftweb.common.Empty
import net.liftweb.util.Helpers._

class UserSpecification extends FeatureSpec with GivenWhenThen with ShouldMatchers {

  val session = new LiftSession("", randomString(20), Empty)

  override def withFixture(test: NoArgTest) {
    InMemoryDB.init
    try {
      S.initIfUninitted(session) { test() }
    }
    finally { }
  }

  feature("New users can be added") {
    info("As a System")
    info("I want to be able to add users to the database")
    info("So that they can be associated with Projects")

    scenario("should be able to retrieve saved user") {
      given("a user is saved to the database")
      User.create.email("here@there.com").firstName("Joe").lastName("Person").password("pa$$w0rd").superUser(true).save

      when("the database is asked to retrieve that user")
      val users = User.findAll

      then("the database comes back with that record")
      1 should equal (users.size)
      val user = users.head
      user.email.is should equal ("here@there.com")
      user.firstName.is should equal ("Joe")
      user.lastName.is should equal  ("Person")
      user.superUser.is should be (true)
    }

  }
}