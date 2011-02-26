package code.model

import net.liftweb.mapper._

class Project extends LongKeyedMapper[Project] with IdPK with OneToMany[Long, Project] with ManyToMany {
  def getSingleton = Project
  object name extends MappedString(this, 120)
  object description extends MappedText(this)
  object features extends MappedOneToMany(Feature, Feature.project)
  object users extends MappedManyToMany(ProjectUser, ProjectUser.project, ProjectUser.user, User)

  def addUser(user: User, role: Role.Value) = {
    ProjectUser.create.user(user).project(this).role(role).save
  }
}

object Project extends Project with LongKeyedMetaMapper[Project]