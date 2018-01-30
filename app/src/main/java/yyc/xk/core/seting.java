//
// Decompiled by FernFlower - 595ms
//
package yyc.xk.core;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;
import java.util.List;

public class seting extends Activity {
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
		Intent var7 = this.getIntent();
		int var2 = var7.getIntExtra("mode", 999);
		if (var2 == 0) {
			String var3 = var7.getStringExtra("path");
			Editor var4 = this.getSharedPreferences("yyc", 4).edit();
			var4.putString("uri", var3);
			var4.commit();
			Intent var8;
			if (!this.isServiceRunning(this, "yyc.xk.core.Wallpaper")) {
				ComponentName var9 = new ComponentName(this.getPackageName(), this.getPackageName() + ".Wallpaper");
				var8 = new Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
				var8.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", var9);

				try {
					this.startActivity(var8);
				} catch (Exception var6) {
					var8 = new Intent();
					var8.setFlags(0x10000000);
					var8.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");

					try {
						this.startActivity(var8);
					} catch (Exception var5) {
						Toast.makeText(this, "安卓动态壁纸组件丢失", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				var8 = new Intent("android.intent.action.MAIN");
				var8.setFlags(0x04000000);
				var8.addCategory("android.intent.category.HOME");
				this.startActivity(var8);
			}
		}

		Editor var10;
		if (var2 == 1) {
			var10 = this.getSharedPreferences("yyc", 4).edit();
			var10.putBoolean("cx", var7.getBooleanExtra("cx", true));
			var10.commit();
		}

		if (var2 == 2) {
			var10 = this.getSharedPreferences("yyc", 4).edit();
			var10.putBoolean("sj", var7.getBooleanExtra("sj", false));
			var10.commit();
		}

		if (var2 == 3) {
			var10 = this.getSharedPreferences("yyc", 4).edit();
			var10.putBoolean("sy", var7.getBooleanExtra("sy", false));
			var10.commit();
		}

		this.finish();
	}
}

