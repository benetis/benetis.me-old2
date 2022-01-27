package me.benetis

import me.benetis.compiler.Compiler
import me.benetis.model.Post
import zio.console.{Console, putStrLn}
import zio.{App, ExitCode, URIO, ZIO}

object MyApp extends App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    myAppLogic.exitCode

  val publishedPosts: Set[Post] = Set(
    posts.NewWorld,
    posts.Backtracking,
    posts.GameOfLife,
    posts.SoftwareEngineerTwoHours
  )

  val myAppLogic: ZIO[Console, Throwable, Unit] =
    for {
      _ <- putStrLn("Starting...")
      _ <- Compiler.compile(publishedPosts)
    } yield ()
}
