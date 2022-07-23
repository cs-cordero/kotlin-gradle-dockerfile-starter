import kotlin.random.Random

fun main(args: Array<String>) {
    println("Hello World!")

    val programArgs = args.takeIf { it.isNotEmpty() }?.joinToString() ?: "None provided"
    println("Program arguments: $programArgs")

    Random.nextLong(until = 10000)
        .also {
            println("Pretending to do work for $it milliseconds.")
            Thread.sleep(it)
        }

    println(
        when (Random.nextBoolean()) {
            true -> "Program was successful."
            false -> "Program failed! (Don't worry, not really)"
        }
    )
}