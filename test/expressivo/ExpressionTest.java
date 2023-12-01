/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
/**
 * Tests for the concrete variants of Expression
 */
public class ExpressionTest {
    // Testing Strategy:

    //
    //  Partition for addExpr: Expression x Expression -> Expression
    //    an empty expression, 
    //    contains multiple variables,
    //    input expression as a subset of this expression,
    //    input expression equals this
    //  
    //  Partition for multiplyExpr: Expression x Expression -> Expression
    //    an empty expression,
    //    expression as the value 1,
    //    contains multiple variables
    //    input expression as a subset of this expression
    //    input expression equals this
    //  
    //  Partition for addVariable: Expression x String -> Expression
    //    this: empty expression, 
    //          doesn't contain input variable,
    //          contains input variable
    //
    //  Partition for multiplyVariable: Expression x String -> Expression
    //    this: empty expression,
    //          expression as a single value 1,
    //          doesn't contain input variable,
    //          contains input variable
    //
	//  Partition for toString: Expression -> String
    //    this: empty expression,
    //          contains multiple variables and values
    //  Partition for equals: Expression x Expression -> boolean
    //    reflexive, symmetric and transitive equality
    //    include tests for numbers correct to 5 decimal places
    //    
    //  Partition for hashCode: Expression -> int
    //    include equal expressions having equal values equal correct
    //    to 5 decimal places
    
    
    final Expression empty = Expression.emptyExpression();
    final Expression one = Expression.parse("1.000009");
    final Expression expr = Expression.parse("x*y + x + 0.5");
    final Expression expr1 = Expression.parse("x*y*0.5");
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
 // Tests for toString()
    @Test
    // covers empty expression
    public void testToString_Empty() {
        Expression empty = Expression.parse("0.000009");
        
       assertEquals("Expected empty string to be 0",
               "0", empty.toString());
    }
    @Test
    // covers contains multiple variables and values
    public void testToString() {
        String actual1 = expr.toString();
        String actual2 = expr1.toString();
        String actual3 = Expression.parse("x*(x*y)").toString();
        String expected1 = "(x)*(y) + x + 0.5";
        String expected2 = "((x)*(y))*(0.5)";
        String expected3 = "(x)*((x)*(y))";
        
        assertEquals("Expected correct spacing and grouping", 
                expected1, actual1);
        assertEquals("Expected correct default grouping", 
                expected2, actual2);
        assertEquals("Expected correct grouping", 
                expected3, actual3);
    }
    
    // Tests for equals()
    @Test
    // covers reflexive equality
    public void testEquals_Reflexive() {
        assertEquals("Expected expression equal to itself",
                empty, empty);
        assertEquals("Expected expression equal to itself",
                one, one);
        assertEquals("Expected expression equal to itself",
                expr, expr);
        assertEquals("Expected expression equal to itself",
                expr1, expr1);
    }
    @Test
    // covers symmetric equality
    public void testEquals_Symmetric() {
        Expression exprEqual = Expression.parse("x*y + x + 0.500009");
        Expression expr1Equal = Expression.parse("x*y*0.500002");
        
        if (expr.equals(exprEqual)) {
            assertTrue("Expected symmetric equality", exprEqual.equals(expr));
        } else {
            fail("Expected expressions to be equal");
        }        
        if (expr1.equals(expr1Equal)) {
            assertTrue("Expected symmetric equality", expr1Equal.equals(expr1));
        } else {
            fail("Expected expressions to be equal");
        }
    }
    @Test
    // covers transitive equality
    public void testEquals_Transitive() {
        Expression exprA = expr;
        Expression exprB = Expression.parse("x*y + x + 0.500009");
        Expression exprC = Expression.parse("x*y + x + 0.500002");
        Expression expr1A = expr1;
        Expression expr1B = Expression.parse("x*y*0.500002");
        Expression expr1C = Expression.parse("x*y*0.500009");
        
        if (exprA.equals(exprC) && exprA.equals(exprB)) {
            assertTrue("Expected transitive equality", exprC.equals(exprB));
            assertTrue("Expected transitive equality", exprB.equals(exprC));
        } else {
            fail("Expected expressions to be equal");
        }  
        if (expr1A.equals(expr1C) && expr1A.equals(expr1B)) {
            assertTrue("Expected transitive equality", expr1C.equals(expr1B));
            assertTrue("Expected transitive equality", expr1B.equals(expr1C));
        } else {
            fail("Expected expressions to be equal");
        } 
    }
    
