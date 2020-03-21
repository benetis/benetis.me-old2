package me.benetis.partials

import me.benetis.Content
import me.benetis.model.{Partial, PostParams}
import org.joda.time.format.DateTimeFormat
import scalatags.Text.all._

object PostMeta extends Partial {
  def render(params: PostParams): Content = div(
    `class`:="post-meta",
    div(s"Posted on ${params.postedOn.toString(DateTimeFormat.mediumDate())}"),
    div(params.tags.map(t => div(s"#${t.value}")))
  )
}
