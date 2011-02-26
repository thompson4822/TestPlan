package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import code.model._
import javax.mail.{Authenticator, PasswordAuthentication}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
	         new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User, Disposition, Feature, Project,
      ProjectUser, Scenario, SetUpTearDown)

    InitialData.init()

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    def sitemap = SiteMap(
      Menu.i("Home") / "index" >> User.AddUserMenusAfter, // the simple way to declare a menu

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))

    def sitemapMutators = User.sitemapMutator

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(() => sitemapMutators(sitemap))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))    

    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)

    // set DocType to HTML5
    LiftRules.docType.default.set((r: Req) => r match {
      case _ if S.skipDocType => Empty
      case _ if S.getDocType._1 => S.getDocType._2
      case _ => Full(DocType.html5)
    })

    // Keeps comments from being stripped out of HTML templates
    LiftRules.stripComments.default.set(() => false)

    // config an email sender
		configMailer
  }

  /*
	* Config mailer
	*/
	private def configMailer = {

		var isAuth = Props.get("mail.smtp.auth", "false").toBoolean

		Mailer.customProperties = Props.get("mail.smtp.host", "localhost") match {
			case "smtp.gmail.com" =>
				isAuth = true
				Map(
					"mail.smtp.host" -> "smtp.gmail.com",
					"mail.smtp.port" -> "587",
					"mail.smtp.auth" -> "true",
					"mail.smtp.starttls.enable" -> "true"
				)
			case h => Map(
				"mail.smtp.host" -> h,
				"mail.smtp.port" -> Props.get("mail.smtp.port", "25"),
				"mail.smtp.auth" -> isAuth.toString
			)
		}

		if (isAuth) {
			(Props.get("mail.smtp.user"), Props.get("mail.smtp.pass")) match {
				case (Full(username), Full(password)) =>
					Mailer.authenticator = Full(new Authenticator() {
						override def getPasswordAuthentication = new
							PasswordAuthentication(username, password)
					})
				case _ => logger.error("Username/password not supplied for Mailer.")
			}
		}
	}
}
