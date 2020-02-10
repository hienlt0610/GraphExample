package dev.hienlt.graph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String loadAssetText(Context context, String inFile) {
        String tContents = "";

        try {
            InputStream stream = context.getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

    public static List<Mien> getDemoData(Context context) {
        List<Mien> miens = new ArrayList<>();
        String data = Utils.loadAssetText(context, "dstinhthanh.json");
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                Mien mien = new Mien();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mien.setName(jsonObject.optString("name"));
                List<Tinh> tinhList = new ArrayList<>();
                JSONArray tinhJsonArray = jsonObject.optJSONArray("tinh");
                for (int j = 0; j < tinhJsonArray.length(); j++) {
                    JSONObject tinhObject = tinhJsonArray.getJSONObject(j);
                    Tinh tinh = new Tinh();
                    tinh.setName(tinhObject.optString("name"));

                    List<Huyen> huyenList = new ArrayList<>();
                    JSONArray huyenJsonArray = tinhObject.optJSONArray("huyen");
                    for (int k = 0; k < huyenJsonArray.length(); k++) {
                        JSONObject huyenJsonObject = huyenJsonArray.getJSONObject(k);
                        Huyen huyen = new Huyen();
                        huyen.setName(huyenJsonObject.optString("name"));
                        huyenList.add(huyen);
                    }
                    tinh.setHuyen(huyenList);

                    tinhList.add(tinh);
                }
                mien.setTinh(tinhList);
                miens.add(mien);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return miens;
    }

    public static Bitmap getViewBitmap(View view)
    {
        //Create a bitmap backed Canvas to draw the view into
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        b.eraseColor(Color.WHITE);
        Canvas c = new Canvas(b);

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
        view.draw(c);

        return b;
    }
}
