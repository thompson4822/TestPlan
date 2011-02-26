package code.domain

import code.model.{Role, ProjectUser, Project, User}

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 12/29/10
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */

trait PermissionManager {
  def canViewProject(user: User, project: Project):Boolean
}

class PermissionManagerImpl extends PermissionManager {
  def canViewProject(user: User, project: Project):Boolean = {
    return ProjectUser.roleFor(user, project) != Role.NoRole
  }
}