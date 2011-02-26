package code.model

import net.liftweb.mapper._
import net.liftweb.common._
import net.liftweb.util._
import java.sql._

object InMemoryDB {

 val vendor = new StandardDBVendor("org.h2.Driver",
    "jdbc:h2:mem:lift;DB_CLOSE_DELAY=-1", Empty, Empty)

 Logger.setup = Full(net.liftweb.util.LoggingAutoConfigurer())
 Logger.setup.foreach { _.apply() }

 def init {
  DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
  Schemifier.destroyTables_!!(Schemifier.infoF _, Disposition, Feature, Project, ProjectUser,
      Scenario, SetUpTearDown, User)
  Schemifier.schemify(true, Schemifier.infoF _, Disposition, Feature, Project, ProjectUser,
      Scenario, SetUpTearDown, User)
 }

 def shutdown {
  // TODO: figure out if anything goes here
 }
}