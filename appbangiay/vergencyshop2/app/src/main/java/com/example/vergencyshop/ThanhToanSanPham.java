package com.example.vergencyshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vergencyshop.Adapter.ThanhToanAdapter;
import com.example.vergencyshop.models.GioHang;
import com.example.vergencyshop.models.HoaDon;
import com.example.vergencyshop.models.HoaDonChiTiet;
import com.example.vergencyshop.models.NguoiDung;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class ThanhToanSanPham extends AppCompatActivity {
    TextView tvTenSDtThanhToan , tvDiaChiThanhToan , tvTongThanhToanHoaDon ;
    LinearLayout btnMuaHang;
    RecyclerView rcSanPhamThanhToan ;
    RadioButton rdNhanHangThanhToan,  rdBankingThanhToan ;
    ThanhToanAdapter thanhToanAdapter;
    ImageView img_backToMain;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();





    ArrayList<GioHang> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_san_pham);


        anhXa();
        setList();
        setThongTin();
        rdBankingThanhToan.setChecked(true);
        rcSanPhamThanhToan.setLayoutManager(new LinearLayoutManager(this ));
        thanhToanAdapter = new ThanhToanAdapter(list,this);
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMuaHang();
            }
        });
        rcSanPhamThanhToan.setAdapter(thanhToanAdapter);
        img_backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setMuaHang() {

        
        reference.child("NguoiDung").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);


                    if (nguoiDung.getTen() == null||
                    nguoiDung.getGioiTinh()== null||
                    nguoiDung.getSoDienThoai()== null||
                    nguoiDung.getDiaChi()== null){

                        Toast.makeText(ThanhToanSanPham.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();


                    }else {
                        // Set giờ
                        // Tạo đối tượng SimpleDateFormat với định dạng mong muốn
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        // Lấy thời gian hiện tại
                        Date currentTime = new Date();

                        // Định dạng thời gian hiện tại theo định dạng đã cho
                        String formattedTime = dateFormat.format(currentTime);
                        String idHD = reference.push().getKey();
                        String idND = user.getUid();
                        String thanhTien = "0";
                        String ngayMua = formattedTime;
                        String phuongThuc = setPhuongThucThanhToan();
                        String trangThai ="Chờ Xác Nhận";
                        int tien = 0;
                        for (GioHang hang: list){
                            String idHDCT = idHD;
                            String idSP =   hang.getIdSP() ;
                            String soLuong = hang.getSoluongSP();
                            String tongTien = hang.getGiaSP() ;
                            String anhSP = hang.getAnhSP() ;
                            String sizeSP = hang.getSizeSP();


                            tien =tien + Integer.parseInt(tongTien);
                            thanhTien = String.valueOf(tien);
                            Toast.makeText(ThanhToanSanPham.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(idHDCT,idSP,soLuong,tongTien,anhSP,sizeSP);
                            reference.child("HoaDonChiTiet").push().setValue(hoaDonChiTiet);

                        }
                        HoaDon hoaDon = new HoaDon(idHD,idND,thanhTien,ngayMua,phuongThuc,trangThai);
                        reference.child("HoaDon").child(idHD)
                                .setValue(hoaDon);


                        //Xóa giỏ hàng


                        Intent intent = new Intent(ThanhToanSanPham.this , MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                    
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private  void anhXa(){
        img_backToMain = findViewById(R.id.img_backToMain);
        tvTenSDtThanhToan = findViewById(R.id.tvTenSDtThanhToan);
        tvDiaChiThanhToan = findViewById(R.id.tvDiaChiThanhToan);
        tvTongThanhToanHoaDon = findViewById(R.id.tvTongThanhToanHoaDon);
        btnMuaHang = findViewById(R.id.btnMuaHangThanhToan);
        rcSanPhamThanhToan = findViewById(R.id.rcSanPhamThanhToan);
        rdBankingThanhToan = findViewById(R.id.rdBankingThanhToan);
        rdNhanHangThanhToan = findViewById(R.id.rdNhanHangThanhToan);
    }


    private String setPhuongThucThanhToan (){

        if (rdBankingThanhToan.isChecked()){
            return "bank";
        }else {
            return "tien";
        }
    }

    private void setThongTin (){
        reference.child("NguoiDung").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);
                   if (nguoiDung.getTen() == null || nguoiDung.getSoDienThoai() == null){
                       tvTenSDtThanhToan.setText("");
                       tvDiaChiThanhToan.setText("");
                   }else {
                       tvTenSDtThanhToan.setText(nguoiDung.getTen().toUpperCase()+"\n"+nguoiDung.getSoDienThoai().toUpperCase());
                       tvDiaChiThanhToan.setText(nguoiDung.getDiaChi().toUpperCase());
                   }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setList(){
        Query query = reference.child("GioHang").orderByChild("idNguoiDung").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        int tongTien = 0 ;
                        int gia1sp = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            GioHang hang = dataSnapshot.getValue(GioHang.class);
                            list.add(hang);
                            gia1sp = Integer.parseInt(hang.getGiaSP())/Integer.parseInt(hang.getSoluongSP());
                            tongTien +=  Integer.parseInt(hang.getGiaSP()) ;
                                    //gia1sp * Integer.parseInt(hang.getSoluongSP());
                             }
                        if (tongTien <= 300000){
                            tvTongThanhToanHoaDon.setText(String.valueOf(tongTien+=20000));
                        } else {
                            tvTongThanhToanHoaDon.setText(String.valueOf(tongTien));
                        }
                        Locale locale = new Locale("vi", "VN");
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
                        Currency currency = Currency.getInstance(locale);

                        String formattedGiaSanPham = currencyFormat.format(Double.parseDouble(String.valueOf(tongTien)));
                        tvTongThanhToanHoaDon.setText(formattedGiaSanPham);
                        thanhToanAdapter.notifyDataSetChanged();
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}