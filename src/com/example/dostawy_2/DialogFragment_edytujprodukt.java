package com.example.dostawy_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.view.*;

import com.example.dostawy_2.DialogFragment_dodajprodukt.DialogDodajProduktListener;
import com.example.dostawy_2.DialogFragment_edytujdostawce.EdytujDostawceListener;

public class DialogFragment_edytujprodukt extends DialogFragment {

	EdycjaProduktyListener mListener;
	View V;
	Produkt mWybranyProdukt;
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());       
        LayoutInflater inflater = getActivity().getLayoutInflater();
        V = inflater.inflate(R.layout.alertdialog_edytujprodukt, null);
        ((EditText)V.findViewById(R.id.editText_dialog_edytuj_produkt)).setText(mWybranyProdukt.getNazwa());
        ((EditText)V.findViewById(R.id.editText_dialog_edytuj_produkt)).setSelection(mWybranyProdukt.getNazwa().length());
               
        NumberPicker np = (NumberPicker)V.findViewById(R.id.numberPicker1_dialog_edytuj_produkt);
        np.setMinValue(0);
        np.setMaxValue(99999);
        np.setWrapSelectorWheel(false);
        np.setValue(mWybranyProdukt.getCena()/100);
        
        String s = new Integer(mWybranyProdukt.getCena()).toString();
        while(s.length()<2)s="0"+s;
        s=s.substring(s.length()-2);
        
        np = (NumberPicker)V.findViewById(R.id.numberPicker2_dialog_edytuj_produkt);
        np.setMinValue(0);
        np.setMaxValue(99);
        np.setWrapSelectorWheel(false);
        np.setValue(new Integer(1).parseInt(s));
        
        
        builder.setTitle("Edytuj produkt")
        	.setNegativeButton("Anuluj", null)
        	.setPositiveButton("Zastosuj", new DialogInterface.OnClickListener(){
        		public void onClick(DialogInterface dialog, int id) {
        			mWybranyProdukt.setNazwa(((EditText)V.findViewById(R.id.editText_dialog_edytuj_produkt)).getText().toString());
        			mWybranyProdukt.setCena(100*((NumberPicker)V.findViewById(R.id.numberPicker1_dialog_edytuj_produkt)).getValue() + ((NumberPicker)V.findViewById(R.id.numberPicker2_dialog_edytuj_produkt)).getValue());
        			mListener.onEdytowanoProdukt(mWybranyProdukt);
        		}
        	})
        	.setView(V);

        return builder.create();
	}
	
	DialogFragment_edytujprodukt setWybrany(Produkt p){
		this.mWybranyProdukt = p;
		return this;
	}
	
	
	
	
	interface EdycjaProduktyListener{
		public void onEdytowanoProdukt(Produkt p);
	}

	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EdycjaProduktyListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	
	
}
