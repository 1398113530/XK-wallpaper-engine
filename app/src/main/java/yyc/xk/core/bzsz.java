package yyc.xk.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import java.util.List;

public class bzsz extends Activity {
	boolean homee = false;

	final boolean isServiceRunning(Context var1, String var2) {
		boolean var4 = false;
		List var5 = ((ActivityManager)var1.getSystemService("activity")).getRunningServices(100);
		if (var5.size() <= 0) {
			var4 = false;
		} else {
			for(int var3 = 0; var3 < var5.size(); ++var3) {
				if (((RunningServiceInfo)var5.get(var3)).service.getClassName().equals(var2)) {
					var4 = true;
					break;
				}
			}
		}

		return var4;
	}

	@Override
	protected void onCreate(Bundle var1) {
	
		super.onCreate(var1);
		Uri var2 = this.getIntent().getData();
		Editor var5 = this.getSharedPreferences("yyc", 4).edit();
		if (var2 != null) {
			new uritostring();
			var5.putString("uri", uritostring.uritostring(this, var2));
			var5.commit();
			Intent var7;
			if (!this.isServiceRunning(this, "yyc.xk.core.Wallpaper")) {
				this.homee = true;
				ComponentName var6 = new ComponentName(this.getPackageName(), this.getPackageName() + ".Wallpaper");
				Intent var8 = new Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
				var8.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", var6);

				try {
					this.startActivity(var8);
				} catch (Exception var4) {
					var7 = new Intent();
					var7.setFlags(0x10000000);
					var7.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");

					try {
						this.startActivity(var7);
					} catch (Exception var3) {
						Toast.makeText(this, "安卓动态壁纸组件丢失", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				var7 = new Intent("android.intent.action.MAIN");
				var7.setFlags(0x04000000);
				var7.addCategory("android.intent.category.HOME");
				this.startActivity(var7);
				this.finish();
			}
		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (this.homee) {
			this.homee = false;
			Intent var1 = new Intent("android.intent.action.MAIN");
			var1.setFlags(0x04000000);
			var1.addCategory("android.intent.category.HOME");
			this.startActivity(var1);
			this.finish();
		}

	}
}
