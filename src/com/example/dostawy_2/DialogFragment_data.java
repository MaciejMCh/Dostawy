package com.example.dostawy_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.util.*;
import android.app.*;
import android.content.DialogInterface;
import java.util.*;
import java.text.*;

import com.example.dostawy_2.DialogFragment_dodajprodukt.DialogDodajProduktListener;

import android.widget.DatePicker;



public class DialogFragment_data extends DialogFragment {

	DateListener mListener;
	View V;
	FragmentHistoriaDostaw mFragment;
	
	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        V = getActivity().getLayoutInflater().inflate(R.layout.alertdialog_data, null);
        
        
        builder.setView(V)
        	.setTitle("Wybierz datę")
        	.setNegativeButton("Anuluj", null)
        	.setPositiveButton("Zastosuj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	String data = ((DatePicker)V.findViewById(R.id.datePicker1)).getDayOfMonth()
                			+"-"+((DatePicker)V.findViewById(R.id.datePicker1)).getMonth()
                			+"-"+((DatePicker)V.findViewById(R.id.datePicker1)).getYear();      
                mListener.onDateChange(new SimpleDateFormat(data),false);
                }
    
            });
        
        return builder.create();
	}
	
	
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	
	
        
    public interface DateListener{
    	public void onDateChange(SimpleDateFormat d, boolean o);
    }

}
