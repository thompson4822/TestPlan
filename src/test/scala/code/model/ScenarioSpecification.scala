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

class ScenarioSpecification extends FeatureSpec with GivenWhenThen with MockitoSugar
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

  feature("Scenarios for a Feature can be defined and manipulated") {
    info("As a User")
    info("I want to be able to define and manipulate Scenarios")
    info("So that the Feature can be tested.")

    scenario("should allow Users with sufficient access to view Scenarios") {
      given("one or more Scenarios exists for a given Feature")
      and("the Feature is part of a Project that has a User with sufficient permissions")
      when("the User asks for Scenario details")
      then("they are provided.")
      pending
    }

    scenario("should not allow users without sufficient access to view Scenarios") {
      given("one or more Scenarios exists for a given Feature")
      and("the Feature is part of a Project that has a User without sufficient permissions")
      when("the User asks for Scenario details")
      then("they get nothing.")
      pending
    }

    scenario("should allow Users with sufficient permissions to add Scenarios") {
      given("a Project exists with a User that is qualified to add Scenarios")
      and("at least one Feature has been defined for that Project")
      when("the User adds a Scenario to the Feature")
      then("the Project will reflect that the Scenario has been added.")
      pending
    }

    scenario("should not allow Users without sufficient permissions to add Scenarios") {
      given("a Project exists with a User that is not qualified to add Scenarios")
      and("at least one Feature has been defined for that Project")
      when("the User adds a Scenario to the Feature")
      then("the request will be ignored.")
      pending
    }

    scenario("should allow Users with sufficient permissions to delete Scenarios") {
      given("a Project exists with a User that is qualified to delete Scenarios")
      and("at least one Feature has been defined for that Project")
      when("the User deletes a Scenario to the Feature")
      then("the Project will reflect that the Scenario has been removed.")
      pending
    }

    scenario("should not allow Users without sufficient permissions to delete Scenarios") {
      given("a Project exists with a User that is not qualified to delete Scenarios")
      and("at least one Feature has been defined for that Project")
      when("the User deletes a Scenario to the Feature")
      then("the request will be ignored.")
      pending
    }

  }

}