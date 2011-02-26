package code.model

import net.liftweb.mapper._

class ProjectUser extends LongKeyedMapper[ProjectUser] with IdPK {
  def getSingleton = ProjectUser
  object project extends MappedLongForeignKey(this, Project)
  object user extends MappedLongForeignKey(this, User)
  object role extends MappedEnum[ProjectUser,Role.type](this, Role)
}

object ProjectUser extends ProjectUser with LongKeyedMetaMapper[ProjectUser]  {
  def roleFor(aUser: User, aProject: Project): Role.Value = {
    val entry = findAll(By(user, aUser), By(project, aProject))
    entry match {
      case Nil => Role.NoRole
      case first :: _ => first.role
    }
  }

  def findAll(aUser: User, aProject: Project): List[ProjectUser] = {
    findAll(By(user, aUser), By(project, aProject))
  }
}