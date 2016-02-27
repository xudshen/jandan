-keep class dagger.** { *; }
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class javax.inject.** { *; }
-dontwarn dagger.internal.codegen.**
-dontwarn dagger.producers.internal.**
-dontwarn dagger.shaded.auto.common.**