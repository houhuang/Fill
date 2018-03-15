package com.jd.fill.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by houhuang on 18/3/15.
 */
public class GeneralUtil {

    public static void shareText(Context context)
    {
        StringBuilder content = new StringBuilder();
        content.append("So cool and relaxing! Come join me!\n");
        content.append("https://play.google.com/store/apps/details?id=");
        content.append(context.getPackageName());

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Tell your friend");
        intent.putExtra(Intent.EXTRA_TEXT, content.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    public static void openGooglePlay(Context context)
    {
        String url = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    public static String getEmojiString(int unicode)
    {
        return new String(Character.toChars(unicode));
    }

}
