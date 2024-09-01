package lab3

fun main() {
    val lotto = Lotto()
    playLotto()

    val low = 1
    val high = 40
    val secretNumbers = lotto.pickNDistinct(low, high, 7) ?: emptyList()
    val result = findLotto(lotto, secretNumbers, low, high)
    println("Computer guess in ${result.first} steps is ${result.second}")
}

class Lotto {
    fun pickNDistinct(low: Int, high: Int, n: Int): List<Int>? {
        val range = low..high
        if (n > range.count()) return null
        return range.shuffled().take(n)
    }

    fun numCommon(list1: List<Int>, list2: List<Int>): Int {
        return list1.intersect(list2.toSet()).size
    }

    fun isLegalLottoGuess(guess: List<Int>, low: Int, high: Int, count: Int = 7): Boolean {
        val range = low..high
        return guess.size == count && guess.toSet().size == count && guess.all { it in range }
    }

    fun checkGuess(guess: List<Int>, secret: List<Int>, low: Int, high: Int): Int {
        return if (isLegalLottoGuess(guess, low, high)) numCommon(guess, secret) else 0
    }
}

fun readNDistinct(low: Int, high: Int, n: Int): List<Int> {
    while (true) {
        print("Give $n numbers from $low to $high, separated by commas: ")
        val input = readLine()

        if (input == null) {
            println("Invalid input. Please try again.")
            continue
        }

        val numbers = input.split(",").map { it.trim().toIntOrNull() }

        if (numbers.any { it == null }) {
            println("Invalid input. Please make sure to input numbers only.")
            continue
        }

        val numberList = numbers.filterNotNull()

        if (numberList.size != n) {
            println("Invalid input. Please enter exactly $n numbers.")
            continue
        }

        if (numberList.toSet().size != n) {
            println("Invalid input. Please enter distinct numbers.")
            continue
        }

        if (numberList.any { it !in low..high }) {
            println("Invalid input. All numbers must be between $low and $high.")
            continue
        }

        return numberList
    }
}

fun playLotto() {
    val lotto = Lotto()
    val low = 1
    val high = 40
    val secretNumbers = lotto.pickNDistinct(low, high, 7) ?: emptyList()

    while (true) {
        val userGuess = readNDistinct(low, high, 7)
        val correctGuesses = lotto.checkGuess(userGuess, secretNumbers, low, high)
        println("Lotto numbers: $secretNumbers, you got $correctGuesses correct!")

        print("More? (Y/N): ")
        val continuePlaying = readLine()?.trim()?.uppercase()
        if (continuePlaying != "Y") break
    }
}

fun findLotto(lotto: Lotto, secretNumbers: List<Int>, low: Int, high: Int): Pair<Int, List<Int>> {
    var attempts = 0

    while (true) {
        attempts++
        val guess = lotto.pickNDistinct(low, high, 7) ?: continue
        val correct = lotto.checkGuess(guess, secretNumbers, low, high)

        if (correct == 7) {
            return Pair(attempts, guess)
        }
    }
}