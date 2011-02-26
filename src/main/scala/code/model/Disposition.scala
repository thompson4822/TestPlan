package code.model

import net.liftweb.mapper._

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 12/18/10
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */

class Disposition extends LongKeyedMapper[Disposition] with IdPK {
  def getSingleton = Disposition
  object scenario extends MappedLongForeignKey(this, Scenario)
  object user extends MappedLongForeignKey(this, User)
  object dispositionDate extends MappedDateTime(this)
  object passed extends MappedBoolean(this)
  object details extends MappedText(this)
}

object Disposition extends Disposition with LongKeyedMetaMapper[Disposition] {
  //override def dbTableName = "Disposition"

}