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

class FeatureSpecification extends FeatureSpec with GivenWhenThen with MockitoSugar
with BeforeAndAfterEach with ShouldMatchers with TestData {

  val session = new LiftSession("", randomString(20), Empty)

  override def withFixture(test: NoArgTest) = {
    InMemoryDB.init
    initTestData
    try {
      S.initIfUninitted(session) { test() }
    } finally {
      // Do any cleanup here
    }
  }

  feature("Features for a Project can be defined and manipulated") {
    info("As a User")
    info("I want to be able to define and manipulate Features")
    info("So that the Project can be provided a Feature set.")

    scenario("should allow Users with sufficient access to view features") {
      given("one or more Features exists for a given Project")
      and("the Project has a User with sufficient permissions")
      when("the User asks for Feature details")
      then("they are provided.")
      pending
    }

    scenario("should not allow users without sufficient access to view Features") {
      given("one or more Features exists for a given Project")
      when("a User who does not have permission asks for Feature details")
      then("they get nothing.")
      pending
    }

    scenario("should allow Users with sufficient permissions to add Features") {
      given("a Project exists with a User that is qualified to add Features")
      when("the User adds a Feature")
      then("the Project will reflect that the Feature has been added.")
      pending
    }

    scenario("should not allow Users without sufficient permissions to add Features") {
      given("a Project exists with a User that is not qualified to add Features")
      when("the User tries to add a Feature")
      then("the request will be ignored.")
      pending
    }

    scenario("should allow Users with sufficient permissions to delete a Feature") {
      given("a Project exists with a User that is qualified to delete Features")
      when("the User deletes a Feature")
      then("the Project will reflect that the Feature has been removed.")
      pending
    }

    scenario("should not allow Users without sufficient permissions to delete Features") {
      given("a Project exists with a User that is not qualified to delete Features")
      when("the User tries to delete a Feature")
      then("the request will be ignored.")
      pending
    }

  }

}

