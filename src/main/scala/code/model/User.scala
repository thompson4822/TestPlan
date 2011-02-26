package code {
package model {

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users" // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)
  // define the order fields will appear in forms and output
  override def fieldOrder = List(id, firstName, lastName, email,
  locale, timezone, password)

  // comment this line out to require email validations
  override def skipEmailValidation = true

}

class User extends MegaProtoUser[User] with ManyToMany {
  def getSingleton = User
  object projects extends MappedManyToMany(ProjectUser, ProjectUser.user, ProjectUser.project, Project)

  // Don't really care much for the following.  I'm thinking that a project should be saved before even an
  // owner is associated with it, which would mean that this functionality would then just be a part of the
  // ProjectUser object.
  def createProject(project: Project) = {
    project.save
    ProjectUser.create.project(project).user(this).role(Role.Owner).save
  }

  def removeFromProject(user: User, project: Project) = {
    if(ProjectUser.roleFor(this, project) == Role.Owner)
        ProjectUser.findAll(user, project).foreach{ pu => pu.delete_!; ()}
  }
}

}}