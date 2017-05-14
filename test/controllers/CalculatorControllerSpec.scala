package controllers

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

import services._

class CalculatorControllerMockSpec extends PlaySpec with MockitoSugar {

  "1. CalculatorController GET /calculus?query=MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=" should {

    "return json data {\"error\":false,\"result\":-132.88888888888889} with status 200" in {
      
      val mockICalculatorService = mock[ICalculatorService]
        when(mockICalculatorService.calculate(CalculationExpression("MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk="))) thenReturn CalculationResult(-132.88888888888889, 0, null)
      
      val calculationController = new CalculatorController(mockICalculatorService)
      val calculation = calculationController.calculus("MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=").apply(FakeRequest())

      status(calculation) mustBe OK
      contentType(calculation) mustBe Some("application/json")
      contentAsString(calculation) must include ("{\"error\":false,\"result\":-132.88888888888889}")
    } 
  }
  
  "2. CalculatorController GET /calculus?query=KDEgKyAxKSAqICg4ICsgMmcp" should {

    "return json data {\"error\":true,\"message\":\"Illegal character 'g'\"} with status 500" in {
      
      val mockICalculatorService = mock[ICalculatorService]
        when(mockICalculatorService.calculate(CalculationExpression("KDEgKyAxKSAqICg4ICsgMmcp"))) thenReturn CalculationResult(0, 1, "Illegal character 'g'")
      
      val calculationController = new CalculatorController(mockICalculatorService)
      val calculation = calculationController.calculus("KDEgKyAxKSAqICg4ICsgMmcp").apply(FakeRequest())

      status(calculation) mustBe INTERNAL_SERVER_ERROR
      contentType(calculation) mustBe Some("application/json")
      contentAsString(calculation) must include ("{\"error\":true,\"message\":\"Illegal character 'g'\"}")
    }
  }
}
  
class CalculatorControllerStdSpec extends PlaySpec with OneAppPerTest {
  "3. CalculatorController GET /calculus?query=MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=" should {

    "get correct response from the application" in {
      val controller = app.injector.instanceOf[CalculatorController]
      val calculation = controller.calculus("MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=").apply(FakeRequest())

      status(calculation) mustBe OK
      contentType(calculation) mustBe Some("application/json")
      contentAsString(calculation) must include ("{\"error\":false,\"result\":-132.88888888888889}")
    }

    "get correct response from the router" in {
      val request = FakeRequest(GET, "/calculus?query=MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=").withHeaders("Host" -> "localhost")
      val calculation = route(app, request).get

      status(calculation) mustBe OK
      contentType(calculation) mustBe Some("application/json")
      contentAsString(calculation) must include ("{\"error\":false,\"result\":-132.88888888888889}")
    }
  }   
}
    
