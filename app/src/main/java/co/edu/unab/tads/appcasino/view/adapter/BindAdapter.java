package co.edu.unab.tads.appcasino.view.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindAdapter {
    @BindingAdapter("image")
    public static void loadImage(ImageView imageView, String url){
        if(url.equals("")){
            Glide.with(imageView.getContext()).load("https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Imagen_no_disponible.svg/1200px-Imagen_no_disponible.svg.png").into(imageView);
        }else
        {
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }

    }
}

