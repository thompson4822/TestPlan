import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.2"

  // uncomment the following if you want to use the snapshot repo
  val scalatoolsSnapshot = ScalaToolsSnapshots

  val scalaTestVersion = "1.2.1-SNAPSHOT"
  val mockitoVersion = "1.8.5"

  override def jettyWebappPath  = webappPath

  // If you're using JRebel for Lift development, uncomment
  // this line
  override def scanDirectories = Nil

  val l4j = "log4j" % "log4j" % "1.2.16"
  val l4jbind = "org.slf4j" % "slf4j-log4j12" % "1.6.1"
  val liftWebkit = "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default" withSources() //withJavadoc()
  val liftMapper = "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default" withSources() //withJavadoc()
  val jettyDep = "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default" withSources()
  val junit = "junit" % "junit" % "4.5" % "test->default"
  val scalatest = "org.scalatest" % "scalatest" % scalaTestVersion % "test->default" withSources() withJavadoc()
  val mockito = "org.mockito" % "mockito-all" % mockitoVersion % "test->default"
  //val mongoDbRecord = "net.liftweb" %% "lift-mongodb-record" % "2.2-SNAPSHOT" % "compile->default" withSources()
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.6" % "test->default" withSources()
  val h2db = "com.h2database" % "h2" % "1.3.148" withSources() withJavadoc()
}
