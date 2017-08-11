package $package$

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait Core {
  implicit def config: $short_name;format="Camel"$Config

  implicit def system: ActorSystem

  implicit def actorMaterializer: ActorMaterializer
}

trait BootedCore extends Core {
  override implicit lazy val config = $short_name;format="Camel"$Config
  override implicit lazy val system = ActorSystem("$short_name;format="normalize"$", config.config)
  override implicit lazy val actorMaterializer = ActorMaterializer()

  sys.addShutdownHook(system.terminate())
}
