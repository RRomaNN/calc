package solvers

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import javax.inject.Singleton


class ExpressionSolverSpec extends PlaySpec with OneAppPerTest {
  
  //1. Simplest tests:
  "1.1 ExpressionSolver::parseInfixExpression method for simple expression ' 3+ 3' " must {
    "return List(Literal(3), Plus(), Literal(3))" in {
      val result = new ExpressionSolver().parseInfixExpression(" 3+ 3")

      result mustBe List(Literal(3), Plus(), Literal(3))
    }
  }
  
  "1.2 ExpressionSolver::infixToRpn method for simple expression 'List(Literal(3), Plus(), Literal(3))' " must {
    "return List(Literal(3), Literal(3), Plus())" in {
      val result = new ExpressionSolver().infixToRpn(List(Literal(3), Literal(3), Plus()))

      result mustBe List(Literal(3), Literal(3), Plus())
    }
  }
  
  "1.3 ExpressionSolver::rpnToNumber method for simple expression 'List(Literal(3), Literal(3), Plus())' " must {
    "return 6" in {
      val result = new ExpressionSolver().rpnToNumber(List(Literal(3), Literal(3), Plus()))

      result mustBe 6
    }
  }
  
  "1.4 ExpressionSolver::calculate method for simple expression ' 3+ 3     ' " must {
    "return 6" in {
      val result = new ExpressionSolver().calculate(" 3+ 3     ")

      result mustBe 6
    }
  }
  
  "2. ExpressionSolver::calculate method for expression from assignment '2 * (23/(3*3))- 23 * (2*3)' " must {
    "return -132.88888888888889" in {
      val result = new ExpressionSolver().calculate("2 * (23/(3*3))- 23 * (2*3)")

      result mustBe -132.88888888888889
    }
  }
  
  //3. Long expression with random spaces
  "3.1 ExpressionSolver::parseInfixExpression method for long expression '1+ 3* (1 + (2*3+ 3 *    (2 +  6 ) / 4) +8+ 3 *4 )/ 3   + 4*2' " must {
    """return List(Literal(1), Plus(), Literal(3), Times(), OpenBracket(), Literal(1), Plus(), OpenBracket(), Literal(2), 
      Times(), Literal(3), Plus(), Literal(3), Times(), OpenBracket(), Literal(2), Plus(), Literal(6), CloseBracket(), Divide(), Literal(4), 
      CloseBracket(), Plus(), Literal(8), Plus(), Literal(3), Times(), Literal(4), CloseBracket(), Divide(), Literal(3), Plus(), Literal(4),
      Times(), Literal(2))""" in {
        val result = new ExpressionSolver().parseInfixExpression("1+ 3* (1 + (2*3+ 3 *    (2 +  6 ) / 4) +8+ 3 *4 )/ 3   + 4*2")
  
        result mustBe List(Literal(1), Plus(), Literal(3), Times(), OpenBracket(), Literal(1), Plus(), OpenBracket(), Literal(2), Times(), 
          Literal(3), Plus(), Literal(3), Times(), OpenBracket(), Literal(2), Plus(), Literal(6), CloseBracket(), Divide(), Literal(4), 
          CloseBracket(), Plus(), Literal(8), Plus(), Literal(3), Times(), Literal(4), CloseBracket(), Divide(), Literal(3), Plus(), Literal(4),
          Times(), Literal(2))
    }
  }
  
  """3.2 ExpressionSolver::infixToRpn method for long expression 'List(Literal(1), Plus(), Literal(3), Times(), OpenBracket(), Literal(1), Plus(), 
    OpenBracket(), Literal(2), Times(), Literal(3), Plus(), Literal(3), Times(), OpenBracket(), Literal(2), Plus(), Literal(6), CloseBracket(), 
    Divide(), Literal(4), CloseBracket(), Plus(), Literal(8), Plus(), Literal(3), Times(), Literal(4), CloseBracket(), Divide(), Literal(3), Plus(), 
    Literal(4), Times(), Literal(2))' """ must {
      """return List(Literal(1), Literal(3), Literal(1), Literal(2), Literal(3), Times(), Literal(3), Literal(2), Literal(6), 
        Plus(), Times(), Literal(4), Divide(), Plus(), Plus(), Literal(8), Plus(), Literal(3), Literal(4), Times(), Plus(), 
        Times(), Literal(3), Divide(), Literal(4), Literal(2), Times(), Plus(), Plus())""" in {
        val result = new ExpressionSolver().infixToRpn(List(Literal(1), Plus(), Literal(3), Times(), OpenBracket(), Literal(1), Plus(), OpenBracket(), Literal(2), Times(), 
          Literal(3), Plus(), Literal(3), Times(), OpenBracket(), Literal(2), Plus(), Literal(6), CloseBracket(), Divide(), Literal(4), 
          CloseBracket(), Plus(), Literal(8), Plus(), Literal(3), Times(), Literal(4), CloseBracket(), Divide(), Literal(3), Plus(), Literal(4),
          Times(), Literal(2)))

        result mustBe List(Literal(1), Literal(3), Literal(1), Literal(2), Literal(3), Times(), Literal(3), Literal(2), Literal(6), 
          Plus(), Times(), Literal(4), Divide(), Plus(), Plus(), Literal(8), Plus(), Literal(3), Literal(4), Times(), Plus(), 
          Times(), Literal(3), Divide(), Literal(4), Literal(2), Times(), Plus(), Plus())
    }
  }
  
