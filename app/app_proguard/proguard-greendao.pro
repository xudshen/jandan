# GreenDao rules
# Source: http://greendao-orm.com/documentation/technical-faq
#
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keepclassmembers class info.xudshen.jandan.data.dao.** {
    public static final <fields>;
}
-keep class **$Properties