import sbt._

lazy val `$name;format="normalize"$` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, JavaServerAppPackaging, AshScriptPlugin)
  .settings(
    settings,
    name := "$short_name;format="normalize"$",
    mainClass := Some("$package$.Server"),
    libraries.nscalaTime,
    libraries.enumeratum,
    libraries.akka,
    libraries.akkaHttp,
    libraries.test,
    libraries.overrides,
    docker
  )

lazy val docker = Seq(
  packageName := StringBuilder.newBuilder
    .append("$organization_shortname$/$short_name$/")
    .append(GitVersion.dockerBranch)
    .mkString,
  version := GitVersion.dockerVersion,
  dockerUpdateLatest := true,

  dockerBaseImage := "java:8-alpine",
  maintainer := "$organization$",
  dockerExposedVolumes := Seq("/init", "/log"),
  dockerExposedPorts := Seq(8080)
)

lazy val settings =
  buildSettings ++
    organizationSettings

lazy val buildSettings = Seq(
  version := GitVersion.applicationVersion,
  organization := "$organization$",
  scalaVersion := "$scala_version$",
  scalacOptions ++= crossScalacOptions(scalaVersion.value),
  resolvers ++= Seq(Resolver.mavenLocal, Resolver.jcenterRepo),
  unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
  unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),
  shellPrompt := ShellPrompt.buildShellPrompt
)

def crossScalacOptions(version: String) = Seq(
  "-encoding", "UTF-8",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-unchecked",
  "-explaintypes",
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-adapted-args",
  "-Ywarn-unused"
) ++ (CrossVersion.partialVersion(version) match {
  case Some((2, majorVersion)) if majorVersion >= 12 =>
    Seq("-target:jvm-1.8")
  case _ =>
    Seq("-target:jvm-1.7")
})

lazy val organizationSettings = Seq(
  organizationName := "$organization_name$",
  organizationHomepage := Some(url("$organization_website$")),
  startYear := Some($project_year$),
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("$organization_website$$short_name$/")),
  scmInfo := Some(ScmInfo(
    browseUrl = url("https://github.com/$organization_shortname$/$short_name$"),
    connection = "git@github.com:$organization_shortname$/$short_name$"
  )),
  developers := List(
    Developer(
      id = "johndoe",
      name = "John Doe",
      email = "john.doe@test.com",
      url = url("https://github.com/johndoe")
    )
  )
)

lazy val libraries = new {
  val overrides = dependencyOverrides ++= Set(
    "com.typesafe.akka" %% "akka-actor" % version.akka,
    "com.typesafe.akka" %% "akka-stream" % version.akka
  )
  val enumeratum = libraryDependencies += "com.beachape" %% "enumeratum" % version.enumeratum
  val nscalaTime = libraryDependencies += "com.github.nscala-time" %% "nscala-time" % version.nscalaTime
  val akka = libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % version.akka,
    "com.typesafe.akka" %% "akka-stream" % version.akka,
    "com.typesafe.akka" %% "akka-testkit" % version.akka % Test
  )
  val akkaHttp = libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % version.akkaHttp,
    "com.typesafe.akka" %% "akka-http-spray-json" % version.akkaHttp,
    "com.typesafe.akka" %% "akka-http-testkit" % version.akkaHttp % Test
  )
  val test = libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % version.scalacheck,
    "org.scalatest" %% "scalatest" % version.scalatest,
    "org.scalamock" %% "scalamock-scalatest-support" % version.scalamock
  ) map (_ % Test)

  object version {
    val enumeratum = "$lib_enumeratum_version$"
    val nscalaTime = "$lib_nscalatime_version$"

    val akka = "$lib_akka_version$"
    val akkaHttp = "$lib_akkahttp_version$"

    val scalatest = "$lib_scalatest_version$"
    val scalamock = "$lib_scalamock_version$"
    val scalacheck = "$lib_scalacheck_version$"
  }
}