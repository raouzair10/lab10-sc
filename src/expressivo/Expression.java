/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
	// Datatype definition:
    //   Expression = Value(num:double)
    //                + Variable(id:String)
    //                + Addition(left:Expression, right:Expression)
    //                + Multiplication(left:Expression, right:Expression)
    
	 /** Creates an empty expression such that Expression.parse("0").equals(emptyExpression())  */
    public static Expression emptyExpression() {
        return new Value(0.0);
    }

    /**
     * Appends an expression at the end of this with an addition
     * 
     * If e equals Expression.emptyExpression(), correct to 5 decimal places, 
     * the empty expression is returned;
     * If e equals this, an expression equivalent to
     *      this * 2 is returned
     *      
     * @param e a non-null non-empty string of a valid expression
     *          syntax
     * @return a simplified expression equivalent to:
     *           this + e
     *      this and e are not modified
     */
    public Expression addExpr(Expression e);

    /**
     * Appends an expression at the end of this with a multiplication
     * 
     * If e equals Expression.emptyExpression(), correct to 5 decimal places, 
     * the empty expression is returned;
     * If e equals Expression.parse("1"), correct to 5 decimal places, this
     * expression is returned
     * The product of any other expression except the two above is not simplified,
     * the resulting expression being equivalent to:
     *      (this)*(e)
     * Note: This is not the case when parsing, where an expression is simplified
     * as much as possible
     * 
     * @param e a non-null non-empty string of a valid expression
     *          syntax
     * @return a new expression equivalent to:
     *           this * e
     *      The returned expression is NOT simplified
     *      this and e are not modified
     */
    public Expression multiplyExpr(Expression e);

    /**
     * Appends a variable at the start of this expression with an addition
     * 
     * @param variable non-null non-empty case-sensitive string of letters, a-zA-Z
     * @return a new expression as a result of inserting a variable at the start
     *         of this expression with an addition.
     *         The expression is not simplified
     *                
     */
    public Expression addVariable(String variable);

    /**
     * Appends a variable as a multiplicative factor to start of this expression
     * 
     * @param variable non-null non-empty case-sensitive string of letters, a-zA-Z
     * @return the product expression of this and variable, variable being at
     *         the head of the expression. The expression is not simplified
     */
    public Expression multiplyVariable(String variable);
   
    /**
     * Returns a string representation of this expression
     * 
     * The string returned is such that:
     *   - for additions, exactly one space exists between
     *     operand and the operator:
     *          operand + operand 
     *   - for multiplications, no space exists between operands
     *     and the operator, and operands are inside parentheses:
     *          (factor)*(factor)
     *     Factors of products are grouped from left to right by default:
     *          x*x*x -> ((x)*(x))*(x)
     * Numbers in the string are truncated and correct to 5 decimal places
     * 
     * @return a parsable representation of this expression, such that
     *         for all e:Expression, e.equals(Expression.parse(e.toString())).
     */  
    @Override public String toString();

    /**
     * Checks if an object is equal to this addition expression
     * Two expressions are equal if and only if: 
     *   - The expressions contain the same variables, numbers, and operators;
     *   - those variables, numbers, and operators are in the same order, read left-to-right;
     *   - and they are grouped in the same way.
     * Two sums are equal if having different groupings with 
     * the same mathematical meaning. For example, 
     *     (3 + 4) + 5 and 3 + (4 + 5) are equal.
     * However, two products are NOT equal if they have different groupings regardless
     * of mathematical meaning. For example:
     *     x*(2*y) is not equal to (x*2)*y 
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
}
