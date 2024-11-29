package com.example.vergencyshop.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vergencyshop.R;
import com.example.vergencyshop.ThanhToanSanPham;
import com.example.vergencyshop.models.GioHang;
import com.example.vergencyshop.models.HoaDonChiTiet;
import com.example.vergencyshop.models.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.HolderThanhToan> {

    ArrayList<GioHang> list ;
    Context context ;
    String  soLuong , size ;


    public ThanhToanAdapter(ArrayList<GioHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ThanhToanAdapter(ArrayList<GioHang> list, Context context,String soLuong , String size) {
        this.list = list;
        this.context = context;
        this.soLuong = soLuong ;
        this.size = size ;
    }

    @NonNull
    @Override
    public HolderThanhToan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thanh_toan_item,parent,false);
        return new HolderThanhToan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderThanhToan holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        GioHang gioHang = list.get(position);
        Glide.with(context)
                .load(Uri.decode(list.get(position).getAnhSP()))
                .placeholder(R.drawable.ic_giohang)
                .error(R.drawable.ngacnhien)
                .into(holder.imgSanPhamThanhToan);


        // Định dạng và gán giá sản phẩm
        double giaSP = Double.parseDouble(gioHang.getGiaSP());
        String formattedGiaSP = currencyFormat.format(giaSP);
        holder.tvItemGiaSanPhamThanhToan.setText(formattedGiaSP);

        // Gán thông tin khác của sản phẩm
        holder.tvItemTenSanPhamThanhToan.setText(gioHang.getTenSP());
        holder.tvItemSizeSanPhamThanhToan.setText(gioHang.getSizeSP());

        // Tính toán và định dạng tổng tiền
        int soLuongSP = Integer.parseInt(gioHang.getSoluongSP());
        int giaSP_1 = (int) (giaSP / (soLuongSP));

        int tongTien = (int) (giaSP_1 * soLuongSP);
        String formattedTongTien = currencyFormat.format(tongTien);
        holder.tvItemTongTienSanPhamThanhToan.setText(formattedTongTien);

        // Gán số lượng sản phẩm
        holder.tvItemSoLuongSanPhamThanhToan.setText(gioHang.getSoluongSP());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class HolderThanhToan extends RecyclerView.ViewHolder {
        TextView tvItemTenSanPhamThanhToan,
                tvItemSizeSanPhamThanhToan,
                tvItemGiaSanPhamThanhToan ,
                tvItemSoLuongSanPhamThanhToan,
                tvItemTongTienSanPhamThanhToan;

        ImageView imgSanPhamThanhToan;
        public HolderThanhToan(@NonNull View itemView) {
            super(itemView);

            imgSanPhamThanhToan = itemView.findViewById(R.id.imgSanPhamThanhToan);
            tvItemGiaSanPhamThanhToan = itemView.findViewById(R.id.tvItemGiaSanPhamThanhToan);
            tvItemTenSanPhamThanhToan   = itemView.findViewById(R.id.tvItemTenSanPhamThanhToan);
            tvItemSizeSanPhamThanhToan     = itemView.findViewById(R.id.tvItemSizeSanPhamThanhToan);
            tvItemSoLuongSanPhamThanhToan  = itemView.findViewById(R.id.tvItemSoLuongSanPhamThanhToan);
            tvItemTongTienSanPhamThanhToan    = itemView.findViewById(R.id.tvItemTongTienSanPhamThanhToan);

        }
    }



}
