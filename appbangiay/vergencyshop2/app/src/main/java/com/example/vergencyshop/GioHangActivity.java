package com.example.vergencyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vergencyshop.Adapter.GioHangAdapter;

import com.example.vergencyshop.models.GioHang;
import com.example.vergencyshop.models.HoaDon;
import com.example.vergencyshop.models.HoaDonChiTiet;
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

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Queue;


public class GioHangActivity extends AppCompatActivity {

    RecyclerView rc_giohang;

    TextView tv_tongtien;
    LinearLayout tv_muahang;
    ImageView btn_backToMain;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    ArrayList<GioHang> list = new ArrayList<>();
    GioHang gioHang = new GioHang();

    GioHangAdapter gioHangAdapter;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        rc_giohang = findViewById(R.id.rcGioHang);
        tv_tongtien = findViewById(R.id.tv_tongtien);
        tv_muahang = findViewById(R.id.btnMuaHangGioHang);
btn_backToMain = findViewById(R.id.img_backToMain);

btn_backToMain.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rc_giohang.setLayoutManager(new LinearLayoutManager(this));

        gioHangAdapter = new GioHangAdapter(this, list);

        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("GioHang");

        rc_giohang.setAdapter(gioHangAdapter);
        // activity lấy giá tổng
        gioHangAdapter.setTongTienTextView(tv_tongtien);
        Query query = cartRef.orderByChild("idNguoiDung").equalTo(user.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        list.add(snapshot1.getValue(GioHang.class));
                    }

                    Locale locale = new Locale("vi", "VN");
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
                    Currency currency = Currency.getInstance(locale);
                    String formattedGiaSanPham = currencyFormat.format(Double.parseDouble(tinhtongtien()));
                    tv_tongtien.setText(formattedGiaSanPham);
                }
                if (list.isEmpty()) {
                    tv_tongtien.setText("0");
                }

                gioHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        tv_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tongTien = tv_tongtien.getText().toString();
                if (tongTien.equals("0") || list.isEmpty()) {
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng đang trống hãy thêm sản phẩm vào giỏ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    setHoaDon();
                }
            }
        });
    }
        private String tinhtongtien () {
        int tongTien = 0;

        for (GioHang gioHang1 : list) {
                int gia1sp = Integer.parseInt(gioHang1.getGiaSP()) /Integer.parseInt(gioHang1.getSoluongSP());
                int giatri = gia1sp * Integer.parseInt(gioHang1.getSoluongSP());
                tongTien = giatri + tongTien;


        }
        return String.valueOf(tongTien);
    }
        private void setHoaDon () {
        Intent intent = new Intent(GioHangActivity.this, ThanhToanSanPham.class);
        startActivity(intent);


    }

}