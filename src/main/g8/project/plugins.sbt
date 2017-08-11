addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "2.0.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.1")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25" // Needed by sbt-git
libraryDependencies += "com.spotify" % "docker-client" % "8.8.4"
