name := "ij-sparkcore"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"

dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value