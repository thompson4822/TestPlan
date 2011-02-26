package code.model

import net.liftweb.mapper._

class Scenario extends LongKeyedMapper[Scenario] with IdPK with OneToMany[Long, Scenario] {
  def getSingleton = Scenario
  object name extends MappedString(this, 120)
  object feature extends MappedLongForeignKey(this, Feature)
  object action extends MappedText(this)
  object outcome extends MappedText(this)
  object setup extends MappedLongForeignKey(this, SetUpTearDown)
  object tearDown extends MappedLongForeignKey(this, SetUpTearDown)
  object dispositions extends MappedOneToMany(Disposition, Disposition.scenario)
}

object Scenario extends Scenario with LongKeyedMetaMapper[Scenario]