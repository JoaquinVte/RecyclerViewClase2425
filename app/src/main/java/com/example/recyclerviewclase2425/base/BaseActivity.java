package com.example.recyclerviewclase2425.base;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.recyclerviewclase2425.API.Connector;


public class BaseActivity extends AppCompatActivity {

    protected Connector connector;
    protected ExecutorService executor = Executors.newSingleThreadExecutor();
    //protected Handler handler = new Handler(Looper.getMainLooper());
    protected MyProgressBar progressBar;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connector = Connector.getConector();
        progressBar = new MyProgressBar(this);
        context=this;
    }

    protected <T> void executeCall(CallInterface<T> callInterface){
        showProgress();
        (new AsyncTask<Void,Void,T>(){
            @Override
            protected T doInBackground(Void... voids) {
                try {
                    return callInterface.doInBackground();
                }catch (Exception e){
                    callInterface.doInError(context,e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                hideProgress();
                if(t!=null)
                    callInterface.doInUI(t);
            }
        }).execute();
    }

//    protected <T> void executeCall(CallInterface<T> callInterface){
//        executor.execute(() -> {
//            try {
//                showProgress();
//                T data = callInterface.doInBackground();
//                this.runOnUiThread(() -> {
//                    hideProgress();
//                    callInterface.doInUI(data);
//                });
//            } catch (Exception e){
//                this.runOnUiThread(() -> {
//                    hideProgress();
//                    callInterface.doInError(BaseActivity.this,e);
//                });
//            }
//        });
//    }

    public void showProgress(){
        progressBar.show();
    }

    public void hideProgress(){
        progressBar.hide();
    }


    // Sobreescribimos el metodo para asociar a la barra de progreso al ContraintLayout o RelativeLayout
    // y asi poder centrarla y manipular la visibilidad del resto de componentes del ViewGroup
    @Override
    public void setContentView(int layout){
        super.setContentView(layout);
        ViewGroup rootView = (ViewGroup) ((ViewGroup) this .findViewById(android.R.id.content)).getChildAt(0);
        progressBar.initControl(rootView);
        hideProgress();
    }

}
