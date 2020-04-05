package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, Post}
import scalatags.Text.all._

object Navigation extends Partial {
  def render(): Content = {
    tag("nav")(
      ul(
        li(
          a(href := "https://benetis.me/articles/backtracking-8-queens.html",
            "8 Queens backtracking")),
        li(
          a(href := "https://benetis.me/articles/new-world-blog.html",
            "From the New world")),
      ),
      div(a(href := "https://benetis.me/old", "Archived blog")),
    )
  }
}
