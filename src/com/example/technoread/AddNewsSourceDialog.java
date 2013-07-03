package com.example.technoread;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class AddNewsSourceDialog extends DialogFragment {
	
	NewsSourceModel _model = null;
	Context _context = null;
	boolean _isNew = true;
	
	public AddNewsSourceDialog()
	{
		
	}
	public AddNewsSourceDialog(NewsSourceModel model,Context context)
	{
		_model = model;
		_context = context;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

	
    @Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		ConfigureActivity configureActivity = (ConfigureActivity)getActivity();
		if(configureActivity != null && _model != null)
		{
			configureActivity.Refresh(_model,_isNew);
		}
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.newssourceedit_layout, container, false);
        if(_model != null)
        {
        	// show the model values in the UI
        	_isNew = false;
        	PopulateUI(v);
        }
        Button saveBtn = (Button)v.findViewById(R.id._saveButton);
        saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				NewsSourceModel model = null;
				if(_isNew)
				{
					model = new NewsSourceModel();
				}
				else
				{
					model = _model;
				}
				FillModel(v,model);
				_model = model;
				AddNewsSourceDialog.this.dismiss();
			}

			private void FillModel(View view,NewsSourceModel model) {
				model.Title = SetTextFieldValues(view,R.id._titleEditText);
				model.Url = SetTextFieldValues(view, R.id._urlEditText);
				model.Description = SetTextFieldValues(view, R.id._descriptionEditText);
				CheckBox cb = (CheckBox)view.findViewById(R.id._openInlineCheckBox);
				model.OpenInline = cb.isChecked();
			}
			
			private String SetTextFieldValues(View view,int id)
			{
				EditText tv = (EditText)view.findViewById(id);
				if(tv.getText() != null)
				{
					return tv.getText().toString();
				}
				else
				{
					return "";
				}
			}
		});
        return v;
    }
	
	private void SetEditText(View view,int id,String value)
	{
		EditText tv = (EditText)view.findViewById(id);
		tv.setText(value);
	}
	private void PopulateUI(View view) {
		SetEditText(view,R.id._titleEditText,_model.Title);
		SetEditText(view,R.id._descriptionEditText	,_model.Description);
		SetEditText(view,R.id._urlEditText,_model.Url);
		CheckBox cb = (CheckBox)view.findViewById(R.id._openInlineCheckBox);
		cb.setChecked(_model.OpenInline);
	}
}
