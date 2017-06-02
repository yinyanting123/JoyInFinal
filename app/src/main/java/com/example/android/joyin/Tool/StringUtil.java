package com.example.android.joyin.Tool;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Date;

public class StringUtil {

	//getRealPathFromURI方法的具体实现
	//----------------------------

		public static String getRealPathFromURI(final Context context, final Uri uri) {

			if (null == uri)
				return null;

			final String scheme = uri.getScheme();

			String path = null;

			if (scheme == null)
				path = uri.getPath();
			else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
				path = uri.getPath();
			} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

				Cursor cursor = context.getContentResolver().query(uri, new String[]{ImageColumns.DATA}, null, null, null);

				if (null != cursor) {

					if (cursor.moveToFirst()) {

						int index = cursor.getColumnIndex(ImageColumns.DATA);

						if (index > -1) {
							path = cursor.getString(index);
						}
					}

					cursor.close();
				}
			}

			return path;
		}

	//转换文件名格式 yyyyMMddHHmmssXXX 100-999
	public static String convertFilename(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		Date date = new Date();

		String now = sdf.format(date);

		Random rd = new Random();
		int random = rd.nextInt(900)+100;

		now = now + random;

		return now;
	}

	public static Timestamp StringToTimestamp(String str){
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date11;
		try {
			date11 = df1.parse(str);
			String time = df1.format(date11);
			Timestamp ts = Timestamp.valueOf(time);
			return ts;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	}


}
