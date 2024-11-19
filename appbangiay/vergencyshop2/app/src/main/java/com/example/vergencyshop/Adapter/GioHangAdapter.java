package com.example.vergencyshop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vergencyshop.GioHangActivity;
import com.example.vergencyshop.R;
import com.example.vergencyshop.models.GioHang;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.HolderGioHangAdapter>{


    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("GioHang");
    private final Context context;
    private final ArrayList<GioHang> list;
    int tongHang = 0;
    private TextView tv_tongtien;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public GioHangAdapter(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GioHangAdapter.HolderGioHangAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gio_hang_item,parent,false);

        return new GioHangAdapter.HolderGioHangAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.HolderGioHangAdapter holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context)
                .load(Uri.decode(list.get(position).getAnhSP()))
                .placeholder(R.drawable.ic_giohang)
                .error(R.drawable.ngacnhien)
                .into(holder.imgSP);
        holder.btnCongSoLuong_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int soLuong = Integer.parseInt(list.get(position).getSoluongSP()) +1;
                if (soLuong <= 10) { // Giới hạn tối đa số lượng là 10
                    int giaSP = Integer.parseInt(list.get(position).getGiaSP());

                    cartRef.child(list.get(position).getIdSP() + user.getUid()).child("soluongSP").setValue(String.valueOf(soLuong));

                    int giaSP_1 = giaSP / (soLuong - 1);
                    giaSP = giaSP_1 * soLuong;
                    int tongGiaSP = giaSP;
                    list.get(position).setGiaSP(String.valueOf(tongGiaSP));
                    cartRef.child(list.get(position).getIdSP() + user.getUid()).child("giaSP").setValue(String.valueOf(tongGiaSP));
                    notifyDataSetChanged(); // Cập nhật giao diện
                    updateTongTien(); // Cập nhật tổng tiền
                }else {
                    Toast.makeText(context, "Đã đạt số lượng tối đa trên 1 sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

//                }

            }
        });
        holder.btnTruSoLuong_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int soLuong = Integer.parseInt(list.get(position).getSoluongSP()) -1;
                if (soLuong >= 1) { // Giới hạn tối thiểu số lượng là 1
                    int giaSP = Integer.parseInt(list.get(position).getGiaSP());
                    cartRef.child(list.get(position).getIdSP() + user.getUid()).child("soluongSP").setValue(String.valueOf(soLuong));

                    int giaSP_1 = giaSP / (soLuong + 1);
                    giaSP = giaSP_1 * soLuong;
                    int tongGiaSP = giaSP;
                    list.get(position).setGiaSP(String.valueOf(tongGiaSP));
                    cartRef.child(list.get(position).getIdSP() + user.getUid()).child("giaSP").setValue(String.valueOf(tongGiaSP));

                    notifyDataSetChanged(); // Cập nhật giao diện
                    updateTongTien(); // Cập nhật tổng tiền
                     }
           }
        });

        holder.tenSP.setText(list.get(position).getTenSP());
        holder.sizeSP.setText(list.get(position).getSizeSP());

        holder.soluongSP.setText(list.get(position).getSoluongSP());
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        try {
            double giaSP = Double.parseDouble(list.get(position).getGiaSP());
            String formattedGiaSP = currencyFormat.format(giaSP);
            holder.giaSP.setText(formattedGiaSP);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            holder.giaSP.setText(list.get(position).getGiaSP());
        }
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaSPTrongGioHang(list.get(position));
                notifyDataSetChanged();
                updateTongTien();
            }
        });
    }
    private void xoaSPTrongGioHang(GioHang gioHang) {
        Query query = cartRef.orderByChild("idSP").equalTo(gioHang.getIdSP());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue(); // hoặc sử dụng setValue(null) để xóa dữ liệu
                }
                list.remove(gioHang);
                notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase Realtime Database
                Log.e("XoaSPTrongGioHang", "Lỗi đọc dữ liệu từ Firebase Realtime Database: " + error.getMessage());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    class HolderGioHangAdapter extends RecyclerView.ViewHolder {
        ImageView imgSP, imgDel;
        TextView tenSP , giaSP, soluongSP , sizeSP,btnTruSoLuong_cart,btnCongSoLuong_cart;

        LinearLayout layoutItem ;
        public HolderGioHangAdapter(@NonNull View itemView) {
            super(itemView);
            imgSP = (ImageView) itemView.findViewById(R.id.eachCartItemIV);
            imgDel = (ImageView) itemView.findViewById(R.id.eachCartItemDeleteBtn);
            tenSP = (TextView) itemView.findViewById(R.id.tv_tensp);
            sizeSP = (TextView) itemView.findViewById(R.id.tv_size);
            giaSP = (TextView) itemView.findViewById(R.id.tv_giatien);
            soluongSP = (TextView) itemView.findViewById(R.id.tv_sl);
            btnTruSoLuong_cart = (TextView) itemView.findViewById(R.id.btnTruSoLuong_cart);
            btnCongSoLuong_cart = (TextView) itemView.findViewById(R.id.btnCongSoLuong_cart);
        }
    }
    public void setTongTienTextView(TextView tv_tongtien) {
        this.tv_tongtien = tv_tongtien;
    }
    public void updateTongTien() {
        int tongTien = 0;
        for (GioHang gioHang : list) {
            int giaSP = Integer.parseInt(gioHang.getGiaSP());
            tongTien += giaSP;
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedTongTien = currencyFormat.format(tongTien);
        tv_tongtien.setText(formattedTongTien);
    }
}
