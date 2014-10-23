package com.example.dostawy_2;

import com.example.dostawy_2.DialogFragment_dodajprodukt.DialogDodajProduktListener;

import android.os.Bundle;
import android.widget.*;
import android.app.*;
import android.content.DialogInterface;
import android.view.*;

public class DialogFragment_edytujdostawce extends DialogFragment{
	View V;
	EdytujDostawceListener mListener;
	Dostawca mDostawca;
	

	public Dialog onCreateDialog(Bundle savedInstanceState) {		
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());       
        V=this.getActivity().getLayoutInflater().inflate(R.layout.alertdialog_edytujdostawce, null);
        ((EditText)V.findViewById(R.id.editText_alertdialog_edytujdostawce)).setText(this.mDostawca.GetNazwa());
        ((EditText)V.findViewById(R.id.editText_alertdialog_edytujdostawce)).setSelection(this.mDostawca.GetNazwa().length());
        
        builder.setView(V);
        builder.setMessage("Nowa nazwa")
        .setPositiveButton("Zastosuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
            	mListener.onEdytujeDostawce(mDostawca.GetID(),((EditText)V.findViewById(R.id.editText_alertdialog_edytujdostawce)).getText().toString());
            }
        })
        .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        
        
        return builder.create();               
	}
	
	
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EdytujDostawceListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	public DialogFragment_edytujdostawce SetDostawca(Dostawca d){
		this.mDostawca=d;
		return this;
	}
	
	
	
	public interface EdytujDostawceListener{
		public void onEdytujeDostawce(int i,String n);
	}

}
