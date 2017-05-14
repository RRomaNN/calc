package services

import com.google.inject.ImplementedBy

//Input expression (base-64 encoded UTF-8 string)
case class CalculationExpression(val expression: String)
//Calculation result with error status 
case class CalculationResult(val number: Double, override val errorCode: Int, override val errorMessage: String) extends BaseResult(errorCode, errorMessage)

//Interface of calculator service
@ImplementedBy(classOf[CalculatorService])
trait ICalculatorService {
   def calculate(expression: CalculationExpression): CalculationResult
}