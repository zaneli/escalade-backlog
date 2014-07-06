name := "escalade-backlog"

version := "0.0.1"

scalaVersion := "2.11.1"

organization := "com.zaneli"

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions")

libraryDependencies ++= {
  Seq(
    "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.3" % "compile"
      exclude("junit", "junit"),
    "com.github.nscala-time" %% "nscala-time" % "1.2.0" % "compile",
    "junit" % "junit" % "4.11" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.specs2" %% "specs2" % "2.3.12" % "test"
  )
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
