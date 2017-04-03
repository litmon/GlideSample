package com.litmon.app.glidesample;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.litmon.app.glidesample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.reload.setOnClickListener(v -> reloadImages());
        binding.setBackground.setOnClickListener(v -> setBackgrounds());
        binding.reset.setOnClickListener(v -> resetImages());

        binding.withoutAnimation.textView.setText(R.string.load_without_animation);
        binding.withAccelerate.textView.setText(R.string.load_with_accelerate);
        binding.withDecelerate.textView.setText(R.string.load_with_decelerate);
        binding.withAccelerateDecelerate.textView.setText(R.string.load_with_accelerate_decelerate);
        binding.withLinear.textView.setText(R.string.load_with_linear);
        binding.withCrossFade.textView.setText(R.string.load_with_cross_fade);

        binding.durationSeekBar.setMax(100);
        binding.durationSeekBar.setProgress(10);
        binding.durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.durationText.setText(String.valueOf(getDuration()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setBackgrounds() {
        binding.withoutAnimation.imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder));
        binding.withAccelerate.imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder));
        binding.withDecelerate.imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder));
        binding.withAccelerateDecelerate.imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder));
        binding.withLinear.imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder));
        binding.withCrossFade.imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder));
    }

    private void reloadImages() {
        loadImageWithoutAnim(binding.withoutAnimation.imageView);
        loadImage(binding.withAccelerate.imageView, new AccelerateInterpolator());
        loadImage(binding.withDecelerate.imageView, new DecelerateInterpolator());
        loadImage(binding.withAccelerateDecelerate.imageView, new AccelerateDecelerateInterpolator());
        loadImage(binding.withLinear.imageView, new LinearInterpolator());
        loadImageWithCrossFade(binding.withCrossFade.imageView);
    }

    private void resetImages() {
        binding.withoutAnimation.imageView.setImageDrawable(null);
        binding.withAccelerate.imageView.setImageDrawable(null);
        binding.withDecelerate.imageView.setImageDrawable(null);
        binding.withAccelerateDecelerate.imageView.setImageDrawable(null);
        binding.withLinear.imageView.setImageDrawable(null);
        binding.withCrossFade.imageView.setImageDrawable(null);

        ViewCompat.setBackground(binding.withoutAnimation.imageView, null);
        ViewCompat.setBackground(binding.withAccelerate.imageView, null);
        ViewCompat.setBackground(binding.withDecelerate.imageView, null);
        ViewCompat.setBackground(binding.withAccelerateDecelerate.imageView, null);
        ViewCompat.setBackground(binding.withLinear.imageView, null);
        ViewCompat.setBackground(binding.withCrossFade.imageView, null);
    }

    private void loadImageWithCrossFade(ImageView target) {
        getRequest()
                .crossFade(getDuration())
                .into(target);
    }

    private void loadImageWithoutAnim(ImageView target) {
        getRequest()
                .dontAnimate()
                .into(target);
    }

    private void loadImage(final ImageView target, final Interpolator interpolator) {
        getRequest()
                .animate(view -> {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
                    animator.setDuration(getDuration());
                    animator.setInterpolator(interpolator);
                    animator.start();
                })
                .into(target);
    }

    private DrawableRequestBuilder<String> getRequest() {
        return Glide.with(this)
                .load("http://www.mojimaru.com/backmaker/img/oRAjnlaM-1.png?1491235721")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.color.placeholder);
    }

    private int getDuration() {
        return binding.durationSeekBar.getProgress() * 100;
    }
}
