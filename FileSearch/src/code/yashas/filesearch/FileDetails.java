package code.yashas.filesearch;

import com.example.filesearch.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FileDetails extends Activity {
	
	private TextView fileName;
	private TextView filePath;
	private FilePojo file;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_description);
		
		file = (FilePojo) this.getIntent().getSerializableExtra("FILE");
		
	  getUiElements();
	  setDatatoUiElements();
	  
	}


	private void setDatatoUiElements() {
		 fileName.setText(file.getFileName());
		 filePath.setText(file.getFilePath());
		
	}


	private void getUiElements() {
		 fileName = (TextView) findViewById(R.id.file_name);
		 filePath = (TextView) findViewById(R.id.file_path);
		
	}
}
