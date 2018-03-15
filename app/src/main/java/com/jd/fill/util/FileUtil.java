package com.jd.fill.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.jd.fill.R;
import com.jd.fill.bean.GameItemInfo;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houhuang on 18/3/12.
 */
public class FileUtil {

    public static Bitmap getBitmapFromDrawable(Context context, int id)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        InputStream is = context.getResources().openRawResource(id);

        return BitmapFactory.decodeStream(is, null, opt);

    }

    public static List<GameItemInfo> readItemInfoFromJson(Context context)
    {
        String jsonStr = FileUtil.readFileFrom_datadata(context, "pub.json");
        if (TextUtils.isEmpty(jsonStr))
        {
            jsonStr = readFileFrom_Assets(context, "pub.json");
        }

        if (TextUtils.isEmpty(jsonStr))
            return null;

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject jsonObject1 = jsonObject.getJSONObject("level");
            JSONArray jsonArray = jsonObject1.getJSONArray("level_info");

            List<GameItemInfo> list = new ArrayList<GameItemInfo>();

            for (int i = 0; i < jsonArray.length(); ++i)
            {
                GameItemInfo itemInfo = new GameItemInfo();
                JSONObject object = jsonArray.getJSONObject(i);
                itemInfo.setRow(Integer.parseInt(object.getString("row")));
                itemInfo.setCol(Integer.parseInt(object.getString("col")));

                String hintStr = object.getString("hint");
                itemInfo.setHint(hintStr.toCharArray());

                String str = object.getString("state");

                if (str.length() != itemInfo.getCol() * itemInfo.getRow())
                {
                    continue;
                }else
                {
                    char[] state = str.toCharArray();
                    int[] st = new int[state.length];
                    for (int j = 0; j < state.length; ++j)
                    {
                        st[j] = Integer.parseInt(String.valueOf(state[j]));
                    }
                    itemInfo.setState(st);
                    list.add(itemInfo);
                }



            }

            return list;

        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    public static String readFileFrom_datadata(Context context, String fileName)
    {
        try{
            FileInputStream inputStream = context.openFileInput(fileName);//只需传文件名
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();//输出到内存

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, len);
            }

            byte[] content_byte = outputStream.toByteArray();
            String content = new String((content_byte));

            return content;

        }catch (IOException e)
        {

        }

        return "";
    }

    public static String readFileFrom_Assets(Context context, String fileName)
    {
        StringBuilder builder = new StringBuilder();
        try {
            AssetManager manager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(manager.open(fileName), "UTF-8"));
            String line;
            while ((line = bf.readLine()) != null)
            {
                builder.append(line);
            }

            return builder.toString();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    /**
     *  往data/data下存储数据
     * */

    public static boolean saveFileTo_datadata(Context context, String fileName, String content)
    {


        try {
            FileOutputStream output = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            output.write(content.getBytes());
            output.close();

            return true;
        }catch (Exception e)
        {

        }

        return false;
    }
}
