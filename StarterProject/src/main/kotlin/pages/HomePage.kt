import com.paul.samuels.maestro.dsl.ScriptScope

context(ScriptScope)
class HomePage(configure: HomePage.() -> Unit) {
    init {
        configure()
    }

    fun tapAdd() {
        tapOn(text("Add"))
    }
}
