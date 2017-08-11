package $package$.health

import enumeratum.{Enum, EnumEntry}

import scala.collection.immutable

sealed trait HealthStatus extends EnumEntry

object HealthStatus extends Enum[HealthStatus] {

  case object Ok extends HealthStatus

  case object Warning extends HealthStatus

  case object Error extends HealthStatus

  override def values: immutable.IndexedSeq[HealthStatus] = findValues
}

case class HealthUpdate(
  status: HealthStatus,
  message: String
)
