name := """WebCalc"""
organization := "none"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test 
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "none.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "none.binders._"
