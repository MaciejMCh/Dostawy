package com.example.dostawy_2;

import android.os.Bundle;
import android.widget.*;
import android.app.*;
import android.content.DialogInterface;
import android.view.*;
import android.view.inputmethod.EditorInfo;

public class DialogFragment_dodajdostawce extends DialogFragment {
	
	DialogDodajListener mListener;
	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        
        
        
        final EditText input = new EditText(getActivity());
        input.setSingleLine(true);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        
        builder.setView(input);
        builder.setMessage("Wprowadź nazwę nowego dostawcy")
               .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.pozyt(input.getText().toString());
                	   
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
	            mListener = (DialogDodajListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement NoticeDialogListener");
	        }
	    }

	
	
	
	
	
	
	
	public interface DialogDodajListener{
		public void pozyt(String n);
	}
	
	
}
