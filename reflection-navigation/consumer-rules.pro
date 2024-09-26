# Keep any class annotated with @ReflectiveRoute
-keep @io.github.jimlyas.reflection.navigation.annotation.ReflectiveRoute class ** { *; }

# Keep class members for classes annotated with @ReflectiveRoute
-keepclassmembers @io.github.jimlyas.reflection.navigation.annotation.ReflectiveRoute class ** { *; }