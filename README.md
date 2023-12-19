A Kotlin Dsl for the [Maestro](https://maestro.mobile.dev) UI testing tool.

## Starter Project

If you have [Maestro](https://maestro.mobile.dev) installed in the default location and an iOS simulator you can open the `StarterProject` and hit run or run `./gradlew run`.
You should see the simulator launch and start adding a contact in the iOS contacts app.

## Basic Usage

Add `maestro-dsl` to your `build.gradle.kts`

```kotlin
implementation("com.paul-samuels:maestro-dsl:0.0.1")
```

See the [StarterProject/build.gradle.kts](https://github.com/paulsamuels/maestro-dsl/blob/main/StarterProject/build.gradle.kts) for an example).

Now you can use the top level `maestroRun` command to build a flow to execute.
A flow that would add a contact to the contacts app might look like this:

```kotlin
fun main() {
    maestroRun("com.apple.MobileAddressBook") {
        launchApp("com.apple.MobileAddressBook")

        tapOn(text("Add"))

        tapOn(id("First name"))
        inputText("John")

        tapOn(id("Last name"))
        inputText("Appleseed")

        tapOn(text("Done"))
    }
}
```

> [!TIP]
> In order to find the selectors to use for picking elements you can run `maestro studio` and click around to explore your app.

## Page Objects

It's common in UI testing to introduce Page Objects to share logic and hide away the details of knowing selectors ids.
In the above example knowing that the `Add` and `Done` buttons don't have ids but instead need looking up by `text` and that the text fields have `id`s is knowledge we want to share and not duplicate.
You can do this by creating a Page class to represent the different screens.
For the contacts app we could have two screens - the `HomePage` and the `EditFormPage` so let's create those page objects

> [!TIP]
> You'll need to enable context receivers to follow the example

```kotlin
context(ScriptScope)
class HomePage(configure: HomePage.() -> Unit) {
    init {
        configure()
    }
    
    fun tapAdd() {
        tapOn(text("Add"))
    }
}
```

The `EditFormPage` is more of the same

```kotlin
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
```

With these in place the original flow can now be expressed without mentioning identifiers

```kotlin
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
```

