package models

import java.util.Date

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Client(id: Long,
                  number: String,
                  public_key_modulus: String,
                  public_key_exponent: String,
                  validation_code: String,
                  validation_code_sent_at: Date,
                  is_active: Boolean,
                  created_at: Date)

case class NewClient(number: String, publicKeyModulus: String, publicKeyExponent: String)

object Client {

  val client = {
    get[Long]("id") ~ get[String]("number") ~ get[String]("public_key_modulus") ~ get[String]("public_key_exponent") ~
    get[String]("validation_code") ~ get[Date]("validation_code_sent_at") ~ get[Boolean]("is_active") ~ get[Date]("created_at") map {
      case id~number~public_key_modulus~public_key_exponent~validation_code~validation_code_sent_at~is_active~created_at =>
        Client(id, number, public_key_modulus, public_key_exponent, validation_code, validation_code_sent_at, is_active, created_at)
    }
  }

  def all(): List[Client] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM clients").as(client *)
  }

  def create(newClient: NewClient) = DB.withConnection{ implicit c =>
    val validation_code = "123456"
    SQL("INSERT INTO clients (number, public_key_modulus, public_key_exponent, validation_code, validation_code_sent_at, is_active) VALUES ({number}, {public_key_modulus}, {public_key_exponent}, {validation_code}, {validation_code_sent_at}, false)")
      .on('number -> newClient.number, 'public_key_modulus -> newClient.publicKeyModulus, 'public_key_exponent -> newClient.publicKeyExponent, 'validation_code -> validation_code, 'validation_code_sent_at -> new Date())
      .executeUpdate()
  }

}
