package com.example.finalstand;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GPFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String[] titles = {"qmatid", "qmaing", "djfklas"};
    private static final String[] descs = {"hehehsiuuu", "siuuuuuuu\nuuuu", "aahhhhhhhhhhhhhh"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static RecyclerView recyclerView;
    private static GPAdapter GPA;
    private static ArrayList<GPEntryModel> data;

    public GPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GPFragment newInstance(String param1, String param2) {
        GPFragment fragment = new GPFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data = new ArrayList<GPEntryModel>();

        //get info from json
        //String[][] GPData = readJSON();

        //populate data
        for (int i = 0; i < titles.length; i++) {
            GPEntryModel GPE = new GPEntryModel(titles[i], descs[i], i);
            data.add(GPE);
        }

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gp, container, false);

        GPA = new GPAdapter(data);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclingGP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(GPA);

        return v;
    }

    //---------------json stuff--------------------
    private String[][] readJSON() {
        try {
            JSONObject json = new JSONObject(loadJSON());
            JSONArray jArray = json.getJSONArray("constructors");
            String[][] contentArray = new String[jArray.length()][3];

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jOInside = jArray.getJSONObject(i);
                contentArray[i][0] = jOInside.getString("name");
                contentArray[i][1] = jOInside.getString("desc");
                contentArray[i][2] = jOInside.getString("pic");
            }
            return contentArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadJSON() {
        String json;
        try {
            InputStream iS = getContext().getAssets().open("constructing.json");
            int size = iS.available();
            byte[] buffer = new byte[size];
            iS.read(buffer);
            iS.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}