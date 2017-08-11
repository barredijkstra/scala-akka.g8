package $organization;format="normalize"$

import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.dispatch.MessageDispatcher
import akka.event.{Logging, LoggingAdapter}
import akka.util.Timeout

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object Akka {
  def actorSystem(name: String)(implicit conf: TypesafeConfig): ActorSystem =
    ActorSystem(name, conf.config)

  def blockingDispatcher(implicit system: ActorSystem): MessageDispatcher =
    dispatcher("test-blocking-dispatcher")

  def dispatcher(name: String)(implicit system: ActorSystem): MessageDispatcher =
    system.dispatchers.lookup(name)

  def logging(ctxClass: Class[_])(implicit system: ActorSystem): LoggingAdapter =
    Logging(system, ctxClass)

  def logging(loggerName: String)(implicit system: ActorSystem): LoggingAdapter =
    Logging(system, loggerName)
}

trait CommonActor extends Actor with ActorLogging {

  implicit val ec: ExecutionContextExecutor = context.dispatcher
  implicit val system: ActorSystem = context.system
  implicit val timeout: Timeout = 5 seconds


  override def aroundPostRestart(reason: Throwable): Unit = {
    super.aroundPostRestart(reason)
    log.debug(s"Restarted actor {} due to {}", self.path, reason.getMessage)
  }

  override def aroundPreStart(): Unit = {
    log.debug(s"Starting actor {}", self.path)
    super.aroundPreStart()
  }

  override def aroundPostStop(): Unit = {
    super.aroundPostStop()
    log.debug(s"Stopped actor {}", self.path)
  }
}
