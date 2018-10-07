package e.valka.lastfm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import e.valka.lastfm.Models.Artist;
import e.valka.lastfm.Models.ArtistHeader;
import e.valka.lastfm.Models.Artists;
import e.valka.lastfm.Utils.DownloadAsyncTask;
import e.valka.lastfm.Utils.URLS;

public class ArtistFragment extends Fragment{
    ArrayList<Artist> artistsList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);
        recyclerView = v.findViewById (R.id.baseApiList);
        recyclerView.setLayoutManager (new LinearLayoutManager(getActivity ()));
        new DownloadAsyncTask(this::parseJSON).execute (URLS.LASTFM_API_ARTIST_URL);
        return v;
    }
    private void parseJSON (String json) {
        ArtistHeader artists = new Gson().fromJson (json, ArtistHeader.class);
        if (artists == null) return;
        artistsList.addAll (artists.artists.artist);
        recyclerView.setAdapter (new artistAdapter(artistsList));
    }
    class artistAdapter extends RecyclerView.Adapter<artistAdapter.artistViewHolder> {

        private ArrayList<Artist> data;

        artistAdapter (ArrayList<Artist> d) {
            data = d;
        }

        @NonNull
        @Override
        public artistViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
            View view = inflater.inflate (R.layout.list_item, parent, false);

            return new artistViewHolder (view);
        }

        @Override
        public void onBindViewHolder (@NonNull artistViewHolder holder, int position) {
            Artist artist = data.get (position);
            holder.setData (artist.image.get(3).text,artist.name,artist.playcount);
        }

        @Override
        public int getItemCount () {
            return data.size ();
        }

        class artistViewHolder extends RecyclerView.ViewHolder {
            ImageView imagen,icono1,icono2;
            TextView texto,texto2;

            artistViewHolder (View itemView) {
                super (itemView);
                texto2 = itemView.findViewById(R.id.texto2);
                icono2 = itemView.findViewById(R.id.icono2);
                icono1 = itemView.findViewById(R.id.icono1);
                texto = itemView.findViewById(R.id.texto);
                imagen = itemView.findViewById(R.id.imagen);
            }

            void setData (String data1,String data2,int data3) {
                Picasso.get().load(R.drawable.ic_play).resize(150,150).into(icono2);
                Picasso.get().load(R.drawable.ic_listener).resize(150,150).into(icono1);
                texto2.setText(""+data3);
                texto.setText(data2);
                Picasso.get().load(data1).resize(1200,1100).centerInside().into(imagen);
            }
        }
    }

}
