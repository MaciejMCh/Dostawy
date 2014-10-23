	package com.example.dostawy_2;
	
	import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.*;
import java.util.*;
import java.lang.*;

public class adapter_listadostaw_ex extends BaseExpandableListAdapter{
 
    private Context mContext;
    private List<Dostawa> mListaDostaw;
    private LinearLayout ll1;
    private RelativeLayout ll2;
    private FragmentHistoriaDostaw mFragment;
    private ArrayList<Integer> mListaDivider;
 
    public adapter_listadostaw_ex(Context context, List<Dostawa> Lista){
        this.mContext = context;
        this.mListaDostaw = Lista;
        this.mListaDivider = new ArrayList<Integer>();
        this.znajdzDividery();
    }
    
    public void znajdzDividery(){
    	this.mListaDivider.add(0);
    	int divOstatni=0;
    	for(int i=1;i<=this.mListaDostaw.size()-1;i++){
    		if(1==this.mListaDostaw.get(i).getData().toPattern().substring(0,this.mListaDostaw.get(i).getData().toPattern().length()-9).compareTo(this.mListaDostaw.get(divOstatni).getData().toPattern().substring(0,this.mListaDostaw.get(divOstatni).getData().toPattern().length()-9))){
    			this.mListaDivider.add(i);
    			divOstatni=i;
    		}
    	}
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return null;
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) { 
        if (convertView == null) {
        	LayoutInflater li=LayoutInflater.from(mContext);
			 ll1 = (LinearLayout)li.inflate(R.layout.listitem_ex_prdukt, null);
        }else {  
			ll1 = (LinearLayout) convertView;  
		}
        Log.d("baza", "getChildView");
        ((TextView)ll1.findViewById(R.id.textView_trescdostawy_nazwa)).setText(this.mListaDostaw.get(groupPosition).getLista().get(childPosition).getNazwa());
        ((TextView)ll1.findViewById(R.id.textView_trescdostawy_ilosc)).setText(this.mListaDostaw.get(groupPosition).getLista().get(childPosition).getIlosc()+"");
        ((TextView)ll1.findViewById(R.id.textView_trescdostawy_cena)).setText(Zlotowki.getString(this.mListaDostaw.get(groupPosition).getLista().get(childPosition).getCena()));
        ((TextView)ll1.findViewById(R.id.textView4_trescdostawy_suma)).setText(Zlotowki.getString(this.mListaDostaw.get(groupPosition).getLista().get(childPosition).getSuma()));
        return ll1;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	//Log.d("rozmiar", this.mListaDostaw.get(groupPosition).getLista().size()+"");
    	return this.mListaDostaw.get(groupPosition).getLista().size();

    }
 
    @Override
    public Object getGroup(int groupPosition) {
    	
        return this.mListaDostaw.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this.mListaDostaw.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	if (convertView == null) {
        	LayoutInflater li=LayoutInflater.from(mContext);
			ll2 = (RelativeLayout)li.inflate(R.layout.listitem_ex_dostawa_root, null);
        }else {  
			ll2 = (RelativeLayout) convertView;  
		}
    	
    	((TextView)ll2.findViewById(R.id.textView_ex_dostawa_nazwa)).setText(this.mListaDostaw.get(groupPosition).getNazwaDostawcy());
    	((TextView)ll2.findViewById(R.id.textView_ex_dostawa_cena)).setText(Zlotowki.getString(this.mListaDostaw.get(groupPosition).getWartosc()));
    	if(this.mListaDivider.contains(groupPosition)){
    		((TextView)ll2.findViewById(R.id.textView_separator)).setText(this.mListaDostaw.get(groupPosition).getData().toPattern().substring(0,this.mListaDostaw.get(groupPosition).getData().toPattern().length()-9));
    		((TextView)ll2.findViewById(R.id.textView_separator)).setVisibility(View.VISIBLE);
    	}else{
    		((TextView)ll2.findViewById(R.id.textView_separator)).setVisibility(View.GONE);
    	}
    	return ll2;
        
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    public void ZaladujDostawe(){
    	
    }
    
    public void run(){
    	
    }

}
