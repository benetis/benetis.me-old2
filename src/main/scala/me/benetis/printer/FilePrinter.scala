package me.benetis.printer

import java.io.{BufferedWriter, File, FileWriter}
import me.benetis.compiler.Compiler.CompiledPage
import zio.{Task, UIO, ZIO}

object FilePrinter {

  def outputPage(compiledPage: CompiledPage): ZIO[Any, Throwable, Unit] = {

    val projectDir = System.getProperty("user.dir")
    openFile(
      s"$projectDir${File.separator}output${File.separator}articles${File.separator}${compiledPage.name}.html")
      .bracket(bw => UIO(bw.close())) { bw =>
        Task(bw.write(compiledPage.pageContent))
      }
  }

  def openFile(path: String): Task[BufferedWriter] = Task {
    println(path)
    val file = new File(path)
    new BufferedWriter(new FileWriter(file))
  }

}
