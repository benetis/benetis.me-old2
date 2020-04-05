package me.benetis

import me.benetis.compiler.Compiler
import me.benetis.model.Post
import zio.console.{Console, putStrLn}
import zio.{App, ZIO}

object MyApp extends App {

  def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    myAppLogic.fold(_ => 1, _ => 0)

  val publishedPosts: Set[Post] = Set(
    posts.NewWorld,
    posts.Backtracking
  )

  val myAppLogic: ZIO[Console, Throwable, Unit] =
    for {
      _ <- putStrLn("Starting...")
      _ <- Compiler.compile(publishedPosts)
    } yield ()
}
