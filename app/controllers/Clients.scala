package controllers

import models.{NewClient, Client}
import play.api.data._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.data.Forms._

import scala.util.Try

object Clients extends Controller{

  implicit val userFormatter = Json.format[Client]
  val form = Form(
    mapping(
      "number" -> nonEmptyText,
      "public_key_modulus" -> nonEmptyText.verifying(x => Try(BigInt(x)).isSuccess),
      "public_key_exponent" -> nonEmptyText
    )(NewClient.apply)(NewClient.unapply)
  )

  def index = Action {
    val clients = Client.all
    Ok(Json.toJson(clients))
  }

  def create = Action { implicit request =>
    val client = form.bindFromRequest()

    client.fold(
      error => BadRequest(error.errorsAsJson),
      newClient => {
        Client.create(newClient)
        Ok("OK")
      }
    )
  }

  def show(number: String) = Action {
    Ok("OK")
  }

}
