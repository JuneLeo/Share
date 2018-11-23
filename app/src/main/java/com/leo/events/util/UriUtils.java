package com.leo.events.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;

public final class UriUtils {
    private static final String AUTHORITIES = "com.event.leo.fileprovider";

    private UriUtils() {

    }

    public static Uri legalLocalUri(@NonNull Context context, @NonNull String path) {
        File file = new File(path);
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uriForFile = FileProvider.getUriForFile(context, AUTHORITIES, file);
                return uriForFile;
            } else {
                return Uri.fromFile(file);
            }
        }
        return null;
    }

    public static Uri legalFileLocalUri(@NonNull Context context, @NonNull File file) {
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uriForFile = FileProvider.getUriForFile(context, AUTHORITIES, file);
                return uriForFile;
            } else {
                return Uri.fromFile(file);
            }
        }
        return null;
    }
}
