package e.valka.lastfm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import e.valka.lastfm.Models.Track;

public class PagerAdapter extends FragmentPagerAdapter {
    private final String[] titulos = { "Artistas", "Canciones"};
    public static final String index ="index";
    public PagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        if(i == 0) {
            return new ArtistFragment();
        }else{
            return new TrackFragment();
        }
    }

    @Override
    public int getCount() {
        return titulos.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }

}

