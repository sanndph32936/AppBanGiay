package tampdph33277.fpoly.vergencyshop_quanly.FRAGMENT;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tampdph33277.fpoly.vergencyshop_quanly.ADAPTER.QuanLySanPhamAdapter;
import tampdph33277.fpoly.vergencyshop_quanly.DTO.SanPham;
import tampdph33277.fpoly.vergencyshop_quanly.R;


public class QuanLySanPhamFragment extends Fragment {
    View view;
    QuanLySanPhamAdapter quanLySanPhamAdapter;
    SearchView sv_tenSP;
ImageView addSP_ImageView;

    private RecyclerView rcSanPham;
    ArrayList<SanPham> list = new ArrayList<>();
    private ArrayList<SanPham> search_List = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view =  inflater.inflate(R.layout.frag_quanly_sanpham, container, false);
anhXa();
        addSP_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();

            }
        });
        sv_tenSP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search_List.clear();
                if (newText.length() > 0) {
                    queryFirebaseForSearch(newText);  // Truy vấn Firebase
                } else {
                    quanLySanPhamAdapter.filterList(list);  // Hiển thị lại toàn bộ danh sách sản phẩm nếu không có từ khóa tìm kiếm
                }
                return false;
            }
        });
        rcSanPham.setLayoutManager(new GridLayoutManager(getActivity(),2));
        quanLySanPhamAdapter= new QuanLySanPhamAdapter(getActivity(),list);
        reference.child("SanPham").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                SanPham sanPham =  snapshot.getValue(SanPham.class);
                list.add(sanPham);
                quanLySanPhamAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                quanLySanPhamAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                SanPham sanPham =  snapshot.getValue(SanPham.class);
                quanLySanPhamAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                quanLySanPhamAdapter.notifyDataSetChanged();
            }
        });
        rcSanPham.setAdapter(quanLySanPhamAdapter);

        return  view;
    }

    private void openDialogAdd() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)getActivity()).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_sanpham,null);
        builder.setView(v);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        EditText Ed_link_sp = v.findViewById(R.id.Ed_addlink_sp);
        EditText Ed_ten_SP = v.findViewById(R.id.Ed_addten_SP);
        EditText Ed_gia_sp = v.findViewById(R.id.Ed_addgia_sp);
        EditText Ed_mota_sp = v.findViewById(R.id.Ed_addmota_sp);
        Spinner spin_danhmuc = v.findViewById(R.id.Spin_adddanhmuc);

        Button btn_huy_sp = v.findViewById(R.id.btn_addhuy_sp);
        Button btn_luu_sp = v.findViewById(R.id.btn_addluu_sp);

        String [] strings = {"shirt","tshirt","sweater","pants","hoodies","short"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,strings);
        spin_danhmuc.setAdapter(adapter);
        dialog.show();
        btn_huy_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });      btn_luu_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkSP = Ed_link_sp.getText().toString();
                String tenSP = Ed_ten_SP.getText().toString();
                String giaSP = Ed_gia_sp.getText().toString();
                String motaSP = Ed_mota_sp.getText().toString();
                String danhMuc = spin_danhmuc.getSelectedItem().toString();
                Pattern pattern = Pattern.compile("^[1-9]\\d{0,9}$");
                Matcher matcher = pattern.matcher(giaSP);
                if(tenSP.isEmpty()&&linkSP.isEmpty()&&giaSP.isEmpty()&&motaSP.isEmpty()){
                    Toast.makeText(getContext(), "Vui vòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;   }
                if(!matcher.matches()){
                    Toast.makeText(getContext(), "Giá không đúng định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("SanPham").child(("S"+(list.size() + 1)));
                SanPham _sanPham = new SanPham(linkSP,giaSP,tenSP,motaSP,danhMuc,"Còn Hàng");
                databaseRef.setValue(_sanPham);

                dialog.dismiss();
            }
        });
    }

    private void anhXa (){
        sv_tenSP = view.findViewById(R.id.sv_tenSP);
        rcSanPham = view.findViewById(R.id.rcdanhSachSanPhamTC);
        addSP_ImageView = view.findViewById(R.id.addSP_ImageView);
    }
    private void queryFirebaseForSearch(String keyword) {
        Query searchQuery = reference.child("SanPham").orderByChild("tenSPLowerCase");

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                search_List.clear();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    SanPham sanPham = childSnapshot.getValue(SanPham.class);
                    String lowercaseTenSP = sanPham.getTenSP().toLowerCase();
                    if (lowercaseTenSP.contains(keyword.toLowerCase())) {
                        search_List.add(sanPham);
                    }
                }
                quanLySanPhamAdapter.filterList(search_List);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý  lỗi
            }
        });
    }

}
