package info.xudshen.jandan.utils;

import android.content.Context;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import info.xudshen.jandan.R;

/**
 * Created by xudshen on 16/2/28.
 */
public class RetrofitErrorHelper {
    public static final String transException(Context context, Throwable e) {
        if (e instanceof IOException) {
            if (e instanceof ConnectException) {
                //HttpNoInternetConnectionException
                return context.getString(R.string.retrofit_no_internet);
            } else if (e instanceof SocketTimeoutException) {
                //HttpServerDownException
                return context.getString(R.string.retrofit_socket_timeout);
            } else {
                //HttpNoInternetConnectionException
                return context.getString(R.string.retrofit_no_internet);
            }
        } else {
            //HttpGeneralErrorException
            return context.getString(R.string.retrofit_unknown);
        }
    }
}
