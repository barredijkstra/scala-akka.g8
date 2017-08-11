package $package$.health

import akka.actor.{Actor, Props}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class HealthApiSpec extends FlatSpec with ScalatestRouteTest with Matchers with BeforeAndAfterAll {
  override def afterAll(): Unit =
    TestKit.shutdownActorSystem(system)

  def api = new HealthApi(system.actorOf(Props(new MockHealthActor)))

  "The a /health call" should "return a HealthUpdate JSON message" in {
    Get("/health") ~> api.route ~> check {
      responseAs[String] shouldEqual """{"status":"Ok","message":"OK"}"""
    }
  }

  class MockHealthActor extends Actor {
    override def receive: Receive = {
      case GetHealth =>
        sender() ! HealthUpdate(HealthStatus.Ok, "OK")
    }
  }

}
