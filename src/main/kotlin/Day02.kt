import kotlin.io.path.Path

fun main() {
    val fileContents = Day02.readFile("day2.txt")
    val rockPaperScissorsPairs = Day02.parseInput(fileContents)
    println(Day02.part1(rockPaperScissorsPairs))
    println(Day02.part2(rockPaperScissorsPairs))
}

object Day02 {
    fun part1(pairs: List<Pair<Char, Char>>): Int {
        // Answer 10994
        return pairs.fold(0) { total, pair ->
            val myMove = RockPaperScissors.resolveMove(pair.second)
            val roundPoints = roundPoints(RockPaperScissors.resolveMove(pair.first), myMove)
            total + myMove.pointValue + roundPoints
        }
    }

    fun part2(pairs: List<Pair<Char, Char>>): Int {
        // Answer 12526
        return pairs.fold(0) { total, pair ->
            val opponentMove = RockPaperScissors.resolveMove(pair.first)
            val myMove = outcomeToMove(opponentMove, pair.second)
            val roundPoints = roundPoints(opponentMove, myMove)
            total + myMove.pointValue + roundPoints
        }
    }

    private fun roundPoints(opponentMove: RockPaperScissors, myMove: RockPaperScissors): Int {
        // LOSE
        if (myMove.losesTo() == opponentMove) return 0
        // DRAW
        if (opponentMove == myMove) return 3
        // WIN
        if (myMove.wins() == opponentMove) return 6
        throw Exception("Invalid moves")
    }

    private fun outcomeToMove(opponentMove: RockPaperScissors, outcome: Char): RockPaperScissors {
        // LOSE
        if (outcome == 'X') return opponentMove.wins()
        // DRAW
        if (outcome == 'Y') return opponentMove
        // WIN
        if (outcome == 'Z') return opponentMove.losesTo()
        throw Exception("Invalid outcome $outcome")
    }

    fun parseInput(input: String): List<Pair<Char, Char>> {
        return input
            .trim()
            .split("\n")
            .map {
                Pair(it[0], it[2])
            }
    }

    fun readFile(fileName: String): String {
        val filePath = Path("src", "main", "resources", fileName)
        return try {
            filePath.toFile().readText()
        } catch (e: Exception) {
            throw Exception("Failed to read file from ${filePath.toAbsolutePath()}...")
        }
    }
}

enum class RockPaperScissors(val pointValue: Int) {
    ROCK (1) {
        override fun wins() = SCISSORS
        override fun losesTo() = PAPER
    },
    PAPER (2) {
        override fun wins() = ROCK
        override fun losesTo() = SCISSORS
    },
    SCISSORS (3) {
        override fun wins() = PAPER
        override fun losesTo() = ROCK
    };

    abstract fun wins(): RockPaperScissors
    abstract fun losesTo(): RockPaperScissors

    companion object {
        fun resolveMove(character: Char): RockPaperScissors {
            if (charArrayOf('A', 'X').contains(character)) {
                return ROCK
            }
            if (charArrayOf('B', 'Y').contains(character)) {
                return PAPER
            }
            if (charArrayOf('C', 'Z').contains(character)) {
                return SCISSORS
            }
            throw IllegalArgumentException("Invalid character $character")
        }
    }
}
