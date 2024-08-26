package lab2

fun main() {
    val a = FractionMutable(1,2,-1)
    a.add(FractionMutable(1,3))
    println(a)
    a.mult(FractionMutable(5,2, -1))
    println(a)
    a.div(FractionMutable(2,1))
    println(a)
}

class FractionMutable(
    private var numerator: Int,
    private var denominator: Int,
    sign: Int = 1
) {

    init {
        if (denominator < 0) {
            denominator *= -1
            numerator *= -1
        }
        if (sign < 0) {
            numerator *= -1
        }

        simplify()
    }

    private fun simplify() {
        val gcdValue = gcd(Math.abs(numerator), denominator)
        numerator /= gcdValue
        denominator /= gcdValue
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    override fun toString(): String {
        val signPrefix = if (numerator < 0) "-" else ""
        return "$signPrefix${Math.abs(numerator)}/${denominator}"
    }

    fun negate() {
        numerator *= -1
    }

    fun add(other: FractionMutable) {
        val commonDenominator = this.denominator * other.denominator
        val thisNumerator = this.numerator * other.denominator
        val otherNumerator = other.numerator * this.denominator

        this.numerator = thisNumerator + otherNumerator
        this.denominator = commonDenominator
        simplify()
    }

    fun mult(other: FractionMutable) {
        this.numerator *= other.numerator
        this.denominator *= other.denominator
        simplify()
    }

    fun div(other: FractionMutable) {
        this.numerator *= other.denominator
        this.denominator *= other.numerator
        if (this.denominator < 0) {
            this.denominator *= -1
            this.numerator *= -1
        }
        simplify()
    }

    fun intPart(): Int {
        return numerator / denominator
    }
}