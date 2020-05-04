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
               DateTime.parse("2020-05-04"))

  val imgSubfolder = "game_of_life"

  def render(): Content = {
    div(
      h2("Introduction"),
      p("""The game of life is a cellular automation devised by John Conway.
          |This simulation has only few rules, but it is turing complete [0] and incredibly interesting to watch.
          |With recent news about Conway, I thought its time to give it a go.
          |In one programming kata I had encountered this problem, but never went to fully complete this simulation.
          | This time with my favorite programming language: Scala and current favorite library: ZIO [1].""".stripMargin),
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
      h2("Defining the task"),
      p("""In order to see how simulation works two things are needed: 1) code to generate next state 2) code to render state.
          |The first part is where the rules of simulation are implemented. For this my plan is to implement them
          |using TDD methodology where I write least amount of code possible. For the second part the state just needs
          | to be rendered. I think its a good place to use HTML Canvas and just paint rectangles to represent cells.""".stripMargin),
      h2("Test driven development"),
      p("""Starting with simplest test and then implementing the code.
          | A test for first rule: after one iteration we expect universe to become empty.
          | Also, since universe is infinite - there are infinite amount of dead cells. 
          | This means that live cells should be stored. On top of that - no reason to rush with
          | multi dimensional arrays just because this task deals with 2D plane. 
          | That's the beauty of TDD as in the end you never need them anyway.""".stripMargin),
      CodeSection.renderScala(
        """
test("cell should die if it has no neighbours") {
      /*
      +---------+
      |---------|
      |---------|
      |-X-------|
      +---------+
       */
      val state = Set(Point(1, 1))

      assert(GameOfLifeRules.nextState(state))(equalTo(Set.empty[Point]))
}           
        """),
      CodeSection.renderScala(
        """
test("dead cell with 3 live neighbours becomes a live cell") {
      /* Input
      +---------+
      |---------|
      |-Y-------|
      |Y-Y------|
      +---------+
       */
      /* Expected
      +---------+
      |---------|
      |-Y-------|
      |-X-------|
      +---------+
       */
      val state = Set(Point(0, 1), Point(1, 2), Point(2, 1))
      val expected = Set(Point(1, 2), Point(1, 1))

      assert(GameOfLifeRules.nextState(state))(equalTo(expected))
}        
        """),
      p(
        """To keep this as short as possible - other tests can be found """,
        a(
          href := "https://github.com/benetis/didactic-computing-machine/blob/master/software-and-math-exercises/game-of-life/src/test/scala/me/benetis/GameOfLifeRulesSpec.scala",
          "in github"
        )
      ),
      p("""In order to test the rules - in total 6 tests were written. It should cover the logic. 
          |In the beginning, state was held in an vector, but as more rules got implemented one optimization
          |for easier code was to replace vectors with sets as all points are unique anyway.
          |Next step is to render calculated 2D universe.""".stripMargin),
      h2("Will it render?"),
      p("""Few things to be done to render cells. First is to decide on frameworks/tools and platform. 
          |'Platform' was mentioned before - HTML Canvas. 
          |With HTML Canvas comes Javascript and ScalaJS is a perfect candidate there. I did consider Swing and JavaFX, but
          |after playing with it a bit I was not impressed. It seemed hard to do anything 'functionally'. """.stripMargin),
      p("Part of program which coordinates rendering:"),
      CodeSection.renderScala(
        """
val program: ZIO[Clock, Throwable, Unit] = {
  for {
    renderer <- prepareScreen(config)
    refState <- Ref.make(RandomState.randomCenter(config))
    _ <- (render(renderer, config, refState) *> updateState(refState) *> UIO(
      dom.console.log("tick")
    )).repeat(Schedule.fixed(1.second)).forever
  } yield ()
}
        """),
      p("""Prepare screen is just setting up canvas and returning 2D renderer.
          |RefState is where things get interesting. 
          |Current cells universe is stored in that reference. 
          |And that reference is mutable reference to cell universe value. 
          |On top of that it is atomic and thread safe.
          |First state of cell universe is just a random state.""".stripMargin),
      p("""Next line contains the main loop of this application: render cell universe,
          |calculate next iteration and update reference with new state.
          | Do this every 1 second and continue forever.""".stripMargin),
      p("""Now to look at what happens during render and during updateState:"""),
      CodeSection.renderScala(
        """
private def render(renderer: CanvasRenderingContext2D,
                   config: RenderConfig,
                   ref: Ref[LifeState]): ZIO[Any, Nothing, Unit] = {
  for {
    _ <- clearCanvas(renderer, config)
    state <- ref.get
    _ = state.foreach(p => renderPoint(renderer, config, p))
  } yield ()
}

private def updateState(ref: Ref[LifeState]): ZIO[Any, Nothing, Unit] =
  for {
    _ <- ref.update(state => GameOfLifeRules.nextState(state))
  } yield ()
      """),
      p("""In render function state is retrieved and iterated. 
          |State is just a Set of points, so to render it only one iteration is needed.
          |Update state does two things: 1) calculate next state 2) update ref with that state.""".stripMargin),
      h2("Summary"),
      p("""This concludes this quick write up about my experiment with Conway's game of life implementation. 
          |Two conclusions I made: Test driven development can really improve code to prevent over engineering solutions and
          |simulations are very interesting.""".stripMargin),
      p(
        "Missing code code can be found ",
        a(href := "https://github.com/benetis/didactic-computing-machine/tree/master/software-and-math-exercises/game-of-life",
          "in github [2]")
      ),
      h4("Bibliography"),
      ul(
        li(
          a(href := "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life",
            "[0] - https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life")),
        li(
          a(href := "https://zio.dev/",
            "[1] - Type-safe, composable asynchronous and concurrent programming for Scala")),
        li(
          a(href := "https://github.com/benetis/didactic-computing-machine/tree/master/software-and-math-exercises/game-of-life",
            "[2] - Github link to the sources"))
      ),
      FeedbackSection.render()
    )
  }
}
