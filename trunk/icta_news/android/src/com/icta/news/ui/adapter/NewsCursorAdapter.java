package com.icta.news.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class NewsCursorAdapter extends CursorAdapter {

	public NewsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub
		
		
		//example
		/*TextView summary = (TextView)view.findViewById(R.id.summary);
		summary.setText(cursor.getString(
				cursor.getColumnIndex(ExampleDB.KEY_EXAMPLE_SUMMARY)));*/

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		//example
		/*LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.item, parent, false);
		bindView(v, context, cursor);
		return v;*/
		
		return null;
	}

}
