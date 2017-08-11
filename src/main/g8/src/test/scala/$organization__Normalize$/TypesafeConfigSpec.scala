package $organization;format="normalize"$

import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration.Duration

class TypesafeConfigSpec extends FlatSpec with Matchers {

  "A configuration property" should "be extractable as option" in {
    val conf = new TypesafeConfig {
      override val config = ConfigFactory.parseString(
        """foo {
          |  bar = "foobar"
          |  number = 12
          |}""".stripMargin)

      def foobar = asOption(_.getString("foo.bar"))

      def number = asOption(_.getInt("foo.number"))

      def invalid = asOption(_.getString("non-existing"))
    }

    conf.foobar shouldEqual Some("foobar")
    conf.number shouldEqual Some(12)
    conf.invalid shouldEqual None
  }

  it should "be implicitly converted from a Java to Scala duration" in {
    val pattern = "10s"
    val conf = new TypesafeConfig {
      override val config = ConfigFactory.parseString(s"timeout = $"$"$pattern")

      def timeout: Duration = config.getDuration("timeout")
    }

    conf.timeout shouldEqual Duration(pattern)
  }

  it should "be extractable as a map" in {
    val conf = new TypesafeConfig {
      override val config = ConfigFactory.parseString("""values = [ { foo: "bar" }, { bar: "foo" }, { times: 2 } ]""")

      def values = getMap("values")
    }
    val expected = Map("foo" -> "bar", "bar" -> "foo", "times" -> 2)

    conf.values shouldEqual expected
  }
}
