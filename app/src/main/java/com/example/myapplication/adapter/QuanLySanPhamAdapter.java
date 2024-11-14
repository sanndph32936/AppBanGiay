package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;


public class QuanLySanPhamAdapter extends  RecyclerView.Adapter<QuanLySanPhamAdapter.HolderQuanLySanPham> {
    private final Context context;
    private ArrayList<SanPham> list;

    public void filterList(ArrayList<SanPham> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public QuanLySanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public HolderQuanLySanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quanli_sanpham_item,parent,false);

        return new HolderQuanLySanPham(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderQuanLySanPham holder, int position) {
        Glide.with(context)
                .load(Uri.decode(list.get(position).getAnhSP()))
                .placeholder(R.drawable.cart)

                .error(R.drawable.shirt)
                .into(holder.imgSP);

        holder.tenSP.setText(list.get(position).getTenSP());
        holder.img_edit_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_sach(list.get(position),position);

            }
        });
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        Currency currency = Currency.getInstance(locale);

        String formattedGiaSP = currencyFormat.format(Double.parseDouble(list.get(position).getGiaSP()));
        if (list.get(position).getTrangthaiSP() == null || list.get(position).getTrangthaiSP().equals("Còn Hàng")   ){
            holder.giaSP.setText(formattedGiaSP);

        }else {
            holder.giaSP.setText("Hết Hàng");

        }


//        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickGoToDetail(list.get(position));
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderQuanLySanPham extends RecyclerView.ViewHolder{
        ImageView imgSP;
        TextView tenSP , giaSP ;
        LinearLayout layoutItem ;
        ImageView img_edit_sanpham;
        public HolderQuanLySanPham(@NonNull View itemView) {
            super(itemView);
            imgSP = (ImageView) itemView.findViewById(R.id.imgSanPhamTrangChu);
            tenSP =(TextView) itemView.findViewById(R.id.tvTenItemSanPhamTrangChu);
            giaSP =(TextView) itemView.findViewById(R.id.tvGiaItemSanPhamTrangChu);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layoutItemSanPhamTrangChu);
            img_edit_sanpham = (ImageView) itemView.findViewById(R.id.img_edit_sanpham);
        }
    }
//    private void onClickGoToDetail(DrawableTransitionOptions model) {
//        Intent intent = new Intent(context, ChiTietSanPham.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("SanPham",model);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//
//
//
//    }
public void update_sach(SanPham jobsp, int position){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View v = inflater.inflate(R.layout.dialog_edit_sanpham,null);
    builder.setView(v);
    builder.setCancelable(false);
    AlertDialog dialog = builder.create();
    EditText Ed_link_sp = v.findViewById(R.id.Ed_link_sp);
    EditText Ed_ten_SP = v.findViewById(R.id.Ed_ten_SP);
    EditText Ed_gia_sp = v.findViewById(R.id.Ed_gia_sp);
    EditText Ed_mota_sp = v.findViewById(R.id.Ed_mota_sp);
    Spinner spin_danhmuc = v.findViewById(R.id.Spin_danhmuc);
    Spinner spin_trangthai = v.findViewById(R.id.Spin_trangthai);
    Button btn_huy_sp = v.findViewById(R.id.btn_huy_sp);
    Button btn_luu_sp = v.findViewById(R.id.btn_luu_sp);
    Ed_link_sp.setText(jobsp.getAnhSP());
    Ed_gia_sp.setText(jobsp.getGiaSP());
    Ed_mota_sp.setText(jobsp.getMotaSP());
    Ed_ten_SP.setText(jobsp.getTenSP());

    String [] stringTragThai = {"Còn Hàng","Hết Hàng"};

    String [] strings = {"shirt","tshirt","sweater","pants","hoodies","short"};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,strings);
    ArrayAdapter<String> adapterTrangthai = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,stringTragThai);



    spin_trangthai.setAdapter(adapterTrangthai);
    spin_danhmuc.setAdapter(adapter);
    //Danh mục
    if (jobsp.getDanhmucSP().equals("shirt")){
        spin_danhmuc.setSelection(0);
    }else if (jobsp.getDanhmucSP().equals("tshirt")){
        spin_danhmuc.setSelection(1);
    }else if (jobsp.getDanhmucSP().equals("sweater")){
        spin_danhmuc.setSelection(2);
    }else if (jobsp.getDanhmucSP().equals("pants")){
        spin_danhmuc.setSelection(3);
    }else if (jobsp.getDanhmucSP().equals("hoodies")){
        spin_danhmuc.setSelection(4);
    }else {
        spin_danhmuc.setSelection(5);
    }

    //Trạng thái
    if (jobsp.getTrangthaiSP()==null || jobsp.getTrangthaiSP().equals("Còn Hàng")){
        spin_trangthai.setSelection(0);
    }else {
        spin_trangthai.setSelection(1);
    }

    dialog.show();
    btn_luu_sp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Lấy giá trị từ các trường EditText và Spinner
            String linkSP = Ed_link_sp.getText().toString();
            String tenSP = Ed_ten_SP.getText().toString();
            String giaSP = Ed_gia_sp.getText().toString();
            String motaSP = Ed_mota_sp.getText().toString();
            String danhMuc = spin_danhmuc.getSelectedItem().toString();
            String trangthai = spin_trangthai.getSelectedItem().toString();
            // Thực hiện cập nhật dữ liệu vào Firebase hoặc nơi lưu trữ dữ liệu của bạn
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("SanPham").child(("S"+(position+1)));

            jobsp.setAnhSP(linkSP);
            jobsp.setTenSP(tenSP);
            jobsp.setGiaSP(giaSP);
            jobsp.setMotaSP(motaSP);
            jobsp.setDanhmucSP(danhMuc);
            jobsp.setTrangthaiSP(trangthai);
            databaseRef.setValue(jobsp)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Xử lý thành công
                            dialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi gặp lỗi
                            // Hiển thị thông báo lỗi hoặc thực hiện các hành động khác
                        }
                    });
        }
    });
btn_huy_sp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});

  }
}
