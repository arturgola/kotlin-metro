package lab4

fun main() {
    val a = Fraction(1, 2, -1)
    println(a)
    println(a + Fraction(1, 3))
    println(a * Fraction(5, 2, -1))
    println(a / Fraction(2, 1))
    println(-Fraction(1, 6) + Fraction(1, 2))
    println(Fraction(2, 3) * Fraction(3, 2))
    println(Fraction(1, 2) > Fraction(2, 3))
}

class Fraction(numerator: Int, denominator: Int, sign: Int = 1) : Comparable<Fraction> {
    private val numerator: Int
    private val denominator: Int
    private val sign: Int

    init {
        require(denominator != 0) { "Denominator cannot be zero" }
        val gcdValue = gcd(numerator, denominator)
        this.numerator = kotlin.math.abs(numerator) / gcdValue
        this.denominator = kotlin.math.abs(denominator) / gcdValue
        this.sign = if (numerator == 0) 1 else sign * if (denominator < 0) -1 else 1
    }

    private fun gcd(a: Int, b: Int): Int {
        var x = a
        var y = b
        while (y != 0) {
            val temp = y
            y = x % y
            x = temp
        }
        return kotlin.math.abs(x)
    }

    fun add(other: Fraction): Fraction {
        val commonDenominator = denominator * other.denominator
        val newNumerator = sign * numerator * other.denominator + other.sign * other.numerator * denominator
        return Fraction(newNumerator, commonDenominator)
    }

    fun subtract(other: Fraction): Fraction = this.add(other.negate())

    fun multiply(other: Fraction): Fraction {
        return Fraction(numerator * other.numerator, denominator * other.denominator, sign * other.sign)
    }

    fun divide(other: Fraction): Fraction {
        require(other.numerator != 0) { "Cannot divide by zero" }
        return this.multiply(Fraction(other.denominator, other.numerator, other.sign))
    }

    fun negate(): Fraction = Fraction(numerator, denominator, -sign)

    override fun compareTo(other: Fraction): Int {
        val lhs = sign * numerator * other.denominator
        val rhs = other.sign * other.numerator * denominator
        return lhs.compareTo(rhs)
    }

    override fun toString(): String {
        return if (sign == -1) "-$numerator/$denominator" else "$numerator/$denominator"
    }

    operator fun plus(other: Fraction) = this.add(other)
    operator fun minus(other: Fraction) = this.subtract(other)
    operator fun times(other: Fraction) = this.multiply(other)
    operator fun div(other: Fraction) = this.divide(other)
    operator fun unaryMinus() = this.negate()

    override fun equals(other: Any?): Boolean {
        if (other !is Fraction) return false
        return this.compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return 31 * numerator + denominator + sign
    }
}