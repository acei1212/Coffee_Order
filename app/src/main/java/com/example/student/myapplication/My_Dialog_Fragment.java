package com.example.student.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class My_Dialog_Fragment extends DialogFragment {
    private 能處理確定取消 OkCancelHandler;
    private View mDialogView;
    private AlertDialog mDialog;
    private Spinner mSpinner;
    private MySpinnerAdapter mSpinnerAdapter;

    public interface 能處理確定取消 {
        void 處理確定(Coffee coffee);

        void 處理取消();
    }


    public My_Dialog_Fragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        initOkCancelHander();
        initDialogView();
        initSpinner();
        initDialog();
        return mDialog;
    }

    private void initOkCancelHander() {
        try {
            OkCancelHandler = (能處理確定取消) getActivity();
        } catch (ClassCastException cause) {
            String message = "Activity 無法處理確定取消";
            throw new MyDialogFragmentException(message, cause);
        }
    }

    private void initDialogView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mDialogView = inflater.inflate(R.layout.fragment_my__dialog_, null);
    }

    private void initSpinner() {
        try {
            mSpinner = (Spinner) mDialogView.findViewById(R.id.coffee_spinner);
            Activity activity = getActivity();
            mSpinnerAdapter = new MySpinnerAdapter(activity);
            mSpinner.setAdapter(mSpinnerAdapter);
            mSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) activity);
            int position = 0;
            mSpinner.setSelection(position);
        } catch (ClassCastException cause) {
            String message = "Activity 無法處理 OnItemSelectedListener";
            throw new MyDialogFragmentException(message, cause);
        }
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("新增商品")
                .setIcon(android.R.drawable.ic_input_add)
                .setView(mDialogView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Coffee coffee = getCoffee();
                        OkCancelHandler.處理確定(coffee);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OkCancelHandler.處理取消();
                    }
                });

        mDialog = builder.create();

    }

    private Coffee getCoffee() {
        int position = mSpinner.getSelectedItemPosition();
        String title = mSpinnerAdapter.getCoffee_titles().getString(position);
        EditText ev_price = (EditText) mDialogView.findViewById(R.id.coffee_price);
        int price;
        try {
            price = Integer.parseInt(ev_price.getText().toString());
        }catch (RuntimeException e){
           price = 0;
        }
        int img_resource_id = mSpinnerAdapter.getImg_resource_id_array()[position];
        return new Coffee(title, price, img_resource_id);
    }

}
