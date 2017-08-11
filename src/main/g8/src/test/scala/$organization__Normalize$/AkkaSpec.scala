package $organization;format="normalize"$

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class AkkaSpec extends TestKit(ActorSystem("AkkaSpec")) with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {
  override def afterAll(): Unit =
    TestKit.shutdownActorSystem(system)

  "A dispatcher for blocking work" should "be available" in {
    Akka.blockingDispatcher shouldNot be(null)
  }
  "A custom dispatcher" should "be available by name" in {
    Akka.dispatcher("test-dispatcher") shouldNot be(null)
  }
  it should "fail on a non configured name" in {
    intercept[akka.ConfigurationException] {
      Akka.dispatcher("non-existing")
    }
  }

  "An actor system" should "be created using a (implicit) config" in {
    implicit val config = new TestConfig(ConfigFactory.parseString("""akka.home = "/fake/home""""))
    val newSystem = Akka.actorSystem("new-system")

    system.settings.Home shouldNot equal(Some(config.home)) // sanity check
    newSystem.settings.Home should equal(Some(config.home))

  }

  class TestConfig(c: => Config) extends TypesafeConfig {
    override lazy val config: Config = c
    lazy val home: String = config.getString("akka.home")
  }
}
