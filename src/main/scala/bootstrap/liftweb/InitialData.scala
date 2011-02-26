package bootstrap.liftweb

import code.model.User

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 12/31/10
 * Time: 8:18 AM
 * To change this template use File | Settings | File Templates.
 */

object InitialData {
  def init() = {
    if(User.count == 0)
    {
      User.create.firstName("Steve").lastName("Thompson").email("thompson4822@gmail.com").password("password").validated(true).save
    }
  }
}