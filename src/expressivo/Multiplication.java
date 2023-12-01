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
		 if (e.equals(new Value(0))) {
	            return this;
	        }
	        if (e.equals(this)) {
	            return this.multiplyExpr(new Value(2));
	        }
	        return new Addition(this, e);
	}

	@Override
	public Expression multiplyExpr(Expression e) {
		 Value zero = new Value(0);
	        if (e.equals(zero)) {
	            return zero;
	        }
	        if (e.equals(new Value(1))) {
	            return this;
	        }
	        return new Multiplication(this, e);
	}

	@Override
	public Expression addVariable(String variable) {
		assert variable != null && variable != "";
        
        return new Addition(new Variable(variable), this);
	}

	@Override
	public Expression multiplyVariable(String variable) {
		 assert variable != null && variable != "";
	        
	    return new Multiplication(new Variable(variable), this);
	}
	@Override public String toString() {
        String leftString = this.left.toString();
        String rightString = this.right.toString();
        leftString = "(" + leftString + ")";
        rightString = "(" + rightString + ")";

        checkRep();
        return leftString + "*" + rightString;
    }  
    @Override public boolean equals(Object thatObject) {
        if (thatObject == this) {
            return true;
        }
        if (!(thatObject instanceof Multiplication)) {
            return false;
        }
        Multiplication thatMult = (Multiplication) thatObject;

        checkRep();
        return this.toString().equals(thatMult.toString());
    }
    @Override public int hashCode() {
        final int prime = 37;
        int result = 1;
        
        result = prime*result + this.left.hashCode();
        result = prime*result + this.right.hashCode();

        checkRep();
        return result;
    }


}
