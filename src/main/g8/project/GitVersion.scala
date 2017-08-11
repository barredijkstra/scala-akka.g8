import BuildUtil._

object GitVersion {
  def applicationVersion: String =
    currentBranch match {
      case Some("master") => lastCommitSha.getOrElse("local")
      case Some(branchName) => s"$"$"$branchName-SNAPSHOT"
      case None => "local"
    }

  def dockerBranch: String =
    currentBranch.map(_.split('/')).flatMap(_.lastOption).getOrElse("local").toLowerCase

  def dockerVersion: String =
    lastCommitSha.getOrElse("local").toLowerCase

}