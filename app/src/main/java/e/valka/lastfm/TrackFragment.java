package e.valka.lastfm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
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
import e.valka.lastfm.Models.Track;
import e.valka.lastfm.Models.TracksHeader;
import e.valka.lastfm.Utils.DownloadAsyncTask;
import e.valka.lastfm.Utils.URLS;

public class TrackFragment extends Fragment {
    ArrayList<Track> trackList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base_2, container, false);
        recyclerView = v.findViewById (R.id.baseApiList);
        recyclerView.setLayoutManager (new GridLayoutManager(getActivity(),2));
        new DownloadAsyncTask(this::parseJSON).execute (URLS.LASTFM_API_TRACKS_URL);
        return v;
    }
    private void parseJSON (String json) {
        TracksHeader tracks = new Gson().fromJson (json, TracksHeader.class);
        if (tracks == null) return;
        trackList.addAll (tracks.tracks.track);
        recyclerView.setAdapter (new trackAdapter(trackList));
    }
    class trackAdapter extends RecyclerView.Adapter<trackAdapter.trackViewHolder> {

        private ArrayList<Track> data;

        trackAdapter (ArrayList<Track> d) {
            data = d;
        }

        @NonNull
        @Override
        public trackViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
            View view = inflater.inflate (R.layout.list_item_2, parent, false);

            return new trackViewHolder(view);
        }

        @Override
        public void onBindViewHolder (@NonNull trackViewHolder holder, int position) {
            Track track = data.get (position);
            holder.setData (track.image.get(3).text,track.name);
        }

        @Override
        public int getItemCount () {
            return data.size ();
        }

        class trackViewHolder extends RecyclerView.ViewHolder {
            ImageView imagen;
            TextView texto;

            trackViewHolder (View itemView) {
                super (itemView);
                texto = itemView.findViewById(R.id.texto);
                imagen = itemView.findViewById(R.id.imagen);
            }

            void setData (String data1,String data2) {
                texto.setText(data2);
                Picasso.get().load(data1).resize(1200,1100).centerInside().into(imagen);
            }
        }
    }
}
