package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, Post}
import scalatags.Text.all._

object Navigation extends Partial {
  def render(): Content = {
    tag("nav")(
      ul(
        li(a(
          href := "https://benetis.me/articles/2-hours-of-engineering.html",
          "Interviews: How much can you do in two hours as software engineer?")),
        li(
          a(href := "https://benetis.me/articles/game-of-life.html",
            "Conway's Game of life")),
        li(
          a(href := "https://benetis.me/articles/backtracking-8-queens.html",
            "Backtracking: 8 queens")),
        li(
          a(href := "https://benetis.me/articles/new-world-blog.html",
            "From the New world")),
      ),
      div(a(href := "https://benetis.me/old", "Archived blog")),
    )
  }
}
