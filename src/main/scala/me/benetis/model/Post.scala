package me.benetis.model

import me.benetis.Content
import scalatags.Text.all._
import com.github.nscala_time.time.Imports._

case class Slug(value: String)

case class PostParams(
    title: String,
    slug: Slug,
    tags: Vector[CategoryTag],
    postedOn: DateTime
)

trait Partial

trait Post extends Partial {
  val params: PostParams
  def render(): Content
}
