package com.example.vergencyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vergencyshop.Adapter.SanPhamTrangChuAdapter;
import com.example.vergencyshop.models.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhMuc extends AppCompatActivity {

    int CODE_CHOSSE;

    RecyclerView rcSanPhamDanhMuc ;

    ImageView img_backTo_dm ;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    ArrayList<SanPham> list = new ArrayList<>();
    SanPham sanPham = new SanPham();

    SanPhamTrangChuAdapter sanPhamTrangChuAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        anhXa();
        img_backTo_dm.setOnClickListener(v -> {
            onBackPressed();
        });
        

        Bundle bundle = getIntent().getExtras();


        if (bundle == null){
            Toast.makeText(this, "Không có giá trị gì", Toast.LENGTH_SHORT).show();
            return;
        }
        CODE_CHOSSE =  bundle.getInt("CODE");


        rcSanPhamDanhMuc .setLayoutManager(new GridLayoutManager(this,2));

        sanPhamTrangChuAdapter = new SanPhamTrangChuAdapter(this,list);


        if (CODE_CHOSSE == 1 ){

            reference.child("SanPham").orderByChild("danhmucSP").equalTo("shirt").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataChild : snapshot.getChildren()){

                            list.add(dataChild.getValue(SanPham.class));

                        }
                        sanPhamTrangChuAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if (CODE_CHOSSE == 2 ){

            reference.child("SanPham").orderByChild("danhmucSP").equalTo("tshirt").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataChild : snapshot.getChildren()){

                            list.add(dataChild.getValue(SanPham.class));

                        }
                        sanPhamTrangChuAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if (CODE_CHOSSE == 3 ){


            reference.child("SanPham").orderByChild("danhmucSP").equalTo("sweater").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataChild : snapshot.getChildren()){

                            list.add(dataChild.getValue(SanPham.class));

                        }
                        sanPhamTrangChuAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if (CODE_CHOSSE == 4 ){

            reference.child("SanPham").orderByChild("danhmucSP").equalTo("hoodies").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataChild : snapshot.getChildren()){
                            list.add(dataChild.getValue(SanPham.class));

                        }
                        sanPhamTrangChuAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if (CODE_CHOSSE == 5 ){

            reference.child("SanPham").orderByChild("danhmucSP").equalTo("short").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataChild : snapshot.getChildren()){

                            list.add(dataChild.getValue(SanPham.class));

                        }
                        sanPhamTrangChuAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if (CODE_CHOSSE == 6 ){

            reference.child("SanPham").orderByChild("danhmucSP").equalTo("pants").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataChild : snapshot.getChildren()){

                            list.add(dataChild.getValue(SanPham.class));

                        }
                        sanPhamTrangChuAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        rcSanPhamDanhMuc.setAdapter(sanPhamTrangChuAdapter);
    }

    private  void  anhXa (){
        img_backTo_dm = findViewById(R.id.img_backTo_dm);
        rcSanPhamDanhMuc = findViewById(R.id.rcSanPhamDanhMuc);
    }
}