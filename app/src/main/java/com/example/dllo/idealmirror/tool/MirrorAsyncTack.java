package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 欢迎页面异步图片获取
 * Created by dllo on 16/4/15.
 */
public class MirrorAsyncTack extends AsyncTask<String,Integer,Bitmap>{
    //异步任务 AsyncTack
    //泛型里有三个参数,参数1:参与任务的参数类型,如String类型的url
    //参数2:后台执行任务的进度类型,一般为Void
    //参数3:执行任务的返回结果类型
    //有的时候写类型,没有的时候写Void,作为泛型使用时,基本数据类型使用包装类

    private Context context;



    public MirrorAsyncTack(Context context) {
        this.context = context;
    }

    /**
     * 执行耗时操作,run方法
     * @param params
     * @return
     * 第二部执行
     */



    /**
     * 后台任务启动前,相当于run方法启动前,在这个方法内做启动工作,
     * 准备
     * 之后执行Doinbackground
     * 首先执行
     */
    @Override
    protected void onPreExecute() {//可以和UI交互
        super.onPreExecute();

    }

    /**
     * 更新进度
     * 更新短笛方法是否被系统调用,需要使用publicshaProgress来控制
     * 参数类型,第二个参数
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);

    }

    /**
     * 发布后台任务执行结果,后台任务完毕时调用,类似于线程run方法执行完毕
     * @param bitmap
     */
    private ImageView iv;
    public void setIv(ImageView ivs){
        this.iv = ivs;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String param= params[0];
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(param);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is=  connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return  bitmap;

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                connection.disconnect();
            }
            if (is!=null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 第三部执行
     * @param bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        //关闭

        //使用后台任务获取的Bitmap
        if (bitmap!=null){
            LogUtils.d("sss");
            iv.setImageBitmap(bitmap);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        /*取消处理的方法*/
    }
}