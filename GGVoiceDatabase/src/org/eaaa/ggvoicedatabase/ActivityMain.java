package org.eaaa.ggvoicedatabase;

import java.util.List;

import org.eaaa.database.MyService;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class ActivityMain extends Activity
{
	private MyService service;
	private TextView txtContent;
	
	private static final int SPEECH_REQUEST = 0;
	
	//private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		
		service = MyService.getInstance(this);
		initView();
		
		//gestureDetector = createGestureDetector(this);
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu)
	{
		if(featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL)
		{
			getMenuInflater().inflate(R.menu.activity_main, menu);
			return true;
		}
		return super.onCreatePanelMenu(featureId, menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL)
		{
			switch (item.getItemId())
			{
			case R.id.Speak:
				startActivityForResult(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
				break;
			case R.id.Close:
				this.finish();
				service.close();
				break;
			default:
				return true;
			}
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == SPEECH_REQUEST && resultCode == RESULT_OK)
		{
			List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String spokenText = results.get(0);
			service.insertContent(spokenText);
			txtContent.setText(spokenText);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
//	
//	private GestureDetector createGestureDetector(Context context)
//	{
//		GestureDetector gestureDetector = new GestureDetector(context);
//		gestureDetector.setBaseListener(new GestureDetector.BaseListener()
//		{
//			
//			@Override
//			public boolean onGesture(Gesture arg0)
//			{
//				
//				return false;
//			}
//		});
//	}

	
	private void initView()
	{
		service.open();
		setContentView(R.layout.activity_main);
		txtContent = (TextView) findViewById(R.id.textView1);
		if(service.fetchLatestContent() != "")
		{
			txtContent.setText(service.fetchLatestContent());
		}
		else
		{
			txtContent.setText("No saved data!");
		}
	}
}
