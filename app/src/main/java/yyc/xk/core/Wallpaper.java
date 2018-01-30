
package yyc.xk.core;



import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.service.wallpaper.*;
import android.view.*;
import android.widget.*;
import java.io.*;

public class Wallpaper extends WallpaperService
{

    @Override
    public Engine onCreateEngine()
    {
		return new CubeEngine(this);
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

	public class CubeEngine extends Engine  implements SurfaceHolder.Callback
    {

		int i=0;
        WallpaperService ww;
        MediaPlayer player;
        SurfaceHolder Holder;
        String dz;
        boolean sy=false;
        boolean cx=false;
        boolean sj=false;
        public CubeEngine(WallpaperService w)
        {

            ww = w;
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder)
        {

            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);

            surfaceHolder.addCallback(this);
        }

        @Override
        public void onDestroy()
        {

            super.onDestroy();
            qc();
        }

        int tj=0;
        long time;

		boolean sjj=false;
        public void onTouchEvent(MotionEvent event)
		{

            if (event.getAction() == MotionEvent.ACTION_DOWN && sj)
			{
                tj++;
                if (tj > 1 && System.currentTimeMillis() - time > 500)
					tj = 1;
                if (tj == 1)
					time = System.currentTimeMillis();

                if (tj == 3)
                {
                    tj = 0;
                    if (System.currentTimeMillis() - time < 500)
                    {
                        if (player.isPlaying())
                        {
                            player.pause();
							sjj=true;
						}
						else
						{
                            player.start();
							sjj=false;
						}
                    }
                }
            }

            super.onTouchEvent(event);
        }



		

		public void qc()
		{

			if (player != null)
			{
				if (player.isPlaying())
					player.stop();
				player.setSurface(null);
				player.reset();
                player.release();
			
                player = null;

			}

		}
		public  void ks()
		{
			qc();
			player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setLooping(true);
			
            player.setSurface(Holder.getSurface());

            try
            {
                SharedPreferences xr2=ww.getSharedPreferences("yyc", MODE_MULTI_PROCESS);
                dz = xr2.getString("uri", "null");          
                sy = xr2.getBoolean("sy", false);
                cx = xr2.getBoolean("cx", false);
                sj = xr2.getBoolean("sj", false);

                if (dz.equals("null"))
                {
                    Toast.makeText(ww, "还没设置视频？先到我的视频设置吧！", Toast.LENGTH_SHORT).show();

                }


				if (dz.equals("yycsjsjsjsj"))
				{
					File ff=new File(Environment.getExternalStorageDirectory() + "/星空视频壁纸/cache/");
			        File[] f=ff.listFiles();
					int i=(int)(Math.random() * f.length);
					String s=f[i].getName();
					s = s.replace(".xki", "");
					player.setDataSource(Environment.getExternalStorageDirectory() + "/星空视频壁纸/" + s);

				}
				else
					player.setDataSource(dz);
                if (!sy)
                {
					player.setAudioStreamType(AudioManager.STREAM_SYSTEM);
					player.setVolume(0f, 0f);
                }
                player.prepare();
                player.start();

            }
            catch (IllegalArgumentException e)
            {}
            catch (IOException e)
            {}
            catch (IllegalStateException e)
            {}
			player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra)
					{
						i = 1;
						return false;
					}
				});
		}

        public void surfaceCreated(SurfaceHolder arg0)
        {

            Holder = arg0;
            super.onSurfaceCreated(arg0);
			ks();
        }
        public void surfaceDestroyed(SurfaceHolder arg0)
        {
            super.onSurfaceDestroyed(arg0);

			qc();
        }

        @Override
        public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4)
        {
            // TODO: Implement this method
        }


		
        public void onVisibilityChanged(boolean visible)
        {
            super.onVisibilityChanged(visible);

            if (visible)//如果可见
            {

				if (i == 1)
				{
					ks();
					i = 0;
				}
				else
				{
					if (player != null && !player.isPlaying()&&!(sj&&sjj))
					{
						
                        player.start();
			
						

					}
					SharedPreferences xr2=ww.getSharedPreferences("yyc", MODE_MULTI_PROCESS);
					if (!xr2.getString("uri", "null").equals(dz) || cx != xr2.getBoolean("cx", false) || sy != xr2.getBoolean("sy", false) )
					{

						ks();                   
						Toast.makeText(ww, "更新设置成功", Toast.LENGTH_SHORT).show();
					}
					if(sj != xr2.getBoolean("sj", false))
					{
						sjj=false;
						Toast.makeText(ww, "更新设置成功", Toast.LENGTH_SHORT).show();
						ks();
					}
				 }
            }
            else//如果不可见
            {
				if (i == 1)
					qc();
				else
				{
					if (player != null && player.isPlaying())
					{
						if (dz.equals("yycsjsjsjsj")&&!(sj&&sjj))
						{
							ks();
						}

						if (cx)
							player.seekTo(0);
					
							player.pause();
						
					}
				}
            }
        }
    }
}



