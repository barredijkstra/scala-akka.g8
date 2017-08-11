package $package$.health

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class HealthActorSpec extends TestKit(ActorSystem("HealthActorSpec")) with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {
  override def afterAll(): Unit =
    TestKit.shutdownActorSystem(system)

  "A GetHealth request" should "return a HealthUpdate" in {
    val healthActor = system.actorOf(HealthActor.props)
    healthActor ! GetHealth
    expectMsg(HealthUpdate(HealthStatus.Ok, "OK"))
  }


}
