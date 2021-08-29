package com.example.e_commerce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Deleteproduct extends AppCompatDialogFragment {
    private int sc_id;
    private int proid;
    private ecommerceDB edb;
    Intent i;
    public Deleteproduct(int sc,int pro,ecommerceDB e,Intent intent)
    {
        sc_id=sc;
        proid=pro;
        edb=e;
        i=intent;
    }
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Product").setMessage("Are you sure you want to delete this product").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edb.delete_product(String.valueOf(sc_id),String.valueOf(proid));
                startActivity(i);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
