package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.LoaiPetDAO;
import com.example.duan1.DAO.PetDAO;
import com.example.duan1.Model.LoaiPet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Activity_ThemSanPham extends AppCompatActivity {
    ImageView ivBack, ivHinhAnh;
    LoaiPetDAO loaiPetDAO;
    PetDAO petDAO;
    EditText etTenPet,etTuoi,etGia;
    Spinner spnLoaiPet;
    Button btnAddPicture, btnClear, btnAdd;
    private Uri imageURI;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String hinhanh=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        ivHinhAnh = findViewById(R.id.ivSanPhamMoi);
        ivBack  = findViewById(R.id.ivBackAddPet);
        etTenPet = findViewById(R.id.et_inputAddTenPet);
        etTuoi  = findViewById(R.id.et_inputAddTuoiPet);
        etGia = findViewById(R.id.et_inputAddGiaPet);
        btnAddPicture = findViewById(R.id.btnThemHinhAnhSP);
        btnClear = findViewById(R.id.btn_ClearFormAddPet);
        btnAdd = findViewById(R.id.btn_AddPet);
        spnLoaiPet = findViewById(R.id.Spinner_LoaiPet);

        loaiPetDAO = new LoaiPetDAO(this);
        petDAO = new PetDAO(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        String loai = getIntent().getExtras().getString("loai");

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                getDataLoaiPet(loai),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloaipet"},
                new int[]{android.R.id.text1});
        spnLoaiPet.setAdapter(simpleAdapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenpet = etTenPet.getText().toString();
                String txttuoi = etTuoi.getText().toString();

                HashMap<String,Object> hmTV =(HashMap<String,Object> ) spnLoaiPet.getSelectedItem();
                String maloai= String.valueOf(hmTV.get("maloaipet"));
                String txtgia = etGia.getText().toString();

                if(tenpet.equals("")|| txttuoi.equals("")|| txtgia.equals("")|| hinhanh.equals("")){
                    showDiaLog("Vui lòng nhập đầy đủ thông tin!");
                }else{
                    try {
                        int tuoi = Integer.parseInt(txttuoi);
                        int gia = Integer.parseInt(txtgia);
                        boolean check = petDAO.themMoiPet(tenpet,tuoi,hinhanh,maloai,gia);
                        if(check){
                            showDiaLogAddThanhCong("Thêm thành công!");
                        }
                        else{
                            showDiaLog("Thêm không thành công!");
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
                ivHinhAnh.setImageBitmap(bm);
                uploadBitmap(bm);
            }else {
                imageURI = data.getData();
                ivHinhAnh.setImageURI(imageURI);
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
                                hinhanh = uri+"";
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
                                hinhanh = uri+"";
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
                Intent i = new Intent();
                i.putExtra("capnhat",1);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    private void clearForm(){
        etTenPet.setText("");
        etTuoi.setText("");
        etGia.setText("");
        ivHinhAnh.setImageResource(R.mipmap.auto);
    }
}