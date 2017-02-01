package com.test.prototype_s.util;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtil {

	public static void showErrorAlert(Context context, String title, String msg){
		AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
				.setPositiveButton("OK", null)
				.setCancelable(false)
				.create();
		dialog.show();
	}
}
