package $package$.web

import $package$.health.HealthApi
import $package$.{Core, CoreActors}

trait $short_name;format="Camel"$Routes { this: Core with CoreActors =>

  def routes =
    health.route

  lazy val health: HealthApi = new HealthApi(healthActor)

}
