package me.benetis.posts

import me.benetis.Content
import me.benetis.model.{GeneralSoftware, Meta, Post, PostParams, Slug}
import me.benetis.partials.FeedbackSection
import org.joda.time.DateTime
import scalatags.Text.all._

object SoftwareEngineerTwoHours extends Post {

  override val params: PostParams =
    PostParams("What software engineer needs to be able to do in 1-2 hours?",
               Slug("2-hours-of-engineering"),
               Vector(GeneralSoftware),
               DateTime.parse("2021-12-16"))

  def render(): Content = {
    div(
      h2("Introduction"),
      p {
        """
          |What can software engineer do in 2 hours? Can you make MVP and deploy it to production in that time?
          |What about a senior software engineer?
          |Recently I applied for a developer position. I did it after a personal suggestion from that company's engineer.
          |I received a fairly simple but not easy task to do and I could do it in any programming language I wanted.
          |I will not mention company's name or any of the people involved. But, my experience could make an interesting read.
          |Also the task specifics are changed a little bit for anonymity sake.
          |""".stripMargin
      },
      h3(""" Rules of the "homework assignment" """),
      p {
        """
          |Assignment consists of two tasks. First task is about coding. Second task is about packaging the same application in docker container and deploying it on kubernetes.
          |Time constraint to do these tasks is 1-2 hours and a there is explicit mention not to try to make everything ideal.
          |If you haven't encountered time constraints on interview tasks - usually its to prevent candidates from polishing things too much. It leads to wasting candidate's time on non-important things.  
          |Now seeing this tight time constraint I knew immediately that I will have to make huge tradeoffs.
          |""".stripMargin
      },
      h3("Specifics"),
      p {
        """
          |First task is to make a Http server with one endpoint. As I mentioned before, I will change endpoint functionality to a similar one.
          |Endpoint task is to count all multiples of 3 or 5 under given `n`. (Taken from first task of Project Euler [1])
          |Example: localhost:9999/sum_multiples_3_5?n=10
          |Http endpoint should answer 23, because if we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. 3+5+6+9=23
          |
          |Now task mentions that Http server should handle a good RPM and prevent malicious attempts which would bring service down.
          |The higher the N is calculated - the more points you get for the task. Tests are suggested, but left optional.
          |
          |Second task requires the application to be packaged with Dockerfile. Also there needs to be kubernetes YAML file to spin up this image on kube.
          |It also requests an ability to scale up or down, automatic scaling is optional. And it requires to self-heal in case the service goes down.
          |""".split("\\|").map(div(_, `class` := "br_div"))
      },
      h3 { "Considerations" },
      p("There are a lot of design decisions to consider and evaluate possible architecture choices:"),
      ul(
        li(
          "Since this endpoint is computationally expensive, it is almost a rule to cache responses for given n"),
        li(
          "Computations can be memoized, for example if we count n=10 and later count n=13, do we count again from n=1 or can we use n=10 cache"),
        li("Should cache be shareable between kubernetes instances?"),
        li("Can memoized calculations be shared between kubernetes instances?"),
        li(
          "How long the timeout should be for the user? Does application continue computations so users in the future can request the number even though it timeouted before?"),
        li("What algorithm to pick so resources uses are optimal?"),
        li(
          "Should http server threads and computational threads run on separate thread pools?"),
        li(
          "Should there be a hardcoded max n? What if better server hardware can handle bigger n easily?"),
        li(
          "What kind of data types response needs? Does big fn(very_big_n) response fit in Int, BigInt?"),
        li(
          "What if users maliciously request expensive computation in parallel? (DOS attack)"),
        li("How can algorithm effectively use memory available on the server?"),
        li(
          "How do we benchmark the endpoint if we use cache? Can we use static requests?"),
      ),
      p {
        """
        |On top of these design decisions there needs to be validation for query params. Best would be if application
        |guides the user to use correct params with correct types. For example: localhost:9999/sum_multiples_3_5?y=cat should provide informative message.
          |
        |Also since I chose to write in Scala, it makes most sense for hiring managers to expect me to write functional Scala.
          |
        |Of course, all this thinking falls under given time constraint of 1-2 hours.
        """.stripMargin
      },
      h3 { "Action list" },
      p {
        """While there are many things to consider, it makes most sense to rank and execute the 
          |required non-optional parts of the tasks. Fulfilling requirements under time constraint is what defines success here.
          |
          |Sensible mini-tasks list I chose to do in order:
        """.stripMargin
      },
      ul(
        li(
          "Start writing README file to capture some of considerations and decisions that went into solution"),
        li("Code function to calculate sum_multiples_of_3_5"),
        li("Rewrite the function to be tail recursive"),
        li("Scaffold http server with the endpoint"),
        li("Validation for the endpoint"),
        li("Connect function and http server to make a naive solution"),
        li(
          "Cache with a Ref[Map[Int, BigInt]], where cache is a Map and Ref is there to deal with concurrency"),
        li("Apache benchmark with static N to test the throughput"),
        li(
          """Wrap sum_of_multiples function in effect, add a timeout of constant 30 seconds. This means if it exceeds that time, user will receive error "Timeout of 30 secs exceeded" with Http code 400"""),
        li("Benchmark with higher number to test the timeout"),
        li(
          "Capture benchmark results into README file, write how big of N I tested application with"),
        li(
          "I chose not to write tests as they are optional and there is not enough time for them now. I deemed benchmarks more important as task specifically mentions higher points for better performance"),
      ),
      p {
        """
        |Http server works, in the end I managed to make it around 100 LoC. Its possible to run it and curl to get the result as per task requirements.
        |Second part is about packaging in docker and spinning it up on k8s.
        |Sensible mini-tasks lists I chose to do:
        """.stripMargin
      },
      ul(
        li(
          """Since task mentions self contained image with Dockerfile, I could not use "industry standard" sbt-native-package, I bootstrapped dockerfile with scala-sbt base image"""),
        ul(
          li("Set SBT_OPTS for memory with ENV in the file"),
          li("Copy whole project into docker image"),
          li("Run sbt assembly"),
          li("Change base image to JDK"),
          li("Expose http server port"),
          li("Run the jar file containing http-server")
        ),
        li("Test image locally with docker run -p $PORT and see if it works"),
        li("Kubernetes part of deployment and service"),
        ul(
          li("Deployment of the image with livelinessProbe on httpGet /sum_multiples_3_5?n=10 to meet self-heal requirement"),
          li("Service to forward port 80 to container's exposed port"),
        ),
        li(
          "Test kubernetes deployment on minikube, benchmark, make pod go down and see if it heals"),
        li(
          "Benchmark kubernetes deployment and compare to local sbt deployment, see if results differ"),
      ),
      p("I did not do automatic pod scaling as I never used it before. There was no time to research and test, but since it was optional - I ignored it"),
      h3("Feedback from company and conclusion"),
      p("I get an email from company saying: I did not write tests, did not implement autoscaling (hpa) and timeout requests should not return bad request status code. Thus, I am rejected and I can apply again in X months."),
      p("No mention of how well my application performed in comparison or if the design choices made were correct"),
      p("WTF? My choice with status code makes sense to me as timeout should not be 200. As for the optional things, I chose specifically to prioritize non-optional stuff first due this insane time constraint. Who in the world could do all this in 1-2 hours?"),
      p("Tests you can always find the ones I have on github or just ask me separately to provide some code examples with tests. Or even give me more time on this task (beyond 2hrs) to write them. I specifically mentioned in the notes that I chose benchmarking priority over testing due not enough time having for both."),
      p("In retrospect I am surprised by their response and expectations, its sad I wasted my time, but I did have fun writing this post."),
      p("Thanks for reading and have a nice day!")
    )
  }
}
