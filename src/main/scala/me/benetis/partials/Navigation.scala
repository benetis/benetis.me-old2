package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, Post}
import scalatags.Text.all._

object Navigation extends Partial {
  def render(): Content = {
    tag("nav")(
      div(
        a(href := "https://benetis.me/posts/new-world-blog.html",
          "From the New world")
      ),
      div(a(href := "https://benetis.me/old", "Archived blog")),
    )
  }
}
