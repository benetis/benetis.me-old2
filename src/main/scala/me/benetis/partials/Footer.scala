package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, Post}
import org.joda.time.DateTime
import scalatags.Text.all._

object Footer extends Partial {
  def render(posts: Set[Post]): Content = div(
    footer(
      s"Žygimantas Benetis • 2016 - ${DateTime.now().year().get()} • ",
      a(href := "https://benetis.me", "Typical personal blog"),
    )
  )
}
