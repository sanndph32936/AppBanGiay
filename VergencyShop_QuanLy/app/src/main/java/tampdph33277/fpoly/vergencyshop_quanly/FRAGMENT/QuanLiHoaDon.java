package tampdph33277.fpoly.vergencyshop_quanly.FRAGMENT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tampdph33277.fpoly.vergencyshop_quanly.ADAPTER.HoaDonAdapter;
import tampdph33277.fpoly.vergencyshop_quanly.DTO.HoaDon;
import tampdph33277.fpoly.vergencyshop_quanly.R;

public class QuanLiHoaDon extends Fragment {

    View view;
    TextView btnListChoXacNhan, btnListDangGiao, btnListDaGiao;
    RecyclerView rcLichSuMuaHang;
    HoaDonAdapter hoaDonAdapter;
    ArrayList<HoaDon> list = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quan_li_ho_don, container, false);

        anhXa();

        xuatDanhSachHoaDon("Chờ Xác Nhận");
        hoaDonAdapter = new HoaDonAdapter(list, getActivity());

        rcLichSuMuaHang.setLayoutManager(new LinearLayoutManager(getActivity()));


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


        return view;
    }

    private void anhXa() {
        rcLichSuMuaHang = view.findViewById(R.id.rcLichSuMuaHang);
        btnListChoXacNhan = view.findViewById(R.id.btnListChoXacNhan);
        btnListDangGiao = view.findViewById(R.id.btnListDangGiao);
        btnListDaGiao = view.findViewById(R.id.btnListDaGiao);

    }

    private void xuatDanhSachHoaDon(String trangThai) {
        Query query = reference.child("HoaDon");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);
                        if (hoaDon.getTrangThai().equals(trangThai)) {
                            list.add(hoaDon);
                        }

                    }

                    hoaDonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
