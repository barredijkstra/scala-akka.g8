package $organization;format="normalize"$

import java.{time => jt}

import com.typesafe.config._

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

trait TypesafeConfig {
  def config: Config

  protected def asOption[T](getter: (Config) => T): Option[T] = Try(getter(config)) match {
    case Success(value) => Some(value)
    case Failure(_: ConfigException.Missing) => None
    case Failure(ex) => throw ex
  }

  protected def getMap(path: String): Map[String, AnyRef] = (for {
    obj <- config.getObjectList(path).asScala
    entry <- obj.entrySet().asScala
  } yield (entry.getKey, entry.getValue.unwrapped())).toMap

  implicit protected def asScalaDuration(d: jt.Duration): Duration =
    Duration.fromNanos(d.toNanos)

}

trait StandardTypesafeConfig extends TypesafeConfig {
  override def config: Config = ConfigFactory.load
}
