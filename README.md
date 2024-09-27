## Reflection Navigation

## ❯ Description

Just another Jetpack Compose Navigation Library that uses the power of Kotlin Reflection, GSON,
and Kotlin extension.

**Kotlin Reflection** is used to reflect the code at runtime, like knowing the parameter name.

**GSON** is used to parse Object as String and vise versa.

**Kotlin Extension** is used provide new function to already existing compose navigation API.

Work like the latest type-safe Jetpack Compose Navigation but without Serializer.
Should be fast enough to build because not using KSP and generated code.

## ❯ Download

Add the dependency below into your **module**'s build.gradle file:

```kotlin
dependencies {
    implementation("io.github.jimlyas:reflection-navigation:0.1.0")
}
```

## R8 / Proguard

The specific rules are [already bundled](reflection-navigation/consumer-rules.pro) into the JAR
which can be interpreted by R8 automatically.

## ❯ Implementation

### Define Route

Make an Object or data class first and we will use it as route for your Screen and just slap
the annotation `@ReflectiveRoute` at the top.

```kotlin
// Screen without any navigation argument
@ReflectiveRoute
object SomeRoute

// Screen with some argument
@ReflectiveRoute
data class OtherRoute(val someData: String, val someClassMaybe: SomeClass)
```

The annotation doesn't used to generate or parse your code.
It is only used to prevent your code to be obfuscated.

As of why any route need to be preserved because the parameter name is used as the argument name,
might not work properly with dynamic link if it's obfuscated.

### Register to NavGraphBuilder

Use the object or class you make earlier and register it to your `NavGraphBuilder` using the
extension
function [`composeRoute`](reflection-navigation/src/main/kotlin/io/github/jimlyas/reflection/navigation/route/RouteExt.kt).

```kotlin
NavHost(
    modifier = Modifier.padding(innerPadding),
    navController = controller,
    // Register your start destination like this by calling asRouteName() function
    startDestination = RouteA::class.asRouteName()
) {
    // basic screen
    composeRoute<RouteA> { YourComposeScreen() }

    // with deep link registered
    composeRoute<RouteB>(
        deepLinks = listOf(deeplinkA, deepLinkB)
    ) { YourOtherComposeScreen() }
}
```

### Navigate to destination

To start navigating to other screen,
use [`navigateTo`](reflection-navigation/src/main/kotlin/io/github/jimlyas/reflection/navigation/navigation/NavigationExt.kt)
from your `NavController` by passing the instance the route that associated with intended screen.

```kotlin
// Navigate without any arguments
navController.navigateTo(RouteA)

// Navigate with some arguments
navController.navigateTo(RouteB("someData", 69, false))
```

### Get Argument

There's two ways you can obtain the arguments passed from the navigation, call the function `getArg`
from `SavedStateHandler` in `ViewModel` or from `NavBackStackEntry` when calling `composeRoute`.

```kotlin
// From ViewModel
@HiltViewModel
class SomeViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val args = savedStateHandle.getArg<RouteB>()
}

// From composeRoute
composeRoute<RouteB> {
    val args = it.getArg<RouteB>()
    RouteBScreen(args)
}
```

Which one is better? You do you. The return type will be nullable to prevent crashed if reflection
failed to initialize your route instance or if your route class doesn't have primary constructor
_(which rarely happen I guess)_.

## ❯ License

```
Copyright (c) 2024 Jimly Asshiddiqy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```