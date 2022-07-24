import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeOptions

val browserDetectionEndpoints = mapOf(
    "JavaScript enabled" to "https://www.whatismybrowser.com/detect/is-javascript-enabled",
    "1P Cookies enabled" to "https://www.whatismybrowser.com/detect/are-cookies-enabled",
    "3P Cookies enabled" to "https://www.whatismybrowser.com/detect/are-third-party-cookies-enabled",
    "User Agent" to "https://www.whatismybrowser.com/detect/what-is-my-user-agent/",
    "HTTP Referrer" to "https://www.whatismybrowser.com/detect/what-is-my-referrer",
    "MIME Types Supported" to "https://www.whatismybrowser.com/detect/mime-types-supported",
    "Navigator Platform" to "https://www.whatismybrowser.com/detect/navigator-platform",
    "Detected Operating System" to "https://www.whatismybrowser.com/detect/what-operating-system-do-i-have",
    "Computer Screen Size" to "https://www.whatismybrowser.com/detect/how-big-is-my-computer-screen",
    "Web Browser Size" to "https://www.whatismybrowser.com/detect/how-big-is-my-web-browser",
    "IP Address" to "https://www.whatismybrowser.com/detect/what-is-my-ip-address",
    "IP Address Location" to "https://www.whatismybrowser.com/detect/ip-address-location",
    "TOR enabled" to "https://www.whatismybrowser.com/detect/am-i-using-tor"
)

val httpHeadersEndpoint = "https://www.whatismybrowser.com/detect/what-http-headers-is-my-browser-sending"  // table

fun main(args: Array<String>) {
    println("Hello World!")

    val programArgs = args.takeIf { it.isNotEmpty() }?.joinToString() ?: "None provided"
    println("Program arguments: $programArgs")

    // https://peter.sh/experiments/chromium-command-line-switches/
    val options = ChromeOptions()
    options.addArguments("--no-sandbox")
    options.setHeadless(true)

    WebDriver(options).use { driver ->
        println("Driver instantiated.\n")

        browserDetectionEndpoints.forEach { attribute, endpoint ->
            driver.get(endpoint)
            val detectedValue = try {
                driver.findElement(By.id("detected_value")).text.replace("\n", " ")
            } catch (e: org.openqa.selenium.NoSuchElementException) {
                "Failed!  Had an NoSuchElementException"
            }
            println("$attribute: $detectedValue")
        }

        driver.get(httpHeadersEndpoint)
        val table = driver.findElement(By.className("table-data"))
        println("HTTP Headers")
        table.findElements(By.tagName("tr")).forEach { row ->
            val headerKey = row.findElement(By.tagName("th")).text
            val headerVal = row.findElement(By.tagName("td")).text
            println("\t$headerKey: $headerVal")
        }
    }
}