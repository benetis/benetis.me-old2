package me.benetis.compiler

import me.benetis.model.Post
import me.benetis.partials.MainPartial
import me.benetis.printer.FilePrinter
import zio.console.Console
import zio.{UIO, ZIO, console}

object Compiler {

  case class CompiledPage(pageContent: String, name: String)

  def compile(posts: Set[Post]): ZIO[Console, Throwable, Unit] =
    for {
      _ <- ZIO.collectAll(
        posts.map(p => compilePage(p, posts))
      )
    } yield ()

  def compilePage(post: Post,
                  allPosts: Set[Post]): ZIO[Console, Throwable, Unit] = {
    for {
      output <- UIO(MainPartial.render(post, allPosts))
      _      <- FilePrinter.outputPage(CompiledPage(output, post.params.slug.value))
    } yield ()
  }

}
