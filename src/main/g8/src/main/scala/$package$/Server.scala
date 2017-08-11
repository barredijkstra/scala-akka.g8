package $package$

import $package$.web.{$short_name;format="Camel"$Routes, Web}

object Server extends App with BootedCore with CoreActors with $short_name;format="Camel"$Routes with Web
