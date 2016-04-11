import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "ONOServer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaJpa,
    "org.hibernate" % "hibernate-entitymanager" % "4.1.9.Final",
    "mysql" % "mysql-connector-java" % "5.1.22"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

  javaOptions ++= Seq("-Xms2048M", "-Xmx2048M", "-XX:MaxPermSize=2048M")

}
