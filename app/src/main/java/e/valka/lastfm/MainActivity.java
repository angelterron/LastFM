package e.valka.lastfm;

import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PagerAdapter pa = new PagerAdapter(getSupportFragmentManager());
        ViewPager vp = findViewById(R.id.viewpager);
        vp.setAdapter(pa);

    }


}
