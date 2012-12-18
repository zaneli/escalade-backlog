name := "escalade-backlog"

version := "0.0.1"

scalaVersion := "2.9.2"

organization := "com.zaneli"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= {
  Seq(
    "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.3" % "compile"
      exclude("junit", "junit"),
    "org.scala-tools.time" % "time_2.9.1" % "0.5" % "compile",
    "junit" % "junit" % "4.9" % "test",
    "org.mockito" % "mockito-core" % "1.9.0" % "test",
    "org.specs2" %% "specs2" % "1.10" % "test",
    "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test"
  )
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file(
  "file", new java.io.File(System.getProperty("repository.url", ""))))