package expressivo;

import java.util.Map;

public class Multiplication implements Expression{
	 private final Expression left;
	    private final Expression right;

	    // Abstraction Function
	    //   represents a multiplication expression made up of
	    //   two subexpressions
	    //
	    // Representation Invariant
	    //   The left and right are non-null immutable expressions
	    //
	    // Safety From Exposure
	    //   - All fields are private and final
	    //   - left and right are required to be immutable
	    //   - Multiplication shares its rep with other implementations
	    //     but they do not modify it
	    
	    private void checkRep() {
	        assert left != null;
	        assert right != null;
	    }
	    public Multiplication(Expression left, Expression right) {
	        this.left = left;
	        this.right = right;
	        checkRep();
	    }
	@Override
	public Expression addExpr(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression multiplyExpr(Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression addVariable(String variable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression multiplyVariable(String variable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression addConstant(double num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression appendCoefficient(double num) {
		// TODO Auto-generated method stub
		return null;
	}

}
