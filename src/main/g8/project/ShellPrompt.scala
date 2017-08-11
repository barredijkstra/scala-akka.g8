import sbt._
import BuildUtil._

object ShellPrompt {
  val buildShellPrompt: (State) => String = state => {
    val project = Project.extract(state).currentRef.project
    s"[$"$"$project:$"$"${currentBranch.getOrElse("local")}]> "
  }
}