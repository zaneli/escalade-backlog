name := "escalade-backlog"

version := "0.0.1"

scalaVersion := "2.11.4"

organization := "com.zaneli"

crossScalaVersions := Seq("2.10.4", "2.11.4")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions")

libraryDependencies ++= {
  Seq(
    "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.3" % "compile"
      exclude("junit", "junit"),
    "com.github.nscala-time" %% "nscala-time" % "1.6.0" % "compile",
    "org.mockito" % "mockito-core" % "1.10.17" % "test",
    "org.specs2" %% "specs2-core" % "2.4.15" % "test",
    "org.specs2" %% "specs2-mock" % "2.4.15" % "test"
  )
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
