import com.paul.samuels.maestro.dsl.ScriptScope

context(ScriptScope)
class EditFormPage(configure: EditFormPage.() -> Unit) {
    init {
        configure()
    }

    fun setFirstName(name: String) {
        tapOn(id("First name"))
        inputText(name)
    }

    fun setLastName(name: String) {
        tapOn(id("Last name"))
        inputText(name)
    }

    fun tapDone() {
        tapOn(text("Done"))
    }
}
