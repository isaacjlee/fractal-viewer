// Isaac Lee
// Data Structures and Algorithms 2
// Assignment 3

/**
 * A class which can be used to represent and do operations with complex numbers.
 * @author Isaac Lee
 */
public class Complex
{
	private double real;
	private double imaginary;
	/**
	 * A constructor for constructing Complex objects which inputs a real part and an imaginary part.
	 * @param inReal The real part of the Complex object to be created, represented with a double.
	 * @param inImag The imaginary part of the Complex object to be created, represented with a double.
	 */
	public Complex(double inReal, double inImag)
	{
		real = inReal;
		imaginary = inImag;
	}
	/**
	 * A constructor for constructing Complex objects which inputs a Complex object and makes a copy of it.
	 * @param c The Complex object to be copied.
	 */
	public Complex(Complex c)
	{
		real = c.real;
		imaginary = c.imaginary;
	}
	/**
	 * A method which multiplies this Complex object by the inputed Complex object and stores the result in this Complex object.
	 * @param toMult The Complex object to multiply this Complex object by.
	 */
	public void multiply(Complex toMult)
	{
		double tempreal = real * toMult.real - imaginary * toMult.imaginary;
		double tempimaginary = real * toMult.imaginary + imaginary * toMult.real;
		real = tempreal;
		imaginary = tempimaginary;
	}
	/**
	 * A method which adds the inputed Complex object to this Complex object and stores the result in this Complex object.
	 * @param toAdd The Complex object to add to this Complex object.
	 */
	public void add(Complex toAdd)
	{
		real += toAdd.real;
		imaginary += toAdd.imaginary;
	}
	/**
	 * A method which subtracts the inputed Complex object from this Complex object and stores the result in this Complex object.
	 * @param toSub The Complex object to subtract from this Complex object.
	 */
	public void subtract(Complex toSub)
	{
		real -= toSub.real;
		imaginary -= toSub.imaginary;
	}
	/**
	 * Accessor method for the real part of this Complex object.
	 * @return The real part of this Complex object, represented with a double.
	 */
	public double getReal()
	{
		return real;
	}
	/**
	 * Accessor method for the imaginary part of this Complex object.
	 * @return The imaginary part of this Complex object, represented with a double.
	 */
	public double getImag()
	{
		return imaginary;
	}
	/**
	 * A method which computes and returns the modulus of this Complex object.
	 * @return A double representing the modulus of this Complex object.
	 */
	public double modulus()
	{
		return Math.sqrt(real * real + imaginary * imaginary);
	}
}
