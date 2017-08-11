package $package$.health

import akka.actor.Props
import $organization;format="normalize"$.CommonActor

class HealthActor extends CommonActor {
  override def receive = {
    case GetHealth =>
      sender() ! HealthUpdate(HealthStatus.Ok, "OK")
  }
}

object HealthActor {
  def props: Props =
    Props(new HealthActor)
}
