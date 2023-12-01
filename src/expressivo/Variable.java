package expressivo;

import java.util.Map;

/**
 * An immutable type representing a named variable in an expression
 * Variable names are case-sensitive
 */
public class Variable implements Expression {
    private final String id;
    
    // Abstraction Function
    //   represents a named variable as an expression
    //
    // Representation Invariant
    //   - id.length > 0
    //   - id.charAt(n) must be a letter, a-zA-Z 
    //     for all 0 < n < id.length
    //
    // Safety From Exposure
    //   - id is private and final
    //   - id is a string which is immutable
    //   - Variable shares its rep with other implementations
    //     but they do not mutate it
    
    private void checkRep() {
        assert id != null && id != "";
        assert id.length() > 0;
        assert id.matches("[a-zA-Z]+");
    }
    public Variable(String id) {
        this.id = id;
        checkRep();
    }
    @Override public String toString() {
        return this.id;
    }
    @Override public boolean equals(Object thatObject) {
        if (thatObject == this) {
            return true;
        }
        if (!(thatObject instanceof Variable)) {
            return false;
        }
        Variable thatVar = (Variable) thatObject;
        String thatId = thatVar.toString();

        checkRep();
        return this.id.equals(thatId);
    }
    @Override public int hashCode() {
        return this.id.hashCode();
    }
    @Override public Expression addExpr(Expression e) {
        if (e.equals(new Value(0))) {
            return this;
        }
        checkRep();
        return e.addVariable(id);
    }
    @Override public Expression multiplyExpr(Expression e) {
        Value zero = new Value(0);
        if (e.equals(zero)) {
            return zero;
        }
        if (e.equals(new Value(1))) {
            return this;
        }
        checkRep();
        return e.multiplyVariable(this.id);
    }
    // TODO: have it simplify such that x + x + x = 3*x
    @Override public Expression addVariable(String variable) {
        assert variable != null && variable != "";
        
        return new Addition(new Variable(variable), this);
    }
    @Override public Expression multiplyVariable(String variable) {
        assert variable != null && variable != "";
        
        return new Multiplication(new Variable(variable), this);
    }
   
  
}