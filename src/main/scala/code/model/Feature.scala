package code.model

import net.liftweb.mapper._

class Feature extends LongKeyedMapper[Feature] with IdPK with OneToMany[Long, Feature] {
  def getSingleton = Feature
  object name extends MappedString(this, 120)
  object purpose extends MappedString(this, 120)
  object goal extends MappedString(this, 120)
  object project extends MappedLongForeignKey(this, Project)
  object setup extends MappedLongForeignKey(this, SetUpTearDown)
  object tearDown extends MappedLongForeignKey(this, SetUpTearDown)
  object scenarios extends MappedOneToMany(Scenario, Scenario.feature)
}

object Feature extends Feature with LongKeyedMetaMapper[Feature]
