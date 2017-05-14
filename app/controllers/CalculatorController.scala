package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import java.util.Base64
import java.nio.charset.StandardCharsets
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import services._
import services.ICalculatorService
import services.CalculationExpression

//Controller for expression calculation
@Singleton
class CalculatorController @Inject() (calcSvc: ICalculatorService) extends Controller {
  
  //Expression calculation action
  def calculus(query: String) = Action.async { 
    //Async call of solver
    val calcFuture = scala.concurrent.Future {  
      calcSvc.calculate(CalculationExpression(query))     
    }
    //Processing result: return 200 and result on success or 500 and error message on fail 
    calcFuture.map(res => 
      if(res.errorCode == 0)
        Ok(JsObject(Seq(
          "error" -> JsBoolean(false),
          "result" -> JsNumber(res.number))))
      else
        InternalServerError(JsObject(Seq(
          "error" -> JsBoolean(true),        
          "message" -> JsString(res.errorMessage)))))
  }
}
