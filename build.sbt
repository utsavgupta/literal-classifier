lazy val root = (project in file("."))
  .settings(
    name := "literal-classifier",
    version := "1.0",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.4.2",
      "com.typesafe.akka" %% "akka-testkit" % "2.4.2",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )

