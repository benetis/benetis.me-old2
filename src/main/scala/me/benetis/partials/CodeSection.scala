package me.benetis.partials

import me.benetis.Content
import me.benetis.model.Partial
import scalatags.Text.all._

object CodeSection extends Partial {
  def renderTypescript(codeBlock: String): Content = div(
    render(codeBlock)
  )

  def render(codeBlock: String): Content = div(
    pre(codeBlock)
  )

}
