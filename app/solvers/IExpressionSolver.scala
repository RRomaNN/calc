package solvers

import com.google.inject.ImplementedBy

//Solver interface
@ImplementedBy(classOf[ExpressionSolver])
trait IExpressionSolver {
   def calculate(expression: String): Double
}