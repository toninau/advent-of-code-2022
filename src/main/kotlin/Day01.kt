import kotlin.io.path.Path

fun main() {
    val fileContents = Day01.readFile("day1.txt") ?: throw Exception("Failed to read file")
    val caloriesCarriedByElfs = Day01.parseInput(fileContents)
    Day01.part1(caloriesCarriedByElfs)
    Day01.part2(caloriesCarriedByElfs)
}

object Day01 {
    fun part1(calories: List<List<Int>>) {
        // Answer 69501 calories
        val mostCalories = calories.maxOfOrNull { it.sum() }
        println("part1 solution: $mostCalories")
    }

    fun part2(calories: List<List<Int>>) {
        // Answer 202346 calories
        val totalCalories = calories
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
        println("part2 solution: $totalCalories")
    }

    fun readFile(fileName: String): String? {
        val filePath = Path("src", "main", "resources", fileName)
        return try {
            filePath.toFile().readText()
        } catch (e: Exception) {
            null
        }
    }

    fun parseInput(input: String): List<List<Int>> {
        // [[1000, 2000, 3000], [4000], ...]
        return input
            .trim()
            .split("\n\n")
            // it.trim().toIntOrNull() ?: throw ...
            .map { chunk -> chunk.lines().map { it.trim().toInt() } }
    }
}
