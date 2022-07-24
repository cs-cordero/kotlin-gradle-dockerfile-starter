import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

/**
 * A lightweight wrapper over the [ChromeDriver] that makes it [AutoCloseable].
 */
class WebDriver(options: ChromeOptions) : ChromeDriver(options), AutoCloseable {
    override fun close() {
        quit()
    }
}