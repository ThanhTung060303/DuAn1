package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.Model.LoaiPet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Activity_Sua_Xoa_SP extends AppCompatActivity {
    ImageView ivBack, ivDel,ivPic;
    Button btnSuaPic, btnClear, btnUpdate;
    EditText etTen, etTuoi,etGia;
    TextView txtMa,txtTrangThai;
    Spinner spnLoai;
    SharedPreferences sharedPreferences;
    PetDAO petDAO;
    LoaiPetDAO loaiPetDAO;
    private Uri imageURI;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String link_anh;
    int mapet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_xoa_sp);
        ivBack = findViewById(R.id.ivBackXemTTPet);
        ivDel = findViewById(R.id.ivXoaPet);
        ivPic = findViewById(R.id.ivXemTTSanPham);
        btnClear = findViewById(R.id.btn_ClearFormSuaPet);
        btnSuaPic = findViewById(R.id.btn_SuaHinhAnhSP);
        btnUpdate = findViewById(R.id.btn_SuaPet);
        txtMa = findViewById(R.id.txtSuaMaPet);
        etTen = findViewById(R.id.et_inputSuaTenPet);
        etTuoi = findViewById(R.id.et_inputSuaTuoiPet);
        txtTrangThai = findViewById(R.id.txtTrangThai);
        etGia = findViewById(R.id.et_inputSuaGiaPet);
        spnLoai = findViewById(R.id.Spinner_LoaiPetSua);

        petDAO = new PetDAO(this);
        loaiPetDAO = new LoaiPetDAO(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        String textmapet = getIntent().getExtras().get("mapet").toString();
        mapet = Integer.parseInt(textmapet);
        String loai = getIntent().getExtras().get("loai").toString();
        if(petDAO.checkPet(mapet)) {
            sharedPreferences = getSharedPreferences("THONGTINPET", MODE_PRIVATE);
            String tenpet = sharedPreferences.getString("tenpet","");
            link_anh = sharedPreferences.getString("hinhanh","");
            int tuoi = sharedPreferences.getInt("tuoi", 0);
            int gia = sharedPreferences.getInt("gia",0);
            String maloai = sharedPreferences.getString("maloai","");
            String trangthai;
            if(sharedPreferences.getInt("trangthai",0)==0){
                trangthai = "Đang bán";

            }else{
                trangthai = "Đã bán";
            }
            //
            txtMa.setText(mapet+"");
            etTen.setText(tenpet);
            etTuoi.setText(tuoi+"");
            etGia.setText(gia+"");
            txtTrangThai.setText("Trạng thái: "+trangthai);
            Glide.with(this)
                    .load(link_anh)
                    .into(ivPic);
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    this,
                    getDataLoaiPet(loai),
                    android.R.layout.simple_list_item_1,
                    new String[]{"tenloaipet"},
                    new int[]{android.R.id.text1});
            spnLoai.setAdapter(simpleAdapter);
            int index = 0;
            int postion = -1;
            for(HashMap<String,Object> item: getDataLoaiPet(loai)){
                if ( item.get("maloaipet").equals(maloai)){
                    postion = index;
                }
                index++;
            }
            spnLoai.setSelection(postion);
            //
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDiaLogYesNo("Bạn có chắc chắn muốn xóa?");
                }
            });
            btnSuaPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePicture();
                }
            });
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten = etTen.getText().toString();
                    String txttuoi = etTuoi.getText().toString();

                    HashMap<String,Object> hmTV =(HashMap<String,Object> ) spnLoai.getSelectedItem();
                    String maloai= String.valueOf(hmTV.get("maloaipet"));
                    String txtgia = etGia.getText().toString();

                    if(ten.equals("") || txttuoi.equals("")|| txtgia.equals("")|| link_anh.equals("")){
                        showDiaLog("Vui lòng nhập đầy đủ thông tin!");
                    }else{
                        try {
                            int tuoi = Integer.parseInt(txttuoi);
                            int gia = Integer.parseInt(txtgia);
                            boolean check = petDAO.capNhatThongTin(mapet,ten,tuoi,link_anh,maloai,gia);
                            if(check){
                                showDiaLogAddThanhCong("Cập nhật thành công!");
                            }
                            else{
                                showDiaLog("Cập nhật không thành công!");
                            }
                        }catch (NumberFormatException c) {
                            showDiaLog("Vui lòng nhập đúng dữ liệu");
                        }
                    }

                }
            });

            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearForm();
                }
            });
        }
    }

    private ArrayList<HashMap<String,Object>> getDataLoaiPet(String x){

        ArrayList<LoaiPet> list = loaiPetDAO.getDSLoaiPet(x);
        ArrayList<HashMap<String,Object>> listHashMap = new ArrayList<>();
        for(LoaiPet loai: list){
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("maloaipet",loai.getMaloaipet());
            hm.put("tenloaipet",loai.getTenloaipet());
            listHashMap.add(hm);
        }
        return listHashMap;
    }

    private void choosePicture() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // inten tich hop 2 intenr tren 1  hop
        Intent tuychon = Intent.createChooser(i, "chọn 1 mục");
        tuychon.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{camera});
        startActivityForResult(tuychon,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999 && resultCode==RESULT_OK){
            if (data.getExtras() != null) {// lay tu camera
                Bundle b = data.getExtras();
                Bitmap bm = (Bitmap) b.get("data");
                ivPic.setImageBitmap(bm);
                uploadBitmap(bm);
            }else {
                imageURI = data.getData();
                ivPic.setImageURI(imageURI);
                uploadPicture();
            }
        }
    }

    private void uploadBitmap(Bitmap bm) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Đang tải...");
        dialog.show();
        final long id = System.currentTimeMillis();
        StorageReference river = storageReference.child("image/"+id);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        river.putBytes(byteArray)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        river.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dialog.dismiss();
                                link_anh = uri+"";
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Tải không thành công.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        dialog.setMessage("Đang tải... ");
                    }
                });
    }

    private void uploadPicture() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Đang tải...");
        dialog.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference river = storageReference.child("image/*"+randomKey);

        river.putFile(imageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        river.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                link_anh = uri+"";
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Tải không thành công.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        dialog.setMessage("Đang tải... ");
                    }
                });
    }

    private void showDiaLog(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongbao,null);
        TextView hienthi = view.findViewById(R.id.txtThongTin);
        Button btn = view.findViewById(R.id.btn_ok);
        hienthi.setText(x);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showDiaLogAddThanhCong(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thongbao,null);
        TextView hienthi = view.findViewById(R.id.txtThongTin);
        Button btn = view.findViewById(R.id.btn_ok);
        hienthi.setText(x);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void clearForm(){
        etTen.setText("");
        etTuoi.setText("");
        etGia.setText("");
        ivPic.setImageResource(R.mipmap.auto);
    }
    private void showDiaLogYesNo(String x){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_y_n,null);
        TextView hienthi = view.findViewById(R.id.txtCauHoi);
        hienthi.setText(x);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = petDAO.xoaPet(mapet);
                if(check){
                    showDiaLogAddThanhCong("Xóa thành công!");
                }
                else{
                    showDiaLog("Xóa không thành công!");
                }

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}