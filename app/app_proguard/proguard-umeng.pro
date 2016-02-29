-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}

-keep public class info.xudshen.jandan.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn org.android.agoo.net.**
-dontwarn com.ta.utdid2.**
-dontwarn com.umeng.**