package yyc.xk.core;


import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;


public class uritostring {
    public static String uritostring(Context context, Uri uri) {
        Uri uri2 = null;
		
        if ((Build.VERSION.SDK_INT >= 19 ? 1 : 0) == 0 || !DocumentsContract.isDocumentUri(context, uri)) {

            return "content".equalsIgnoreCase(uri.getScheme()) ? aaa(context, uri, null, null) : "file".equalsIgnoreCase(uri.getScheme()) ? uri.getPath() : null;
        } else {
            String[] split = null;
            if (aa(uri)) {

                split = DocumentsContract.getDocumentId(uri).split(":");
                return "primary".equalsIgnoreCase(split[0]) ? Environment.getExternalStorageDirectory() + "/" + split[1] : null;
            } else if (bb(uri)) {

                return aaa(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri))), null, null);//减少

            } else if (!cc(uri)) {

                String ss = Uri.decode(uri.toString());
                String[] sss = ss.split("//", 3);
                return "/" + sss[sss.length - 1];
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//新增
                    split = DocumentsContract.getDocumentId(uri).split(":");
                }
                Object obj = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//新增
                    obj = DocumentsContract.getDocumentId(uri).split(":")[0];
                }
                if ("image".equals(obj)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(obj)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(obj)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                return aaa(context, uri2, "_id=?", new String[]{split[1]});
            }
        }
    }

    public static boolean aa(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean bb(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean cc(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String aaa(Context context, Uri uri, String str, String[] strArr) {
        String str2 = "_data";

        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
        if (query != null) {

            if (query.moveToFirst()) {
                str2 = query.getString(query.getColumnIndexOrThrow("_data"));
                query.close();
                return str2;
                /*
                 if (query == null) {
				 return str2;
				 }
                 */
            }
        }
        if (query != null) {
            query.close();
        }
        return null;
    }
}


