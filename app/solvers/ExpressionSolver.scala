package solvers

import scala.collection.mutable._
import scala.language.postfixOps
import javax.inject._

abstract class ExprItem(val priority: Int)
		
case class Literal(value: Double) extends ExprItem(0)
case class Plus() extends ExprItem(2)
case class Minus() extends ExprItem(2)
case class Times() extends ExprItem(3)
case class Divide() extends ExprItem(3)
case class OpenBracket() extends ExprItem(1)
case class CloseBracket() extends ExprItem(1)

//Solver
@Singleton
class ExpressionSolver() extends IExpressionSolver {
	type Expression = List[ExprItem]
 	                                                
	//Converting input string to infix expression of objects
  def parseInfixExpression(expression: String): Expression = {
	  
	  //Check if a char is a part of literal
    def isLiteral(c: Char): Boolean =
		  "[0-9.]".r findFirstIn c.toString match {
		  	case Some(x) => true
		  	case _ => false
		  }
    
	  //Tail-recursive parser method
	  def parseHelper(expr: List[Char], expAcc: Expression, literalAcc: List[Char]): Expression = {
	  	expr match {
	  		case List() =>
		  		if(literalAcc.isEmpty)
		  			expAcc
		  		else
		  			new Literal(literalAcc.reverse.mkString.toDouble) :: expAcc
		  	case c :: xc if c == ' ' =>
		  		if(literalAcc.isEmpty)
		  			parseHelper(xc, expAcc, literalAcc)
		  		else
		  			parseHelper(xc, new Literal(literalAcc.reverse.mkString.toDouble) :: expAcc, List())
		  	case c :: xc if isLiteral(c) => parseHelper(xc, expAcc, c :: literalAcc)
		  	case _ =>
		  		val exprItem = expr.head match {
		  		  case '+' => new Plus()
		  		  case '-' => new Minus()
		  		  case '*' => new Times()
		  		  case '/' => new Divide()
		  		  case '(' => new OpenBracket()
		  		  case ')' => new CloseBracket()
		  		  case _ => throw new IllegalArgumentException(s"Illegal character '${expr.head}'");
		  		}
		  		if(literalAcc.isEmpty)
		  			parseHelper(expr.tail, exprItem :: expAcc, literalAcc)
		  		else
		  			parseHelper(expr.tail, exprItem :: new Literal(literalAcc.reverse.mkString.toDouble) :: expAcc, List())
	  	}
	  }
	  
	  parseHelper(expression.toList, List(), List()) reverse
  }                                               
  
  //Converting infix expression to Reverse Polish Notation
	def infixToRpn(infixExpression: Expression): Expression = {
	  
	  //Pop operators until opening bracket is not reached
	  def popBracketOperators(stack: Stack[ExprItem]): Expression = {
	    
	    //Tail-recursive helper
	    def popHelper(innerStack: Stack[ExprItem], acc: Expression): Expression = {
			if(innerStack.isEmpty)
				throw new IllegalArgumentException("Closing brackets count is more than opening brackets count");
			else
				innerStack.pop match {
					case OpenBracket() => acc
					case exprItem => popHelper(innerStack, exprItem :: acc)
				}
   		}		
   		popHelper(stack, List())
		}		
	  
		//Tail-recursive RPN-expression builder
		def rpnBuilder(infixExpr: Expression, expAcc: Expression, stack: Stack[ExprItem]): Expression = {
			infixExpr match {
				case List() => stack.toList.reverse ::: expAcc
				case i :: ix => i match {
					case Literal(v) => rpnBuilder(ix, i :: expAcc, stack)
					case Plus() | Minus() | Times() | Divide() =>
						if(!stack.isEmpty && i.priority <= stack.top.priority)
							rpnBuilder(ix, stack.pop :: expAcc, stack.push(i))
						else
							rpnBuilder(ix, expAcc, stack.push(i))
	 				case OpenBracket() => rpnBuilder(ix, expAcc, stack.push(i))
	 				case CloseBracket() => rpnBuilder(ix, popBracketOperators(stack) ::: expAcc, stack)
				}
			}
		}		
		rpnBuilder(infixExpression, List(), new Stack[ExprItem]) reverse
	}                                      
	
  //Evaluating infix expression
  def rpnToNumber(rpnExpression: Expression): Double = {
    
    //Tail-recursive helper method
  	def rpnSolver(rpnExpr: Expression, stack: Stack[Double]): Double = {
  		rpnExpr match {
  			case List() => stack.pop
				case i :: ix => {
					i match {
						case Literal(v) => stack.push(v);
						case Plus() => stack.push(stack.pop + stack.pop);
						case Minus() => stack.push(-stack.pop + stack.pop);
						case Times() => stack.push(stack.pop * stack.pop);
						case Divide() => stack.push(1 / (stack.pop / stack.pop));
						case OpenBracket() => throw new IllegalArgumentException("Opening brackets count is more than closing brackets count");
					}
					rpnSolver(ix, stack)
				}
  		}
  	}  	
  	rpnSolver(rpnExpression, new Stack[Double])
  }   
  
  //Calculation method
  def calculate(expression: String): Double = {
    if(expression == null || expression.isEmpty)
      throw new IllegalArgumentException("Input string is empty")
    else
      rpnToNumber(infixToRpn(parseInfixExpression(expression)))
  } 
}