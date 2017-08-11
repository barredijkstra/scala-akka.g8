package $package$

import $organization;format="normalize"$.{TypesafeConfig, StandardTypesafeConfig}

case class ServerSettings(port: Int, interface: String)

trait $short_name;format="Camel"$Config {
  def serverSettings: ServerSettings
}

object $short_name;format="Camel"$Config extends $short_name;format="Camel"$Config
  with Typesafe$short_name;format="Camel"$Config
  with StandardTypesafeConfig

trait Typesafe$short_name;format="Camel"$Config extends $short_name;format="Camel"$Config { self: TypesafeConfig =>
  override lazy val serverSettings: ServerSettings =
    ServerSettings(
      port = config.getInt("$short_name;format="normalize"$.server.port"),
      interface = config.getString("$short_name;format="normalize"$.server.interface")
    )
}