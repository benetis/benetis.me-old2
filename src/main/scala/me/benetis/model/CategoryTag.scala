package me.benetis.model

sealed trait CategoryTag {
  def value: String
}
case object Frontend extends CategoryTag {
  override def value: String = "Frontend"
}
case object Scala extends CategoryTag {
  override def value: String = "Scala"
}
case object Elm extends CategoryTag {
  override def value: String = "Elm"
}
case object Meta extends CategoryTag {
  override def value: String = "Meta"
}

case object Computing extends CategoryTag {
  override def value: String = "Computing"
}
