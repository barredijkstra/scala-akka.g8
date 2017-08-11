package $package$

import akka.actor.ActorRef
import $package$.health.HealthActor

trait CoreActors { self: Core =>
  lazy val healthActor: ActorRef = system.actorOf(HealthActor.props, "health")
}
