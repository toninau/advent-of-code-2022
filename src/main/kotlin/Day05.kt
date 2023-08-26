import kotlin.io.path.Path

fun main() {
    val fileContents = Day05.readFile("day5.txt")
    println(Day05.part1(fileContents))
    println(Day05.part2(fileContents))
}

object Day05 {
    fun part1(input: String): String {
        val stack = createStartingStack(input)
        val instructions = createInstructions(input)
        instructions.forEach { (amount, from, to) ->
            val toBeMoved = stack[from].take(amount)
            stack[from].subList(0, amount).clear()
            stack[to].addAll(0, toBeMoved.reversed())
        }
        return stack.map { it.first() }.joinToString("")
    }

    fun part2(input: String): String {
        val stack = createStartingStack(input)
        val instructions = createInstructions(input)
        instructions.forEach { (amount, from, to) ->
            val toBeMoved = stack[from].take(amount)
            stack[from].subList(0, amount).clear()
            stack[to].addAll(0, toBeMoved)
        }
        return stack.map { it.first() }.joinToString("")
    }

    private fun createStartingStack(input: String): List<MutableList<Char>> {
        val stacks = input.substringBefore("\n\n").split("\n")
        val stackRows = stacks.filter { it.contains('[') }
        // 1 = 1
        // 2 = 1 + 4 = 5
        // 3 = 5 + 4 = 9
        return (1..stackRows.last().length step 4).map { index ->
            stackRows
                .mapNotNull { it.getOrNull(index) }
                .filter { it.isUpperCase() }
                .toMutableList()
        }
    }

    private fun createInstructions(input: String): List<Instruction> {
        val instructions = input.substringAfter("\n\n").split("\n")
        return instructions.map { row ->
            row.split(" ")
                .let { Instruction(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }
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

data class Instruction(val amount: Int, val from: Int, val to: Int)
