import com.paul.samuels.maestro.dsl.maestroRun

fun main() {
    maestroRun("com.apple.MobileAddressBook") {
        launchApp("com.apple.MobileAddressBook")

        HomePage {
            tapAdd()
        }

        EditFormPage {
            setFirstName("John")
            setLastName("Appleseed")
            tapDone()
        }
    }
}
