package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok("{\"api_version\":\"0.1\"}").as("application/json")
  }

}