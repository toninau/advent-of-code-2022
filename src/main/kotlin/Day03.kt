import kotlin.io.path.Path

fun main() {
    val fileContents = Day03.readFile("day3.txt")
    val contents = Day03.parseInput(fileContents)
    println(Day03.part1(contents))
    println(Day03.part2(contents))
}

object Day03 {
    private const val values = "+abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    fun part1(contents: List<String>): Int {
        return contents
            .map {
                val middle = it.length / 2
                Pair(it.substring(0, middle), it.substring(middle))
            }
            .fold(0) { total, pair ->
                val letterValue = pair.first
                    .find { it in pair.second }
                    ?.let { values.indexOf(it) } ?: 0
                total + letterValue
            }
    }

    fun part2(contents: List<String>): Int {
        return contents
            .chunked(3)
            .fold(0) { total, strings ->
                val letterValue = strings[0]
                    .find { it in strings[1] && it in strings[2] }
                    ?.let { values.indexOf(it) } ?: 0
                total + letterValue
            }
    }

    fun parseInput(input: String): List<String> {
        return input
            .trim()
            .split("\n")
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