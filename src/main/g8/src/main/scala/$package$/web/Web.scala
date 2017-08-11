package $package$.web

import akka.actor.ActorRef
import $package$.{Core, CoreActors}

trait Web { this: $short_name;format="Camel"$Routes with Core with CoreActors =>
  val webserver: ActorRef =
    system.actorOf(Webserver.props(config.serverSettings.interface, config.serverSettings.port, routes), "webserver")
}



