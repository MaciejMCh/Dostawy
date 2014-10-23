package com.example.dostawy_2;

import com.example.dostawy_2.DialogFragment_dodajdostawce.DialogDodajListener;

import android.os.Bundle;
import android.widget.*;
import android.app.*;
import android.content.DialogInterface;
import android.view.*;

public class DialogFragment_dodajprodukt extends DialogFragment {

	DialogDodajProduktListener mListener;
	View v;
	int mWybranyDostawca;
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());       
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.alertdialog_nowyprodukt, null);
        NumberPicker np = (NumberPicker)v.findViewById(R.id.numberPicker1);
        np.setMinValue(0);
        np.setMaxValue(99999);
        np.setWrapSelectorWheel(false);
        np=(NumberPicker)v.findViewById(R.id.numberPicker2);
        np.setMinValue(0);
        np.setMaxValue(99);
        np.setWrapSelectorWheel(false);
        
        builder.setView(v);
        builder.setMessage("Nowy produkt")
               .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.dodaj(((EditText)v.findViewById(R.id.editText20)).getText().toString(),
                			   100*((NumberPicker)v.findViewById(R.id.numberPicker1)).getValue() + ((NumberPicker)v.findViewById(R.id.numberPicker2)).getValue(),
                			   DialogFragment_dodajprodukt.this.mWybranyDostawca
                			   );
                	   
                   }
               })
               .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });
        
        
        return builder.create();
    }
	
	public void setWybrany(int w){
		this.mWybranyDostawca=w;
	}
	
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogDodajProduktListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	
	
	public interface DialogDodajProduktListener{
		public void dodaj(String n, int c, int i);
	}
	
}
