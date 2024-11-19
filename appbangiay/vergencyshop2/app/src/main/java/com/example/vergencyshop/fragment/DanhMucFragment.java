package com.example.vergencyshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.vergencyshop.DanhMuc;
import com.example.vergencyshop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhMucFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhMucFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhMucFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiTietSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhMucFragment newInstance(String param1, String param2) {
        DanhMucFragment fragment = new DanhMucFragment();
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
    View view ;
    ImageView btnDanhMucShirt , btnDanhMucTShirt , btnDanhMucSweaters , btnDanhMucHoodies , btnDanhMucShort ,  btnDanhMucPants;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_danhmuc, container, false);
        anhXa();

        btnDanhMucShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMuc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CODE",1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnDanhMucTShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMuc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CODE",2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnDanhMucSweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMuc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CODE",3);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        btnDanhMucHoodies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMuc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CODE",4);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        btnDanhMucShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMuc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CODE",5);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnDanhMucPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhMuc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("CODE",6);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    private  void anhXa (){
        btnDanhMucShirt = view.findViewById(R.id.btnDanhMucShirt);
        btnDanhMucTShirt = view.findViewById(R.id.btnDanhMucTShirt);
        btnDanhMucSweaters = view.findViewById(R.id.btnDanhMucSweaters);
        btnDanhMucHoodies = view.findViewById(R.id.btnDanhMucHoodies);
        btnDanhMucShort= view.findViewById(R.id.btnDanhMucShort);
        btnDanhMucPants= view.findViewById(R.id.btnDanhMucPants);



    }
}