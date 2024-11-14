package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tampdph33277.fpoly.vergencyshop_quanly.DTO.NguoiDung;
import tampdph33277.fpoly.vergencyshop_quanly.R;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.HolderNguoiDungAdapter> {
    ArrayList<NguoiDung> list ;
    Context context ;

    public NguoiDungAdapter(ArrayList<NguoiDung> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderNguoiDungAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nguoi_dung_item,parent,false);
        return new HolderNguoiDungAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNguoiDungAdapter holder, int position) {

        Glide.with(context).load(list.get(position).getAnh()).placeholder(R.drawable.logo).into(holder.imgNguoiDung);


                if (list.get(position).getTen() == null){
                    holder.tvTenNguoiDung.setText("Chưa có") ;
                }else {
                    holder.tvTenNguoiDung.setText("Tên: "+list.get(position).getTen()) ;
                }
                if (list.get(position).getGioiTinh() == null){
                     holder.tvIdNguoiDung .setText("Chưa có");

               }else {
                   holder.tvIdNguoiDung .setText("Giới tính: "+list.get(position).getGioiTinh());

                }


                holder.tvEmailNguoiDung .setText("Email: "+list.get(position).getTaiKhoan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  HolderNguoiDungAdapter extends RecyclerView.ViewHolder {
        ImageView imgNguoiDung;

        TextView tvIdNguoiDung , tvTenNguoiDung ,tvEmailNguoiDung ;


        public HolderNguoiDungAdapter(@NonNull View itemView) {
            super(itemView);

            imgNguoiDung = itemView.findViewById(R.id.imgNguoiDung);
            tvIdNguoiDung= itemView.findViewById(R.id.tvGioiTinhNguoiDung   );
            tvTenNguoiDung= itemView.findViewById(R.id.tvTenNguoiDung);
            tvEmailNguoiDung= itemView.findViewById(R.id.tvEmailNguoiDung);

        }
    }
}
