package me.benetis.partials

import me.benetis.Content
import me.benetis.model.Partial
import scalatags.Text.all._

object CodeSection extends Partial {

  val scalaKeywords = Vector(
    "abstract",
    "case",
    "catch",
    "class",
    "def",
    "do",
    "else",
    "extends",
    "false",
    "final",
    "finally",
    "for",
    "forSome",
    "if",
    "implicit",
    "import",
    "lazy",
    "match",
    "new",
    "null",
    "object",
    "override",
    "package",
    "private",
    "protected",
    "return",
    "sealed",
    "super",
    "this",
    "throw",
    "trait",
    "try",
    "true",
    "type",
    "val",
    "var",
    "while",
    "with",
    "and",
    "yield"
  )

  def renderTypescript(codeBlock: String): Content = div(
    render(codeBlock)
  )

  def renderScala(codeBlock: String): Content = {
    render(codeBlock)
  }

  def render(codeBlock: String): Content = {
    pre(
      `class` := "code-container",
//      attr("data-line") := "4",
      code(
        `class` := "language-scala line-numbers",
        codeBlock
      )
    )
  }

}
