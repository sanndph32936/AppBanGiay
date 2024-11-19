package com.example.vergencyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vergencyshop.Adapter.HoaDonChiTietAdapter;
import com.example.vergencyshop.fragment.LichSuMuaHangFragment;
import com.example.vergencyshop.models.GioHang;
import com.example.vergencyshop.models.HoaDon;
import com.example.vergencyshop.models.HoaDonChiTiet;
import com.example.vergencyshop.models.NguoiDung;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    TextView tvIdHoaDonChiTiet ,tvNgayTaoHoaDonChiTiet,tvKhachHangHoaDonChiTiet,tvSoDienThoaiHoaDonChiTiet,tvDiaChiHoaDonChiTiet;
    RecyclerView rcHoaDonChiTiet ;
    ImageView img_backTo_hdct;
    TextView tvPhiVanChuyenHoaDonChiTiet , tvTongTienHoaDonChiTiet;
    Button btnHuyHoaDon ;
    DatabaseReference reference = FirebaseDatabase .getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    HoaDonChiTietAdapter hoaDonChiTietAdapter ;
    ArrayList<HoaDonChiTiet> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        Bundle bundle = getIntent().getExtras();
        HoaDon hoaDon = (HoaDon) bundle.get("hoadon");
        if (bundle == null){
            return;
        }
        anhXa();
        setGiaTri(hoaDon);
img_backTo_hdct.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});
        rcHoaDonChiTiet.setLayoutManager(new LinearLayoutManager(this));

        hoaDonChiTietAdapter = new HoaDonChiTietAdapter(list,this);

        rcHoaDonChiTiet.setAdapter(hoaDonChiTietAdapter);


        if (hoaDon.getTrangThai() .equals("Chờ xác nhận")){
            btnHuyHoaDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    reference.child("HoaDon").child(hoaDon.getIdHD()).child("trangThai").setValue("Hủy");
                    Toast.makeText(ChiTietHoaDonActivity.this, "Hủy thành công", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            btnHuyHoaDon.setVisibility(View.GONE);
        }




    }

    private void anhXa() {
        tvIdHoaDonChiTiet = findViewById(R.id.tvIdHoaDonChiTiet) ;
        tvNgayTaoHoaDonChiTiet = findViewById(R.id.tvNgayTaoHoaDonChiTiet) ;
        tvKhachHangHoaDonChiTiet = findViewById(R.id.tvKhachHangHoaDonChiTiet);
        tvSoDienThoaiHoaDonChiTiet = findViewById(R.id.tvSoDienThoaiHoaDonChiTiet);
        tvDiaChiHoaDonChiTiet = findViewById(R.id.tvDiaChiHoaDonChiTiet);
        //RC
        rcHoaDonChiTiet = findViewById(R.id.rcHoaDonChiTiet) ;
        //Gia Tien
        tvPhiVanChuyenHoaDonChiTiet = findViewById(R.id.tvPhiVanChuyenHoaDonChiTiet) ;
        tvTongTienHoaDonChiTiet = findViewById(R.id.tvTongTienHoaDonChiTiet);
//thoát
        img_backTo_hdct = findViewById(R.id.img_backTo_hdct);
        //btn huy
        btnHuyHoaDon = findViewById(R.id.btnHuyHoaDon);



    }

    private  void setGiaTri (HoaDon hoaDon) {

        tvIdHoaDonChiTiet.setText("ID hóa đơn: " + hoaDon.getIdHD());
        tvNgayTaoHoaDonChiTiet.setText("Ngày mua: " + hoaDon.getNgayMua());

        reference.child("NguoiDung").child(hoaDon.getIdND()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);

                tvKhachHangHoaDonChiTiet.setText("Tên người dùng: " + nguoiDung.getTen());
                tvSoDienThoaiHoaDonChiTiet.setText("Số điện thoại: " + nguoiDung.getSoDienThoai());
                tvDiaChiHoaDonChiTiet.setText("Địa chỉ: " + nguoiDung.getDiaChi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query query = reference.child("HoaDonChiTiet").orderByChild("idHD").equalTo(hoaDon.getIdHD());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        HoaDonChiTiet hoaDonChiTiet = dataSnapshot.getValue(HoaDonChiTiet.class);
                        list.add(hoaDonChiTiet);

                    }
                    hoaDonChiTietAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int thanhtien = Integer.parseInt(hoaDon.getThanhTien());
        if (thanhtien < 300000) {
            tvPhiVanChuyenHoaDonChiTiet.setText("Tiền ship: " + numberFormat.format(20000));
            tvTongTienHoaDonChiTiet.setText("Thành tiền: " + numberFormat.format(thanhtien + 20000));
        } else {
            tvPhiVanChuyenHoaDonChiTiet.setText("Tiền ship: " + numberFormat.format(0));
            tvTongTienHoaDonChiTiet.setText("Thành tiền: " + numberFormat.format(thanhtien));
        }
    }
    }
