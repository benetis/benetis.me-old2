package me.benetis.partials

import me.benetis.Content
import me.benetis.model.Partial
import scalatags.Text.all._

object Image extends Partial {
  def renderOld(imageName: String): Content = div(
    img(src := s"images/old/$imageName")
  )
}
