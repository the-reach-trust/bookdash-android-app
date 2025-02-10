package org.bookdash.android.presentation.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import timber.log.Timber;

public class StringUtils {
    public static String convertGsUrlToHttp(String gsUrl) {
        if (gsUrl == null || !gsUrl.startsWith("gs://book-dash.appspot.com/")) {
            return null;
        }

        // Extract path from gs:// URL
        String path = gsUrl.substring("gs://book-dash.appspot.com/".length());

        try {
            // URL-encode the path, but leave '/' and ':' as is
            String encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());

            // Construct the Firebase URL
            return String.format("https://firebasestorage.googleapis.com/v0/b/book-dash.appspot.com/o/%s?alt=media",
                    encodedPath);
        } catch (UnsupportedEncodingException e) {
            Timber.tag("URLConversion").e(e, "Failed to encode URL");
            return null;
        }
    }
}
