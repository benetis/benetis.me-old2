import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "me.benetis"
ThisBuild / organizationName := "benetis.me"

val copyFiles = taskKey[Unit]("Copy public resources to output")

copyFiles := {
  import Path._

  val src = (Compile / resourceDirectory).value / "public"

  // get the files we want to copy
  val htmlFiles: Seq[File] = (src ** "*").get()

  // use Path.rebase to pair source files with target destination in crossTarget
  val pairs = htmlFiles pair rebase(src,
                                    (Compile / baseDirectory).value / "output")

  // Copy files to source files to target
  IO.copy(pairs,
          CopyOptions.apply(overwrite = true,
                            preserveLastModified = true,
                            preserveExecutable = false))

}

lazy val root = (project in file("."))
  .settings(
    (compile in Compile) := {
      val compileAnalysis = (compile in Compile).value
      copyFiles.value
      compileAnalysis
    },
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:postfixOps"
    ),
    name := "benetis.me",
    libraryDependencies ++= Seq(
      scalaTest                % Test,
      "com.lihaoyi"            %% "scalatags" % "0.8.2",
      "dev.zio"                %% "zio" % "1.0.12",
      "com.github.nscala-time" %% "nscala-time" % "2.22.0",
      "com.lihaoyi"            %% "fastparse" % "2.2.2",
      "eu.timepit" %% "refined"                 % "0.9.28",
      "eu.timepit" %% "refined-cats"            % "0.9.28",
      "io.estatico" %% "newtype" % "0.4.4"
    ),
    watchTriggers += baseDirectory.value.toGlob/ "src" / "*.css",
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
