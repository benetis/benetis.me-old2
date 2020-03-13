package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, Post}
import scalatags.Text.all._

object Navigation extends Partial {
  def render(): Content = {
    tag("nav")(
      div(a("Old blog - "))
    )
  }
}
