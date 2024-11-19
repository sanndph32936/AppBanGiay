package com.example.vergencyshop;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vergencyshop.fragment.DanhMucFragment;
import com.example.vergencyshop.fragment.DoiMatKhauFragment;
import com.example.vergencyshop.fragment.GioiThieuFragment;
import com.example.vergencyshop.fragment.HoaDonFragment;
import com.example.vergencyshop.fragment.LichSuMuaHangFragment;
import com.example.vergencyshop.fragment.ThongTinNguoiDungFragment;
import com.example.vergencyshop.fragment.TopSanPhamFragment;
import com.example.vergencyshop.fragment.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    public static final int MY_REQUEST_CODE = 10 ;
    DrawerLayout drawerLayout ;
    Toolbar toolbar ;
    NavigationView navigationView ;
    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog ;
    TextView tvUser;
    ImageView imgAvt ;
    DatabaseReference reference = FirebaseDatabase .getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private final ThongTinNguoiDungFragment thongTinNguoiDungFragment  = new ThongTinNguoiDungFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        drawerLayout = findViewById(R.id.layout_all);
        toolbar = findViewById(R.id.jl_toolbar);
        navigationView = findViewById(R.id.main_navigation_view01);
        bottomNavigationView = findViewById(R.id.jl_btton_nav);
        imgAvt = navigationView.getHeaderView(0).findViewById(R.id.imgAvt);
         tvUser = navigationView.getHeaderView(0).findViewById(R.id.tvUser);
        setThongTin();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(MainActivity.this , drawerLayout , toolbar , R.string.open,R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        callFragment(new TrangChuFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                progressDialog.show();
                if (item.getItemId() == R.id.nav_trangchu){

                    callFragment(new TrangChuFragment());
                    toolbar.setTitle("Trang chủ");
                }
                if (item.getItemId() == R.id.nav_danhmuc){

                    callFragment(new DanhMucFragment());
                    toolbar.setTitle("Danh mục");
                }

                if (item.getItemId() == R.id.nav_giohang){
                    startActivity(new Intent(getApplicationContext(),GioHangActivity.class));
                }
                if (item.getItemId() == R.id.sub_Top){

                    callFragment(new TopSanPhamFragment());
                    toolbar.setTitle("Sản phẩm bán chạy");
                }
                if (item.getItemId() == R.id.nav_HoSo){

                    callFragment(thongTinNguoiDungFragment);
                    toolbar.setTitle("Thông tin khách hàng");
                }
                if (item.getItemId() == R.id.nav_HoaDon){

                    callFragment(new HoaDonFragment());
                    toolbar.setTitle("Hóa đơn");
                }
                if (item.getItemId() == R.id.nav_lichsumuahang){
                    progressDialog.dismiss();
                    callFragment(new LichSuMuaHangFragment());
                    toolbar.setTitle("Lịch sử mua hàng");
                }

                if (item.getItemId() == R.id.sub_Logout){

                  Logout();
                }
                if (item.getItemId() == R.id.sub_Support){
                    callFragment(new GioiThieuFragment());
                    toolbar.setTitle("Hỗ trợ");
                }
                if (item.getItemId() == R.id.sub_Pass){

                    callFragment(new DoiMatKhauFragment());
                    toolbar.setTitle("Đổi mật khẩu");
                }

                return false;
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bt_trangchu){
                    callFragment(new TrangChuFragment());
                    toolbar.setTitle("Trang chủ");
                }
                if (item.getItemId() == R.id.bt_banchay){
                    callFragment(new TopSanPhamFragment());
                    toolbar.setTitle("Sản Phẩm bán chạy");
                }
                if (item.getItemId() == R.id.bt_danhmuc){
                    callFragment(new DanhMucFragment());
                    toolbar.setTitle("Danh mục");
                }

                if (item.getItemId() == R.id.bt_nguoidung){
                    callFragment(new ThongTinNguoiDungFragment());
                    toolbar.setTitle("Hồ sơ");
                }

                return true;
            }
        });



    }

    private void callFragment (Fragment fragment){
        progressDialog.dismiss();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.jl_fragment,fragment).commit();
        drawerLayout.close();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
            progressDialog.dismiss();
        }
    }


    private void setThongTin (){
            reference.child("NguoiDung").child(user.getUid()).child("ten").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null){
                        tvUser.setText(null);
                    }else {
                        tvUser.setText(snapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        reference.child("NguoiDung").child(user.getUid()).child("anh").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    Glide.with(MainActivity.this).load(snapshot.getValue().toString()).into(imgAvt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
public void Logout(){
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("Thông báo");
    builder.setMessage("Bạn có muốn đăng xuất tài khoản không ?");
    builder.setCancelable(false);
    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this,DangNhap.class);
            startActivity(intent);
            finishAffinity();
        }
    });
    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
}

}
