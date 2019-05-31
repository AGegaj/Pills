package org.unipr.pills.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.unipr.pills.R;

import java.util.ArrayList;

import org.unipr.pills.adapter.PillboxAdapter;
import org.unipr.pills.database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import org.unipr.pills.model.PillBoxDataResult;

public class PillboxFragment extends Fragment {

    RecyclerView recyclerView;
    PillboxAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    private ArrayList<String> pillNameData = new ArrayList<String>();
    private ArrayList<Integer> pillPhotoData = new ArrayList<Integer>();
    private Database db;

    TextView tvPillName;
    CircleImageView imgPill;
    Toolbar toolbarPillbox;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pillbox, null);

        tvPillName = view.findViewById(R.id.txtPillName);
        imgPill = view.findViewById(R.id.imgPill);
        toolbarPillbox = view.findViewById(R.id.toolbarPillbox);


        initPillboxList();
        recyclerView = view.findViewById(R.id.recycler_view_pillbox);
        adapter = new PillboxAdapter(this.getContext(), pillPhotoData, pillNameData);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        toolbarPillbox.setTitle("Pillbox");

    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void initPillboxList() {
        try {
            db = new Database(getContext());
            ArrayList<PillBoxDataResult> pillBoxList = db.getActivePills();
            for (PillBoxDataResult pill : pillBoxList) {
                pillNameData.add(pill.getPillName());
                pillPhotoData.add(pill.getPhotoId());
            }

            db.close();
        } catch (Exception e) {
            System.err.println(e);
        }


    }

}
