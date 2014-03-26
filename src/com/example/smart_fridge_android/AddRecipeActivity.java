package com.example.smart_fridge_android;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView.OnEditorActionListener;


public class AddRecipeActivity extends Activity {


	private Button add;
	private List <String> ingredlist = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_recipe);
		add = (Button) findViewById(R.id.addBtn);
        if (add != null)
        	add.setVisibility(View.INVISIBLE);
	}
	
	protected void onPause() {
		super.onPause();
		add.setVisibility(View.VISIBLE); //Make button visible again when you leave this view.
	}
	
	public void onButtonClick(View v) {
		
		NavigationBar navBar = new NavigationBar();
        navBar.onButtonClick(v, getApplicationContext());
        switch (v.getId()){
        
        case R.id.addingred:
        	
        	EditText mEdit; 
			mEdit = (EditText)findViewById(R.id.IngredientField);
        	if (mEdit.getText().toString().equals("")) {
        		Toast.makeText(getApplicationContext(), "Please enter an ingredient name", Toast.LENGTH_SHORT).show();
        		return;
        	}
        	ingredlist.add(mEdit.getText().toString());
        	mEdit.setText("");    	
        	break;
        	
        case R.id.showlist:
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	EditText recName;
        	recName = (EditText)findViewById(R.id.RecipeNameField);
        	
        	String recipeN = recName.getText().toString();
        	
        	String message = ""; 
  
        	for(String ingredient : ingredlist)
        	{
        		message = message + "\n" + ingredient;
        	}
        	          	
        	builder.setMessage(message);
        	builder.setTitle("Current ingredients for "+recipeN);
        	builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
								@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();					
				}
				}
        	);
        	builder.create();
        	builder.show();          	
        	break;
        	
        case R.id.addrec:
        	Recipe recipe = new Recipe();
        	
        	String name = ((EditText)findViewById(R.id.RecipeNameField)).getText().toString();
			if(name.isEmpty()) {
				Toast.makeText(getApplicationContext(), "Name is required", Toast.LENGTH_LONG).show();
				break;
			}
			recipe.setName(name);
			
			String directions = ((EditText)findViewById(R.id.InstructionsField)).getText().toString();
			if(directions.isEmpty()) {
				Toast.makeText(getApplicationContext(), "Directions are required", Toast.LENGTH_LONG).show();
				break;
			}
			recipe.setDirections(directions);
			
			
			if(ingredlist.isEmpty()) {
				Toast.makeText(getApplicationContext(), "Ingredients are required", Toast.LENGTH_LONG).show();
				break;
			}
			recipe.setIngredientsList(ingredlist);
        	
			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			db.addRecipe(recipe);
			
			Intent i = new Intent(this, MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(i);
        	break;
        }
	}	
}