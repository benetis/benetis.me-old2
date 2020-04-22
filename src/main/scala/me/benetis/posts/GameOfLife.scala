package me.benetis.posts

import me.benetis.Content
import me.benetis.model._
import me.benetis.partials.{CodeSection, FeedbackSection, Image}
import org.joda.time.DateTime
import scalatags.Text.all._

object GameOfLife extends Post {

  override val params: PostParams =
    PostParams("Conway's Game of life",
               Slug("game-of-life"),
               Vector(Computing, Scala, Zio, Simulation),
               DateTime.parse("2020-04-23"))

  val imgSubfolder = "game_of_life"

  def render(): Content = {
    div(
      h2("Introduction"),
      p("""The game of life is a cellular automation devised by John Conway.
          |This simulation has only few rules, but it is turing complete [0] and incredibly interesting to watch.
          |With recent news about Conway, I thought its time to give it a go.
          |In one programming kata I had encountered this problem, but never went to fully complete this simulation.
          | This time with my favorite programming language: Scala and current favorite library ZIO [1].""".stripMargin),
      Image.render(s"$imgSubfolder/game_of_life_for_blog.gif"),
      h2("Rules of the game"),
      p("Universe is 2D infinite space. With that we have four simple rules for our automation:"),
      ul(
        li(
          "Any live cell with fewer than two live neighbours dies, as if by underpopulation"),
        li(
          "Any live cell with two or three live neighbours lives on to the next generation"),
        li(
          "Any live cell with more than three live neighbours dies, as if by overpopulation"),
        li(
          "Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction"),
      ),
      p("""Each cell will have 8 neighbours which need to be checked for these rules to be used
          |This results in next game state and same rules will need to be applied to create third game state. 
          |And so on.""".stripMargin),
      pre("""
          |+-----+
          ||YYY--|
          ||YXY--|
          |+YYY--+ , where X is the cell and Y are its neighbours.""".stripMargin),
      h4("Bibliography"),
      ul(
        li(
          a(href := "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life",
            "[0] - https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life")),
        li(
          a(href := "https://zio.dev/",
            "[1] - Type-safe, composable asynchronous and concurrent programming for Scala"))
      ),
      FeedbackSection.render()
    )
  }
}
