package $package$.health

sealed trait HealthMessage

case object GetHealth extends HealthMessage
