import kotlin.io.path.Path

fun main() {
    val fileContents = Day04.readFile("day4.txt")
    val contents = Day04.parseInput(fileContents)
    println(Day04.part1(contents))
    println(Day04.part2(contents))
}

object Day04 {
    fun part1(contents: List<Pair<IntRange, IntRange>>): Int {
        return contents.count {
            it.first.fullyContains(it.second) || it.second.fullyContains(it.first)
        }
    }

    fun part2(contents: List<Pair<IntRange, IntRange>>): Int {
        return contents.count {
            it.first.overlapsAtAll(it.second) || it.second.overlapsAtAll(it.first)
        }
    }

    private fun IntRange.overlapsAtAll(other: IntRange): Boolean =
        first <= other.last && other.first <= last

    private fun IntRange.fullyContains(other: IntRange): Boolean =
        first <= other.first && last >= other.last

    private fun String.toIntRange(): IntRange =
        substringBefore("-").toInt() .. substringAfter("-").toInt()

    fun parseInput(input: String): List<Pair<IntRange, IntRange>> {
        return input
            .trim()
            .split("\n")
            .map {
                Pair(it.substringBefore(",").toIntRange(), it.substringAfter(",").toIntRange())
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