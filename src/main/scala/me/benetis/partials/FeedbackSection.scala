package me.benetis.partials

import me.benetis.Content
import me.benetis.model.Partial
import scalatags.Text
import scalatags.Text.all._

object FeedbackSection extends Partial {
  def render(): Content = div(
    h4("Feedback"),
    p(
      "If you have any suggestions or want to contact me - I am eagerly waiting for your feedback: { blog at benetis.me }."
    )
  )
}
