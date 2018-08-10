package me.tatiyanupanwong.supasin.samples.android.viewbinding;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.tatiyanupanwong.supasin.android.viewbinding.BindView;
import me.tatiyanupanwong.supasin.android.viewbinding.ViewBindingUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewHolder views = new ViewHolder(this);
        views.textView.setText(R.string.app_name);
    }

    private static final class ViewHolder {
        @BindView(R.id.textView)
        TextView textView;

        ViewHolder(Activity activity) {
            ViewBindingUtils.bind(this, activity);
        }
    }
}