  """3.3 ExpressionSolver::rpnToNumber method for long expression 'List(Literal(1), Literal(3), Literal(1), Literal(2), Literal(3), Times(), Literal(3), 
    Literal(2), Literal(6), Plus(), Times(), Literal(4), Divide(), Plus(), Plus(), Literal(8), Plus(), Literal(3), Literal(4), Times(), Plus(), 
    Times(), Literal(3), Divide(), Literal(4), Literal(2), Times(), Plus(), Plus())' """ must {
    "return 42" in {
      val result = new ExpressionSolver().rpnToNumber(List(Literal(1), Literal(3), Literal(1), Literal(2), Literal(3), Times(), Literal(3), Literal(2), Literal(6), 
          Plus(), Times(), Literal(4), Divide(), Plus(), Plus(), Literal(8), Plus(), Literal(3), Literal(4), Times(), Plus(), 
          Times(), Literal(3), Divide(), Literal(4), Literal(2), Times(), Plus(), Plus()))

      result mustBe 42
    }
  }
  
  "3.4 ExpressionSolver::calculate method for long expression '1+ 3* (1 + (2*3+ 3 *    (2 +  6 ) / 4) +8+ 3 *4 )/ 3   + 4*2' " must {
    "return 42" in {
      val result = new ExpressionSolver().calculate("1+ 3* (1 + (2*3+ 3 *    (2 +  6 ) / 4) +8+ 3 *4 )/ 3   + 4*2")

      result mustBe 42
    }
  }
  
  "4. ExpressionSolver::calculate method for empty expression " must {
    "throw Illegal argument exception 'Input string is empty'" in {
      var errMessage = ""
      try {
        new ExpressionSolver().calculate("")
      } catch {
        case e: IllegalArgumentException => errMessage = e.getMessage
      }
          
      errMessage mustBe "Input string is empty"
    }
  }
  
  "5. ExpressionSolver::calculate method for null expression " must {
    "throw Illegal argument exception 'Input string is empty'" in {    
      var errMessage = ""
      try {
        new ExpressionSolver().calculate(null)
      } catch {
        case e: IllegalArgumentException => errMessage = e.getMessage
      }
     
      errMessage mustBe "Input string is empty"
    }
  }
  
  "6. ExpressionSolver::calculate method for expression with illegal chracter '1- 3* g1 + (2*3* 3 *    (2 +  6 ) / 4)' " must {
    "throw Illegal argument exception 'Illegal character 'g'" in {
      var errMessage = ""
      try {
        new ExpressionSolver().calculate("1- 3* g1 + (2*3* 3 *    (2 +  6 ) / 4)")
      } catch {
        case e: IllegalArgumentException => errMessage = e.getMessage
      }
     
      errMessage mustBe "Illegal character 'g'"
    }
  }
  
  "7.1 ExpressionSolver::calculate method for expression with unbalanced brakets (2 closing, 3 opening) '1- 3* 1 + (2*3* 3 *    (2 +  6 ) / 4) + 2)' " must {
    "throw Illegal argument exception 'Closing brackets count is more than opening brackets count" in {
      var errMessage = ""
      try {
        new ExpressionSolver().calculate("1- 3* 1 + (2*3* 3 *    (2 +  6 ) / 4) + 2)")
      } catch {
        case e: IllegalArgumentException => errMessage = e.getMessage
      }
     
      errMessage mustBe "Closing brackets count is more than opening brackets count"
    }
  }
  
  "7.2 ExpressionSolver::calculate method for expression with unbalanced brakets (3 opening, 2 closing) '1- (3* 1 + (2*3* 3 *    (2 +  6 ) / 4 + 2)' " must {
    "throw Illegal argument exception 'Opening brackets count is more than closing brackets count" in {
      var errMessage = ""
      try {
        new ExpressionSolver().calculate("1- (3* 1 + (2*3* 3 *    (2 +  6 ) / 4 + 2)")
      } catch {
        case e: IllegalArgumentException => errMessage = e.getMessage
      }
      
      errMessage mustBe "Opening brackets count is more than closing brackets count"
    }
  }
  
  "8. ExpressionSolver::calculate method for expression with empty brakets ' 1- () 2 + 3* (1 + 2)' " must {
    "return 8" in {
      val result = new ExpressionSolver().calculate(" 1- () 2 + 3* (1 + 2)")

      result mustBe 8
    }
  }
  
  "9. ExpressionSolver::calculate method for expression with two consecutive operators ' 1- + 3* (1 + 2)' " must {
    "throw exception" in {
      var isFailed = false
      try {
        new ExpressionSolver().calculate(" 1- + 3* (1 + 2)")
      } catch {
        case e: Exception => isFailed = true
      }
      
      isFailed mustBe true
    }
  }
  
  "10. ExpressionSolver::calculate method for expression with floating point numbers ' 1.2 + 3* (1 + 2)' " must {
    "return 10.2" in {
      val result = new ExpressionSolver().calculate(" 1.2 + 3* (1 + 2)")

      result mustBe 10.2
    }
  }
}
