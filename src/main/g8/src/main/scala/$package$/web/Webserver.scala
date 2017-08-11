package $package$.web

import akka.actor.{Props, Status}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.pattern._
import akka.stream.ActorMaterializer
import $organization;format="normalize"$.CommonActor

class Webserver(interface: String, port: Int, routes: Route)(implicit am: ActorMaterializer) extends CommonActor {

  Http(context.system).bindAndHandle(routes, interface, port) pipeTo self

  def receive = {
    case Http.ServerBinding(address) =>
      log.info("HTTP API listening on {}", address)
    case Status.Failure(cause) =>
      log.error(cause, "Unable to bind HTTP API to {}:{}", interface, port)
      context.stop(self)
  }
}

object Webserver {
  def props(interface: String, port: Int, routes: Route)(implicit am: ActorMaterializer): Props =
    Props(new Webserver(interface, port, routes))
}