package $organization;format="normalize"$

import org.scalatest.{FlatSpec, Matchers}

class StandardTypesafeConfigSpec extends FlatSpec with Matchers {

  object TestConfig extends StandardTypesafeConfig

  "Loaded configuration" should "get properties from the application.conf" in {
    val result = TestConfig.config.getString("base.test.value")
    result shouldEqual "foobar"
  }

  it should "use the reference.conf for fallback" in {
    val result = TestConfig.config.getString("base.test.inherited")
    result shouldEqual "foo"
  }

  it should "use the properties from application.conf replacing those in reference.conf" in {
    val result = TestConfig.config.getString("base.test.overridden")
    result shouldEqual "new"
  }
}
