package com.example.vergencyshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vergencyshop.Adapter.HoaDonAdapter;
import com.example.vergencyshop.R;
import com.example.vergencyshop.models.HoaDon;
import com.example.vergencyshop.models.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HoaDonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HoaDonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HoaDonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiTietSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HoaDonFragment newInstance(String param1, String param2) {
        HoaDonFragment fragment = new HoaDonFragment();
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
    View view ;
    RecyclerView rcHoaDon;
    SearchView sv_date;
    HoaDonAdapter hoaDonAdapter ;
    ArrayList<HoaDon> list = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_hoadon, container, false);

        anhXa();

        setListHoaDon();

        hoaDonAdapter = new HoaDonAdapter(list,getActivity());
        rcHoaDon.setLayoutManager(new LinearLayoutManager(getActivity()));

        rcHoaDon.setAdapter(hoaDonAdapter);
sv_date.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        list.clear();
        if (newText.length() > 0) {
            queryFirebaseForSearch(newText);  // Truy vấn Firebase
        } else {
            hoaDonAdapter.filterList_hoadon(list);  // Hiển thị lại toàn bộ danh sách sản phẩm nếu không có từ khóa tìm kiếm
        }

        return false;
    }
});

        // Inflate the layout for this fragment
        return view ;
    }

    private  void anhXa (){

        sv_date = view.findViewById(R.id.sv_date);
        rcHoaDon = view.findViewById(R.id.rc_danhsachhoadon);
    }

    private void setListHoaDon (){
       Query query = reference.child("HoaDon").orderByChild("idND").equalTo(user.getUid());
       query .addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               if (snapshot.exists()){
                   list.clear();
                   for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                       list.add(dataSnapshot.getValue(HoaDon.class));

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
    private void queryFirebaseForSearch(String keyword) {
        Query searchQuery = reference.child("HoaDon").orderByChild("ngayMuaLowerCase");

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    HoaDon hoaDon = childSnapshot.getValue(HoaDon.class);
                    String lowercasehoadon = hoaDon.getNgayMua().toLowerCase();
                    if (hoaDon != null && hoaDon.getIdND().equals(user.getUid()) && lowercasehoadon.contains(keyword.toLowerCase())) {
                        list.add(hoaDon);
                    }
                }
                hoaDonAdapter.filterList_hoadon(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý  lỗi
            }
        });
    }
}