    // Test for hashCode()
    @Test
    public void testHashCode() {
        Expression exprA = expr;
        Expression exprB = Expression.parse("x*y + x + 0.500009");
        Expression exprC = Expression.parse("x*y + x + 0.500002");
        Expression expr1A = expr1;
        Expression expr1B = Expression.parse("x*y*0.500002");
        Expression expr1C = Expression.parse("x*y*0.500009");
        
        assertEquals("Expected equal objects to have the same hash code",
                exprA.hashCode() == exprB.hashCode(), 
                exprB.hashCode() == exprC.hashCode());
        assertEquals("Expected equal objects to have the same hash code",
                expr1A.hashCode() == expr1B.hashCode(), 
                expr1B.hashCode() == expr1C.hashCode());
    }
}
    // Tests for addExpr()
    @Test
    // covers empty expression
    public void testAddExpr_Empty() {
        Expression actual1 = empty.addExpr(expr);
        Expression actual2 = expr1.addExpr(empty);
        
        assertEquals("Expected expression + 0 = expression",
                expr, actual1);
        assertEquals("Expected expression + 0 = expression",
                expr1, actual2);
    }
    @Test
    // covers 
    public void testAddExpr_Subset() {
        Expression subset = Expression.parse("x + 0.5");
        Expression actual = expr.addExpr(subset);
        Expression expected = Expression.parse(expr.toString() + "+" + subset.toString());
        
        assertEquals("Expected expression added at the end",
                expected, actual);
    }
    @Test
    // covers input equals this
    //        input as a subset
    public void testAddExpr_EqualsThis() {
        Expression actual = expr.addExpr(expr);
        Expression expected = Expression.parse("(x*y + x)*2 + 1");
        
        assertEquals("Expected simplified expression",
                expected, actual);
    }
    
    // Tests for multiplyExpr()
    @Test
    // covers empty expression
    public void testMultiplyExpr_Empty() {
        Expression actual1 = empty.multiplyExpr(expr);
        Expression actual2 = expr.multiplyExpr(empty);
        
        assertEquals("Expected 0*expression = 0",
                empty, actual1);
        assertEquals("Expected expression*0 = 0",
                empty, actual2);
    }
    @Test
    // covers expression as the value 1
    public void testMultiplyExpr_One() {
      Expression actual1 = one.multiplyExpr(expr);
      Expression actual2 = expr.multiplyExpr(one);
      
      assertEquals("Expected 1*expression = expression", 
              expr, actual1);
      assertEquals("Expected expression*1 = expression", 
              expr, actual2);
    }
    @Test
    // covers expression contains multiple variables,
    //        input as a subset
    public void testMultiplyExpr_MultipleVars() {
        Expression subset = Expression.parse("x + 0.500009");
        Expression actual = expr.multiplyExpr(subset);
        String expected = "((x)*(y) + x + 0.5)*(x + 0.5)";
        
        assertEquals("Expected non-simplified expression * subset", 
                expected, actual.toString());
    }
    @Test
    // covers expression contains multiple variables,
    //        input as a subset
    //        input equals this
    public void testMultiplyExpr_Equals() {
        Expression actual = expr.multiplyExpr(expr);
        String expected = "(" + expr.toString() + ")" + "*(" + expr.toString() + ")";
        
        assertEquals("Expected non-simplified expression * subset", 
                expected, actual.toString());
    }
    @Test
    // covers empty expression
    public void testAddVariable_Empty() {
        Expression actual = empty.addVariable("x");
        String expected = "x";
        
        assertEquals("Expected expression + 0 = expression", 
                expected, actual.toString());
        
    }
    @Test
    // covers input variable does not exist in expression
    public void testAddVariable_NotExist() {
        Expression actual = expr.addVariable("foo");
        Expression expected = Expression.parse("foo + " + expr.toString());
        
        assertEquals("Expected variable added at the start", 
                expected, actual);
    }
    @Test
    // covers contains input variable
    public void testAddVariable_Exists() {
        Expression actual1 = expr.addVariable("y");
        Expression actual2 = Expression.parse("x*y").addVariable("x");
        Expression actual3 = expr.addVariable("x");
        Expression expected1 = Expression.parse("y + " + expr.toString());
        Expression expected2 = Expression.parse("x + x*y");
        Expression expected3 = Expression.parse("x + " + expr.toString());
        
        assertEquals("Expected variable added at the start",
                expected1, actual1);        
        assertEquals("Expected variable added at the start",
                expected2, actual2);
        assertNotEquals("Expected expression not simplified", 
                expected3, actual3);
    }
    
    }