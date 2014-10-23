package com.example.dostawy_2;

import com.example.dostawy_2.DialogFragment_edytujprodukt.EdycjaProduktyListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;



public class DialogFragment_podajilosc extends DialogFragment {	
	
	View V;
	
	DodajDoZamowieniaListener mListener;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        V=this.getActivity().getLayoutInflater().inflate(R.layout.alertdialog_podajilosc, null);

        this.ViewInit();
        builder
        	.setTitle("Podaj ilość sztuk")
        	.setNegativeButton("Anuluj", null)
        	.setPositiveButton("Dodaj", new DialogInterface.OnClickListener(){
        		public void onClick(DialogInterface dialog, int id) {
        			mListener.onDodalDoZamowienia(((NumberPicker)V.findViewById(R.id.numberPicker_podajilosc)).getValue());
        		}
        	})
        	.setView(V);
        
        return builder.create();
	}
	
	public void ViewInit(){
		NumberPicker np = (NumberPicker)V.findViewById(R.id.numberPicker_podajilosc);
		np.setMinValue(0);
		np.setMaxValue(Integer.MAX_VALUE);
		np.setWrapSelectorWheel(false);
	}
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DodajDoZamowieniaListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	public interface DodajDoZamowieniaListener{
		public void onDodalDoZamowienia(int i);
	}

}
