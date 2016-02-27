# Android iconics library - https://github.com/mikepenz/Android-Iconics
# Warning: works ONLY with iconics > 1.0.0
#
# Tested on gradle config:
# 
#  compile 'com.mikepenz:iconics-core:1.7.1@aar'
#

-keep class com.mikepenz.iconics.** { *; }
-keep class com.mikepenz.google_material_typeface_library.** { *; }
-keep class com.mikepenz.fontawesome_typeface_library.** { *; }

-keep class .R
-keep class **.R$* {
    <fields>;
}
