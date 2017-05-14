package services

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import javax.inject.Singleton

import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

import solvers._

class CalculatorServiceSpec extends PlaySpec with MockitoSugar {

  "1. ICalculatorService::calculate" should {
    "return CalculationResult(-132.88888888888889, 0, null) if the expression is MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=" in {
      
      val mockIExpressionSolver = mock[IExpressionSolver]
        when(mockIExpressionSolver.calculate("2 * (23/(3*3))- 23 * (2*3)")) thenReturn -132.88888888888889

      val iCalculatorService = new CalculatorService(mockIExpressionSolver)  

      val result = iCalculatorService.calculate(CalculationExpression("MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk="))
      result mustBe CalculationResult(-132.88888888888889, 0, null)
    }
  }
  
  "2. ICalculatorService::calculate" should {
    "return CalculationResult(0, 1, 'Opening brackets count is more than closing brackets count') if the expression is KDEgKyAx" in {
      
      val mockIExpressionSolver = mock[IExpressionSolver]
        when(mockIExpressionSolver.calculate("(1 + 1")) thenThrow new IllegalArgumentException("Opening brackets count is more than closing brackets count")

      val iCalculatorService = new CalculatorService(mockIExpressionSolver)  

      val result = iCalculatorService.calculate(CalculationExpression("KDEgKyAx"))
      result mustBe CalculationResult(0, 1, "Opening brackets count is more than closing brackets count")
    }
  }
  
  "3. ICalculatorService::calculate" should {
    "return CalculationResult(0, 1, 'Illegal base64 character 2d') if the expression is KDEgKyAx-=====" in {
      
      val mockIExpressionSolver = mock[IExpressionSolver]
      val iCalculatorService = new CalculatorService(mockIExpressionSolver)  

      val result = iCalculatorService.calculate(CalculationExpression("KDEgKyAx-====="))
      result mustBe CalculationResult(0, 1, "Illegal base64 character 2d")
    }
  }
  
  "4. ICalculatorService::calculate" should {
    "return CalculationResult(0, 1, 'Input string is empty') if the expression is empty" in {
      
      val mockIExpressionSolver = mock[IExpressionSolver]
      val iCalculatorService = new CalculatorService(mockIExpressionSolver)  
         when(mockIExpressionSolver.calculate("")) thenThrow new IllegalArgumentException("Input string is empty")
      
      val result = iCalculatorService.calculate(CalculationExpression(""))
      result mustBe CalculationResult(0, 1, "Input string is empty")
    }
  }
}