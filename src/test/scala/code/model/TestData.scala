package code.model

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 12/20/10
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */

trait TestData {

  var segwayProject: Project = _

  var joe: User = _

  var jane: User = _

  var thrusterInstallationFeature: Feature = _

  var interferenceScenario: Scenario = _

  var basicThrustScenario: Scenario = _

  // TODO - Add more test data to this class!

  def initTestData = {
    segwayProject = Project.create.name("Segway Vertical Thrust Subsystem").description("Driver for the vertical thruster I'm retrofitting onto my Segway")
    joe = User.create.firstName("Joe").lastName("Person")
    jane = User.create.firstName("Jane").lastName("Person")
    thrusterInstallationFeature = Feature.create.name("Thruster installed correctly")
    interferenceScenario = Scenario.create.name("Gyro doesn't interfere physically with thruster")
    basicThrustScenario = Scenario.create.name("Basic thruster power lifts unit no more than an inch off the ground")

    //List[{def save:Boolean }](segwayProject, joe, jane, interferenceScenario, basicThrustScenario, thrusterInstallationFeature).foreach(_.save)

  }

}