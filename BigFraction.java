import java.math.BigInteger;

/**
 * Author: CSC207 instructor and Audrey Trinh
 * Purpose: A class for fraction with numerator and denominator as BigNumber,
 * including basic evaluation method.
 */
public class BigFraction {
  /*
   * (1) Denominators are always positive. Therefore, negative fractions are
   * represented
   * with a negative numerator. Similarly, if a fraction has a negative numerator,
   * it
   * is negative.
   *
   * (2) Fractions are not necessarily stored in simplified form. To obtain a
   * fraction
   * in simplified form, one must call the `simplify` method.
   */

  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The numerator of the fraction. Can be positive, zero or negative.
   */
  BigInteger num;

  /**
   * The denominator of the fraction. Must be non-negative.
   */
  BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   */
  public BigFraction(BigInteger num, BigInteger denom) {
    this.num = num;
    this.denom = denom;
  } // Fraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   */
  public BigFraction(int num, int denom) {
    this.num = BigInteger.valueOf(num);
    this.denom = BigInteger.valueOf(denom);
  } // Fraction(int, int)

  /**
   * Build a new fraction by parsing a string.
   */
  public BigFraction(String str) {
    if (str.contains("/")) {
      this.num = new BigInteger(str.substring(0, str.indexOf('/')));
      this.denom = new BigInteger(str.substring(str.indexOf('/') + 1));
    } else {
      this.num = new BigInteger(str);
      this.denom = new BigInteger("1");
    }
  } // Fraction

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Express this fraction as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Multiply this fraction with multMe
   */
  public BigFraction multiply(BigFraction multMe) {
    BigInteger resultNum;
    BigInteger resultDenom;
    resultNum = this.num.multiply(multMe.num);
    resultDenom = this.denom.multiply(multMe.denom);
    return new BigFraction(resultNum, resultDenom);
  }

  /**
   * Divide this fraction by divideMe
   */
  public BigFraction divide(BigFraction divideMe) {
    BigInteger resultNum;
    BigInteger resultDenom;
    resultNum = this.num.multiply(divideMe.denom);
    resultDenom = this.denom.multiply(divideMe.num);
    return new BigFraction(resultNum, resultDenom);
  }

  /**
   * Return the fractional of this fraction
   */
  public BigFraction fractional() {
    BigInteger resultNum;
    resultNum = this.num.remainder(this.denom);
    return new BigFraction(resultNum, this.denom);
  }

  /**
   * Add the fraction `addMe` to this fraction.
   */
  public BigFraction add(BigFraction addMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the
    // product of this object's denominator
    // and addMe's denominator
    resultDenominator = this.denom.multiply(addMe.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(addMe.denom)).add(addMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  }// add(Fraction)

  /**
   * Subtract this fraction with subtractMe
   */
  public BigFraction subtract(BigFraction subtractMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the
    // product of this object's denominator
    // and subtractMe's denominator
    resultDenominator = this.denom.multiply(subtractMe.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(subtractMe.denom)).subtract(subtractMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  }// add(Fraction)

  /**
   * Get the denominator of this fraction.
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Simplify this fraction
   */
  public void simplify() {
    BigInteger start = new BigInteger("0");
    BigInteger multiplier = new BigInteger("1");
    if ((this.num.signum() < 0 || this.denom.signum() < 0) &&
            !(this.num.signum() < 0 && this.denom.signum() < 0)) {
      multiplier = new BigInteger("-1");
    }
    this.num = this.num.multiply(multiplier);
    BigInteger i = this.denom.min(this.num);
    System.out.println("this num before" + this.num);
    // until the divider reach 1
    while (i.compareTo(start) > 0) {
      // if the denominator and numerator are both divisible by one number
      if (this.denom.remainder(i).compareTo(start) == 0 &&
              this.num.remainder(i).compareTo(start) == 0) {
        // divide denominator and numerator by that number
        this.denom = this.denom.divide(i);
        this.num = this.num.divide(i);
      }
      i = i.subtract(new BigInteger("1"));
    }
    this.num = this.num.multiply(multiplier);
  }

  /**
   * toString method to print BigFraction
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0";
      // if it's zero
    } else if (this.denom.equals(new BigInteger("1"))) {
      return this.num + "";
    }
    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    // return this.num.toString().concat("/").concat(this.denom.toString());
    return this.num + "/" + this.denom;
  } // toString()
}
