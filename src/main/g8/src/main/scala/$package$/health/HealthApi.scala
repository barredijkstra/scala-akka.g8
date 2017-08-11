package $package$.health

import akka.actor.ActorRef
import akka.http.scaladsl.server.Route
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.duration._

class HealthApi(healthActor: ActorRef) extends HealthJsonProtocol {

  implicit val timeout: Timeout = Timeout(5 seconds)

  import akka.http.scaladsl.server.Directives._

  def route: Route =
    pathPrefix("health") {
      getHealth
    }


  def getHealth =
    pathEndOrSingleSlash {
      complete(
        (healthActor ? GetHealth).mapTo[HealthUpdate]
      )
    }
}
