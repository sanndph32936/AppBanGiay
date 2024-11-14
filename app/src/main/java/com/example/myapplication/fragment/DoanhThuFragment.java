package com.example.myapplication.fragment;

import android.app.DatePickerDialog;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.Locale;

import tampdph33277.fpoly.vergencyshop_quanly.DTO.HoaDon;
import tampdph33277.fpoly.vergencyshop_quanly.R;

public class DoanhThuFragment extends Fragment {
    Button btn_doanhthu,btn_ngaybd,btn_ngaykt;
    EditText ed_ngaybd,ed_ngaykt;
    TextView tv_doanhthu;
    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
    int mDay,mMonth,mYear;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.frag_doanhthu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_doanhthu = view.findViewById(R.id.btn_doanhthu);
        btn_ngaybd = view.findViewById(R.id.btn_ngaybd);
        btn_ngaykt = view.findViewById(R.id.btn_ngaykt);
        ed_ngaybd = view.findViewById(R.id.ed_ngaybd);
        ed_ngaykt = view.findViewById(R.id.ed_ngaykt);
        tv_doanhthu = view.findViewById(R.id.tv_doanhthu);
        btn_ngaybd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear= c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,mDate_bd,mYear,mMonth,mDay);
                d.show();

            }
        });
        btn_ngaykt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear= c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,mDate_kt,mYear,mMonth,mDay);
                d.show();
            }
        });
        btn_doanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String batdau = ed_ngaybd.getText().toString();
                String ketthuc = ed_ngaykt.getText().toString();
                if (batdau.isEmpty() || ketthuc.isEmpty()){
                    Toast.makeText(getContext(), "Bạn chưa chọn ngày", Toast.LENGTH_SHORT).show();

                    return;

                }
                try {
                    boolean checkNgay  = sdf.parse(batdau).before(sdf.parse(ketthuc));

                    if (!checkNgay){
                        Toast.makeText(getContext(), "Từ ngày phải trước đến ngày", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                reference.child("HoaDon").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                int tongTien = 0 ;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);
                                  if (hoaDon.getTrangThai().equals("Đã Giao")){
                                      try {

                                          boolean checkTN = sdf.parse(batdau).before(sdf.parse(hoaDon.getNgayMua()));
                                          boolean checkBTN = sdf.parse(batdau).equals(sdf.parse(hoaDon.getNgayMua()));

                                          boolean checkBDN= sdf.parse(ketthuc).equals(sdf.parse(hoaDon.getNgayMua()));
                                          boolean checkDN = sdf.parse(ketthuc).after(sdf.parse(hoaDon.getNgayMua()));
                                         if ((checkTN  && checkDN) || (checkBTN || checkBDN)){
                                              tongTien+= Integer.parseInt(hoaDon.getThanhTien());

                                          }
                                      } catch (ParseException e) {
                                          throw new RuntimeException(e);
                                      }
                                  }
                                    Locale locale = new Locale("vi", "VN");
                                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
                                    Currency currency = Currency.getInstance(locale);
                                    String formattedDoanhthu = currencyFormat.format(Double.parseDouble(String.valueOf(tongTien)));
                                    tv_doanhthu.setText(formattedDoanhthu);



                                }


                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });




    }
    DatePickerDialog.OnDateSetListener mDate_bd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            mYear= year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            ed_ngaybd.setText(sdf.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDate_kt = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear= year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            ed_ngaykt.setText(sdf.format(c.getTime()));
        }
    };
    }

