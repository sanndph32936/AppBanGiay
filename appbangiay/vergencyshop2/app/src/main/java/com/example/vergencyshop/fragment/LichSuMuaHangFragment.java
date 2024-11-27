package com.example.vergencyshop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vergencyshop.Adapter.HoaDonAdapter;
import com.example.vergencyshop.R;
import com.example.vergencyshop.models.HoaDon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichSuMuaHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichSuMuaHangFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LichSuMuaHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichSuMuaHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichSuMuaHangFragment newInstance(String param1, String param2) {
        LichSuMuaHangFragment fragment = new LichSuMuaHangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    TextView btnListChoXacNhan , btnListDangGiao , btnListDaGiao , btnListDaHuy ;
    RecyclerView rcLichSuMuaHang;
    HoaDonAdapter hoaDonAdapter ;
    ArrayList<HoaDon> list = new ArrayList<>();
    View view;
    DatabaseReference reference = FirebaseDatabase .getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lich_su_mua_hang, container, false);
        anhXa();
        xuatDanhSachHoaDon("Chờ Xác Nhận");
        hoaDonAdapter = new HoaDonAdapter(list,getActivity() );

        rcLichSuMuaHang.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnListDaHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuatDanhSachHoaDon("Hủy");
            }
        });

        btnListChoXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuatDanhSachHoaDon("Chờ Xác Nhận");
            }
        });

        btnListDaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuatDanhSachHoaDon("Đã Giao");
            }
        });

        btnListDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuatDanhSachHoaDon("Đang Giao");
            }
        });

        rcLichSuMuaHang.setAdapter(hoaDonAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void xuatDanhSachHoaDon(String trangThai) {
        Query query =  reference.child("HoaDon").orderByChild("idND").equalTo(user.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);
                        if (hoaDon.getTrangThai() .equals(trangThai)){
                            list.add(hoaDon);
                        }

                    }
                    Collections.reverse(list);
                    hoaDonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private  void anhXa (){
        rcLichSuMuaHang = view.findViewById(R.id.rcLichSuMuaHang);
        btnListChoXacNhan = view.findViewById(R.id.btnListChoXacNhan);
        btnListDangGiao  = view.findViewById(R.id.btnListDangGiao) ;
        btnListDaGiao  = view.findViewById(R.id.btnListDaGiao) ;
        btnListDaHuy  = view.findViewById(R.id.btnListDaHuy) ;
    }
}