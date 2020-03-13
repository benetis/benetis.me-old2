package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, Post}
import scalatags.Text.all._

object Footer extends Partial {
  def render(posts: Set[Post]): Content = div(
    footer("fuuuf")
  )
}
