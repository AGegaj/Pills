package org.unipr.pills.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.unipr.pills.R;

import java.util.ArrayList;

import org.unipr.pills.adapter.InactiveMedsAdapter;
import org.unipr.pills.adapter.PillboxAdapter;
import org.unipr.pills.database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import org.unipr.pills.model.PillBoxDataResult;

public class PillboxFragment extends Fragment {

    RecyclerView recyclerView, inActiveRecyclerView;
    PillboxAdapter adapter;
    InactiveMedsAdapter inactiveMedsAdapter;
    RecyclerView.LayoutManager layoutManager, inActiveLayoutManager;


    private ArrayList<String> pillNameData = new ArrayList<String>();
    private ArrayList<Integer> pillPhotoData = new ArrayList<Integer>();
    private ArrayList<String> inActivePillNameData = new ArrayList<String>();
    private ArrayList<Integer> inActivePillPhotoData = new ArrayList<Integer>();
    private Database db;

    TextView tvPillName;
    CircleImageView imgPill;
    Toolbar toolbarPillbox;
    CardView inActiveCard;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pillbox, null);

        tvPillName = view.findViewById(R.id.txtPillName);
        imgPill = view.findViewById(R.id.imgPill);
        toolbarPillbox = view.findViewById(R.id.toolbarPillbox);
        inActiveCard = view.findViewById(R.id.card_pillboxInactive);

        initPillboxList();
        recyclerView = view.findViewById(R.id.recycler_view_pillbox);
        adapter = new PillboxAdapter(this.getContext(), pillPhotoData, pillNameData);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        initInactivePillboxList();
        if(inActivePillNameData.isEmpty())
            inActiveCard.setVisibility(View.GONE);
        inActiveRecyclerView = view.findViewById(R.id.recycler_view_pillboxInactive);
        inactiveMedsAdapter = new InactiveMedsAdapter(this.getContext(), inActivePillPhotoData, inActivePillNameData);
        inActiveRecyclerView.setAdapter(inactiveMedsAdapter);
        inActiveLayoutManager = new LinearLayoutManager(getActivity());
        inActiveRecyclerView.setLayoutManager(inActiveLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        toolbarPillbox.setTitle(R.string.title_pillbox);

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

    private void initInactivePillboxList() {
        try {
            db = new Database(getContext());
            ArrayList<PillBoxDataResult> pillBoxList = db.getInActivePills();
            for (PillBoxDataResult pill : pillBoxList) {
                inActivePillNameData.add(pill.getPillName());
                inActivePillPhotoData.add(pill.getPhotoId());
            }

            db.close();
        } catch (Exception e) {
            System.err.println(e);
        }


    }

}
