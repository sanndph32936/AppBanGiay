package com.example.vergencyshop.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vergencyshop.R;
import com.example.vergencyshop.models.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoiMatKhauFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoiMatKhauFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoiMatKhauFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoiMatKhauFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoiMatKhauFragment newInstance(String param1, String param2) {
        DoiMatKhauFragment fragment = new DoiMatKhauFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    EditText edtOldPass , edtNewPass , edtCheckNewPass ;
    Button btnChange;
    ImageView imgNguoiDungDoiMK;
    View view;
    ProgressDialog progressDialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("NguoiDung");


    //Người dùng
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        anhXa();
        setAnh();


        btnChange.setOnClickListener(v -> {
            progressDialog.show();
            String oldPass, newPass  ;

            if (    edtOldPass.getText().toString().isEmpty()||
                    edtNewPass.getText().toString().isEmpty()||
                    edtCheckNewPass.getText().toString().isEmpty()
            ){
                Toast.makeText(getContext(), "Bạn nhập thiếu", Toast.LENGTH_SHORT).show();
            } else if (!edtNewPass.getText().toString().equalsIgnoreCase(edtCheckNewPass.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            }else {
                oldPass = edtOldPass.getText().toString();
                newPass = edtNewPass.getText().toString();
               auth.signInWithEmailAndPassword(user.getEmail(),oldPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       progressDialog.dismiss();
                        if (task.isSuccessful()){


                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                   }
               });


            }


        });


        // Inflate the layout for this fragment
        return view ;
    }

    private void setAnh() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();

        reference1.child("NguoiDung").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);

                Glide.with(getContext()).load(nguoiDung.getAnh()).into(imgNguoiDungDoiMK);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private  void  anhXa (){
        imgNguoiDungDoiMK = view.findViewById(R.id.imgNguoiDungDoiMK);
        progressDialog = new ProgressDialog(getContext());
        edtNewPass = view.findViewById(R.id.edt_newpassDMK);
        edtOldPass = view.findViewById(R.id.edt_oldpassDMK);
        edtCheckNewPass = view.findViewById(R.id.edt_checknewpassDMK);
        btnChange = view.findViewById(R.id.btnsaveDMK);
    }





}