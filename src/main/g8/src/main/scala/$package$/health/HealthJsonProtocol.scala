package $package$.health

import $organization;format="normalize"$.JsonProtocol

trait HealthJsonProtocol extends JsonProtocol {
  implicit val healthStatusFormat = jsonEnumFormat(HealthStatus)
  implicit val healthUpdateFormat = jsonFormat2(HealthUpdate)
}
