package services

import javax.inject._
import java.util.Base64
import java.nio.charset.StandardCharsets

import solvers._

//Calculator service   
@Singleton
class CalculatorService @Inject() (solver: IExpressionSolver) extends ICalculatorService {
  def calculate(expression: CalculationExpression): CalculationResult = {
    try {
      //Decoding input string
      val plainExpression = new String(Base64.getDecoder.decode(expression.expression), StandardCharsets.UTF_8)        
      //Solving expression
      CalculationResult(solver.calculate(plainExpression), 0, null)
    } catch {
      case e: Exception => {
        CalculationResult(0, 1, e.getMessage)
      }
    }
  }
}
