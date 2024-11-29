package com.example.vergencyshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vergencyshop.R;
import com.example.vergencyshop.models.SanPham;
import com.example.vergencyshop.models.TopSanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class TopSanPhamAdapter extends RecyclerView .Adapter<TopSanPhamAdapter.HolderTopSanPhamAdapter>{
    ArrayList<TopSanPham> list ;
    Context context ;

    public TopSanPhamAdapter(ArrayList<TopSanPham> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderTopSanPhamAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.san_pham_item_trang_chu,parent,false);
        return new HolderTopSanPhamAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTopSanPhamAdapter holder, int position) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("SanPham").child(list.get(position).getIdSP()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SanPham sanPham =  snapshot.getValue(SanPham.class);
                Glide.with(context).load(sanPham.getAnhSP()).into( holder.imgSanPhamTrangChu );


                holder.tvTenItemSanPhamTrangChu .setText(sanPham.getTenSP()); ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.tvGiaItemSanPhamTrangChu .setText("Lượt mua: "+list.get(position).getSoLuong());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class HolderTopSanPhamAdapter extends RecyclerView.ViewHolder {
        ImageView imgSanPhamTrangChu ;
        TextView tvTenItemSanPhamTrangChu , tvGiaItemSanPhamTrangChu ;

        public HolderTopSanPhamAdapter(@NonNull View itemView) {
            super(itemView);
            imgSanPhamTrangChu = itemView.findViewById(R.id.imgSanPhamTrangChu);
            tvTenItemSanPhamTrangChu = itemView.findViewById(R.id.tvTenItemSanPhamTrangChu);
            tvGiaItemSanPhamTrangChu = itemView.findViewById(R.id.tvGiaItemSanPhamTrangChu);


        }
    }
}
