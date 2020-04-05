package me.benetis.posts

import me.benetis.Content
import me.benetis.model.{Computing, Meta, Post, PostParams, Scala, Slug}
import me.benetis.partials.{CodeSection, FeedbackSection, Image}
import org.joda.time.DateTime
import scalatags.Text.all._

object Backtracking extends Post {

  override val params: PostParams =
    PostParams("Backtracking: 8 queens",
               Slug("backtracking-8-queens"),
               Vector(Computing, Scala),
               DateTime.parse("2020-04-05"))

  val imgSubfolder = "backtracking"

  def render(): Content = {
    div(
      h2("Introduction"),
      p("""
          |Recently I started reading a book [1] about evolutionary computing. As you can guess - its about
          |problem solving using evolution concepts. One of the first examples in the book is about 8 queens puzzle.
          |Its a puzzle in constraint satisfaction problem space and my guess is that it is solved with
          |evolutionary computing later on. I think it would be interesting to look into more common
          |algorithm for this puzzle before proceeding with the book. It is called backtracking.
          |""".stripMargin),
      h2("Puzzle"),
      p("""
          | Put 8 queens on a 8x8 chessboard in such a way that no queen can take each other.
          | It might sound an easy puzzle to solve with code. However if you give it a shot -
          | you will quickly see that it is not so trivial.
          |""".stripMargin),
      Image.render(s"$imgSubfolder/8queens.png",
                   "One of the solutions to this puzzle"),
      h2("Approaching the problem"),
      h4("Data structures [2]"),
      CodeSection.renderScala("""
case class Square(x: Int, y: Int)

type PlacedQueens = Vector[Square]

val wholeBoard: Vector[Square] = {
  Range
    .inclusive(1, 8)
    .flatMap(x => {
      Range
        .inclusive(1, 8)
        .map(y => {
          Square(x, y)
        })
    })
    .toVector
}
  """),
      p("""Instead of usual chess squares with letters and digits, pair of digits (x, y) is used. 
          |This is to avoid mapping letters to digits for diagonal checks later on. This optimisation's problem's goal
          | is to find a vector of square where queens can be put. Board of is chess is just a matrix with pairs of row and column numbers as their values.
          |""".stripMargin),
      //todo think about mathml or something here
      //new file format which compiles to what I want? :o
      p("| (1,1) (1,2) ... |"),
      p("| (2,1) (2,2) ... |"),
      h4("Algorithm"),
      p(
        """
          |The first 'solution' that comes to mind is a recursive one. 
          |Try to put one queen and the next after that. 
          |No need to carry so called state of what squares were checked already and which ones were not. 
          |This results with a problem that if no solution is found with 8 queens and first queen on square (1,1). """.stripMargin
      ),
      p("""Since this is not enough, next step is to keep some sort of state. The easiest solution should be to just iterate
          |through all squares and start backtracking from each of the squares. This actually works, although the code below
          |is not performant due non-early return of foldLeft (takes a minute~ to find a solution). Next step would be to replace it with something that can do early returns,
          |but this is beyond my interest.
          |""".stripMargin),
      CodeSection.renderScala(
        """
def placeQueens(toPlace: Int,
                available: Vector[Square]): Option[PlacedQueens] = {

  if (toPlace <= 0) {
    Some(Vector.empty[Square])
  } else {
    val result = available.foldLeft(Vector.empty[Square])(
      (prev, queenToPlace: Square) => {
        val afterQueen = canBePlaced(queenToPlace, available)

        afterQueen match {
          case Some(nextSquares) =>
            placeQueens(toPlace - 1, nextSquares) match {
              case Some(value) =>
                queenToPlace +: value
              case None => prev
            }
          case None => prev
        }

      }
    )

    if (result.isEmpty)
      None
    else
      Some(result)
  }
}
          """),
      h4("Bibliography"),
      ul(
        li(
          "[1] - Introduction to Evolutionary Computing By A.E. Eiben, J.E. Smith "),
        li(
          a(
            href := "https://github.com/benetis/didactic-computing-machine/blob/master/software-and-math-exercises/constraint-satisfaction/src/main/scala/me/benetis/Main.scala",
            "[2] - Demo'ed code of this problem (github)"
          ))
      ),
      FeedbackSection.render()
    )
  }
}
