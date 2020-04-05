package me.benetis.partials

import me.benetis.Content
import me.benetis.model.Partial
import scalatags.Text.all._

object Image extends Partial {
  def render(imageName: String, caption: String = ""): Content = div(
    `class`:="image-container",
    img(src := s"/images/$imageName"),
    div(`class`:="image-caption", caption)
  )
}
