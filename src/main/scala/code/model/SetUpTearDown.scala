package code.model

import net.liftweb.mapper._

class SetUpTearDown extends LongKeyedMapper[SetUpTearDown] with IdPK {
  def getSingleton = SetUpTearDown
  object instructions extends MappedText(this)
  object isSetup extends MappedBoolean(this)
}

object SetUpTearDown extends SetUpTearDown with LongKeyedMetaMapper[SetUpTearDown]