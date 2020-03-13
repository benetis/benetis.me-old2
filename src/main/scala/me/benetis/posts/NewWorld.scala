package me.benetis.posts

import me.benetis.Content
import me.benetis.model.{Meta, Post, PostParams, Slug}
import me.benetis.partials.FeedbackSection
import org.joda.time.DateTime
import scalatags.Text
import scalatags.Text.all._

object NewWorld extends Post {

  override val params: PostParams =
    PostParams("From the New World",
               Slug("new-world-blog"),
               Vector(Meta),
               DateTime.parse("2020-03-12"))

  def render(): Content = {
    div(
      h2("Introduction"),
      a(href := "https://www.youtube.com/watch?v=Qut5e3OfCvg",
        "From the New World by Antonín Dvořák [youtube video]"),
      p {
        """
          |Chances are that you like classical music. I personally do. There is a beautiful symphony "From the New World" 
          | composed by Antonín Dvořák.
          | The interesting part is that If you check local classical music events around January the 1st - you will probably see
          | this symphony being performed. In my humble opinion, 
          | coupled with the music itself, 
          | it signals that this piece of art truly carries meaning of new beginning archetype. I am not sure If this is closer to
          | rebirth, but it does feel like there is no reason to step back into the old world and we proudly get to celebrate the new one.
          |""".stripMargin
      },
      p {
        """
          | Stepping back into blogging I feel similar. It feels truly feels exciting.
          | I can do it and I can do it differently and better (in my opinion).
          | The question is what it is better. Previously I wrote blogs to learn to write better and to learn things better.
          | Every opportunity at some exercise or presentation I did - I wrote it up. It was beneficial and some posts got quite
          | a lot of views (at least in my book). I wrote about things I was learning about - as a tool to learn them better.
          | Now my blogging stopped with start of my master studies and a switch from Typescript to Scala in a professional setting. I went to learn about AI field to a specific professor 
          | as he looked promising (and indeed the experience lived up to my expectations).
          | There were enough projects on my hands and quite many courses and topics to learn. No interest to write blogs as writing, creativity and learning resources
          | were spent on university. Fast forward to present: I want to do those things again.
          |""".stripMargin
      },
      h2("Not a promise"),
      p {
        """
          | The question of what is better remains unanswered. In my opinion, If I want to write the best blog posts
          | they have to be interesting to me first. In other words - I write to myself.
          | Interesting and novelty strongly correlate for me. That is where the majority of future content will be.
          | Instead of sharing battle experience on specific topics, I will be journeying somewhere new. 
          | I expect that at some time this point of view of mine will change. That being said - I make no promises here.
          |""".stripMargin
      },
      FeedbackSection.render()
    )
  }
}
