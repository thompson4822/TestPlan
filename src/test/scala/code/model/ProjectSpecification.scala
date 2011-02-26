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

class ProjectSpecification extends FeatureSpec with GivenWhenThen with MockitoSugar
with BeforeAndAfterEach with ShouldMatchers with TestData {

  val session = new LiftSession("", randomString(20), Empty)

  override def withFixture(test: NoArgTest) = {
    InMemoryDB.init
    initTestData
    try {
      // Intialize session state if it hasn't been already
      S.initIfUninitted(session) {
        test()
      }
    } finally {
      // Do any cleanup here
    }
  }

  feature("A Project can be created that has users and features") {
    info("As a User")
    info("I want to be able to establish a Project")
    info("So that I can create a Test Plan with it.")

    scenario("should allow a Project owner to be established") {
      given("a signed in user")
      joe.save

      when("the User creates a project")
      joe.createProject(segwayProject)

      then("the User is automatically made the Project's owner")
      val segwayProjectUsers = segwayProject.users
      segwayProjectUsers.length should equal (1)
      segwayProjectUsers(0) should equal (joe)
      ProjectUser.roleFor(joe, segwayProject) should equal (Role.Owner)
    }

    scenario("should not allow Users not associated with a Project to know it exists") {
      given("A Project exists")
      segwayProject.save

      and("the current User is not a member of the Project")
      joe.save

      when("the User asks for Projects")
      val projects = joe.projects

      then("the Project should not be included")
      projects.filter(_ == segwayProject).length should equal (0)
    }

    scenario("should allow Users to be added to the Project") {
      given("a project has been created")
      joe.save
      joe.createProject(segwayProject)

      and("the current system User is part of the Project and has an ownership Role")

      when("a User is added with a specific Role")
      jane.save
      segwayProject.addUser(jane, Role.Read)

      then("the Project should reflect that it includes that user")
      val users = segwayProject.users.all
      users.length should equal (2) // Joe and Jane
      users.filter(_ == jane).length should equal (1) // Make sure Jane is in the list

      and("the User should reflect that it is part of the Project in the Role capacity")
      ProjectUser.roleFor(jane, segwayProject) should equal (Role.Read)
    }

    scenario("should allow Users to be removed from a Project") {
      given("a project exists with at least one User other than the owner")
      joe.save
      jane.save
      joe.createProject(segwayProject)

      segwayProject.addUser(jane, Role.Read)

      and("the current system User is part of the Project and has an ownership Role")
      val systemUser = joe

      when("a User is removed from the Project")
      systemUser.removeFromProject(jane, segwayProject)

      then("the Project should no longer reflect that the User is associated with it")
      segwayProject.users.filter(_ == jane).length == 0
      segwayProject.users.length should equal (1)

      and("the removed User should no longer reflect an association to the Project")
      jane.projects.filter(_ == segwayProject).length == 0
    }

    scenario("should not allow Users to be removed from a Project if unauthorized") {
      given("a project exists with at least one User")
      joe.save
      jane.save
      joe.createProject(segwayProject)

      segwayProject.addUser(jane, Role.Read)

      and("the current system User is part of the Project but does not have an ownership Role")
      val systemUser = jane

      when("a User is removed from the Project")
      systemUser.removeFromProject(joe, segwayProject)

      then("the Project should ignore the request")
      segwayProject.users.length should equal (2)

      and("the User that would have been removed should still be associated with the Project")
      joe.projects.filter(_ == segwayProject).length should equal (1)
    }


  }

}