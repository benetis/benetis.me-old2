package me.benetis.posts

import me.benetis.Content
import me.benetis.model.{Meta, Post, PostParams, Slug}
import me.benetis.partials.FeedbackSection
import org.joda.time.DateTime
import scalatags.Text.all._

object TestPageForSE extends Post {

  override val params: PostParams =
    PostParams("Test page for SE",
               Slug("test-page-se"),
               Vector(Meta),
               DateTime.parse("2021-01-24"))

  def render(): Content = {
    div(
      h2("Introduction"),
      a(href := "benetis.me/from-new-world",
        "TEST PAGE"),
      p {
        """
          |Some content here and there, Should be hidden from everyone except with link""".stripMargin
      },
    )
  }
}
