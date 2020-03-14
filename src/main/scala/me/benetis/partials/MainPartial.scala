package me.benetis.partials

import me.benetis.model.{Partial, Post}
import scalatags.Text
import scalatags.Text.all._

object MainPartial extends Partial {

  private def template(post: Post, posts: Set[Post]): Text.TypedTag[String] =
    html(
      head(
        script(""),
        link(
          href := "styles/main.css",
          `type` := "text/css",
          rel := "stylesheet"
        ),
        link(
          href := "https://fonts.googleapis.com/css?family=Montserrat&display=swap",
          rel := "stylesheet"
        )
      ),
      body(
        div(
          `class` := "page-container",
          h1(post.params.title),
          div(`class` := "post-container", post.render()),
          Navigation.render(),
        ),
        Footer.render(posts)
      )
    )

  def render(post: Post, posts: Set[Post]): String =
    "<!DOCTYPE html>" + template(post, posts)

}
