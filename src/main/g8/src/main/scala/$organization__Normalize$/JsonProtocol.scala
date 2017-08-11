package $organization;format="normalize"$

import java.net.URL
import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import enumeratum.{Enum, EnumEntry}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import spray.json._

import scala.util.{Failure, Success, Try}

trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {

  def jsonFlatFormat[P, T <: Product](construct: P => T)(implicit jw: JsonWriter[P], jr: JsonReader[P]): JsonFormat[T] =
    new JsonFormat[T] {
      override def read(json: JsValue): T = construct(jr.read(json))

      override def write(obj: T): JsValue = jw.write(obj.productElement(0).asInstanceOf[P])
    }

  def jsonEnumFormat[T <: EnumEntry](enumCompanion: Enum[T]): JsonFormat[T] =
    new JsonFormat[T] {
      override def read(json: JsValue): T = json match {
        case JsString(name) =>
          enumCompanion
            .withNameInsensitiveOption(name)
            .getOrElse(deserializationError(s"$"$"$name should be one of ($"$"${enumCompanion.values.map(_.entryName).mkString(", ")})"))
        case _ =>
          deserializationError(s"$"$"${json.toString()} should be a string of value ($"$"${enumCompanion.values.map(_.entryName).mkString(", ")})")
      }

      override def write(obj: T): JsValue = JsString(obj.entryName)
    }

  implicit val urlJsonFormat = new JsonFormat[URL] {
    override def read(json: JsValue): URL = json match {
      case JsString(url) => Try(new URL(url)).getOrElse(deserializationError("Invalid URL format"))
      case _ => deserializationError("URL should be string")
    }

    override def write(obj: URL): JsValue = JsString(obj.toString)
  }

  implicit val uuidFormat = new JsonFormat[UUID] {
    override def write(obj: UUID): JsValue = JsString(obj.toString)

    override def read(json: JsValue): UUID = json match {
      case JsString(uuid) => Try(UUID.fromString(uuid)).getOrElse(deserializationError("Expected UUID format"))
      case _ => deserializationError("Expected UUID format")
    }
  }

  def jodaDateTimeFormat(formatter: DateTimeFormatter) = new JsonFormat[DateTime] {
    override def write(obj: DateTime): JsValue = JsString(formatter.print(obj))

    override def read(json: JsValue): DateTime = json match {
      case JsString(dateTime) => Try(formatter.parseDateTime(dateTime)) match {
        case Success(parsedDateTime) => parsedDateTime
        case Failure(exception) => deserializationError(s"Unable to parse $"$"$dateTime as DateTime object", exception)
      }
      case _ => deserializationError(s"Unable to parse $"$"$json as DateTime")
    }
  }
}
