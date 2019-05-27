name := "$name$"

version := "1.0.0"

scalaVersion := "2.11.12"

val sparkVersion = "2.3.3"

// spark
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided"
)

// kafka
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.kafka" % "kafka-clients" % "0.10.0.1"
)

// elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch-hadoop" % "6.6.2"

// velocity
libraryDependencies ++= Seq( 
  "org.apache.velocity" % "velocity" % "1.7",
  "org.apache.velocity" % "velocity-tools" % "2.0"
)

// buildinfo
lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "build"
  )

// fat JAR
assemblyOutputPath in assembly := file(s"target/scala-2.11/\${name.value}/\${name.value}.jar")
mainClass in assembly := Some("sparkStreamingKafka")
assemblyMergeStrategy in assembly := {
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
  case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
