import sbt._

object BuildUtil {
  object NullProcessLogger extends ProcessLogger {
    def info(s: => String): Unit = {}
    def error(s: => String): Unit = {}
    def buffer[T](f: => T): T = f
  }

  def currentBranch: Option[String] =
    ("git symbolic-ref --short HEAD" lines_! NullProcessLogger).headOption


  def lastCommitSha: Option[String] =
    ("git rev-parse --short HEAD" lines_! NullProcessLogger).headOption
}