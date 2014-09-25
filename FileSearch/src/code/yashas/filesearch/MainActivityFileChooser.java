package code.yashas.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.filesearch.R;
/**
 * 
 * @author hogwarts (Thats my computer name, my name is Yashas)
 *
 */
public class MainActivityFileChooser extends Activity {

	private ListView listView;
	private Button buttonDelete, buttonUpload;
	private Spinner spinner_files;
	private EditText search_files;
	private ArrayList<FilePojo> checkedValue;
	private SimpleListAdaptor adaptor;
	private String ALL_FILE_TYPE = "All Files";
	private List<String> spinnerList;
	private List<FilePojo> listOfFilesFromSdcard;
	private final File sdCardFile = new File(Environment
			.getExternalStorageDirectory().getPath());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkedValue = new ArrayList<FilePojo>();
		listOfFilesFromSdcard = new ArrayList<FilePojo>();
		getListOfFilesFromSdCard();

		listView = (ListView) findViewById(R.id.listView1);
		buttonDelete = (Button) findViewById(R.id.button_delete);
		spinner_files = (Spinner) findViewById(R.id.spinner_type_of_files);
		search_files = (EditText) findViewById(R.id.search_edit_text);
		buttonUpload = (Button) findViewById(R.id.button_upload);

		adaptor = new SimpleListAdaptor(new ArrayList<FilePojo>(
				listOfFilesFromSdcard), getLayoutInflater().getContext());
		listView.setAdapter(adaptor);
		checkedValue = (ArrayList<FilePojo>) adaptor.getAllCheckedItems();

		setSpinnerActionNValues();
		setSearchActions();

		buttonDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkedValue.size() > 0) {
					deleteFiles(checkedValue);
					adaptor.updateListOfFiles(checkedValue);
				} else {
					Toast.makeText(MainActivityFileChooser.this,
							"Select Files to Delete", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		buttonUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {}
		});

	}

	private boolean deleteFiles(List<FilePojo> files) {
		if (files == null)
			return false;

		if (files.size() <= 0)
			return false;

		for (FilePojo file : files) {
			File filez = new File(file.getFilePath());
			if (filez.exists() && filez.isFile()) {
				filez.delete();
				// update files in list
				getListOfFilesFromSdCard();
			}
		}
		return false;

	}

	private void setSearchActions() {
		search_files.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				ArrayList<FilePojo> listOfValuesMatching = new ArrayList<FilePojo>();

				for (FilePojo val : getFileOfType(spinner_files
						.getSelectedItem().toString())) {
					if (val.getFileName().toLowerCase()
							.contains(s.toString().toLowerCase())) {
						listOfValuesMatching.add(val);
					}
				}

				adaptor.updateListOfFilesForSearch(listOfValuesMatching);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	private void setSpinnerActionNValues() {

		spinnerList = new ArrayList<String>();
		spinnerList.add("ALL FILES");
		spinnerList.add("PDF");
		spinnerList.add("JPG");
		spinnerList.add("PNG");
		spinnerList.add("MP3");
		spinnerList.add("3GP");
		spinnerList.add("MP4");
		spinnerList.add("M4A");
		spinnerList.add("AAC");
		spinnerList.add("MKV");
		spinnerList.add("AVI");
		spinnerList.add("GIF");
		spinnerList.add("BMP");
		spinnerList.add("WEBM");
		spinnerList.add("HTML");
		spinnerList.add("DOCX");
		spinnerList.add("DOC");
		spinnerList.add("PPTX");
		spinnerList.add("PPT");
		spinnerList.add("XLS");
		spinnerList.add("XLSX");

		ArrayAdapter<String> spinner_adaptor = new ArrayAdapter<String>(this,
				R.layout.custom_text_layout, spinnerList);

		spinner_files.setAdapter(spinner_adaptor);

		spinner_files.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adv, View v,
					int position, long id) {

				adaptor.updateListOfFilesForSearch(getFileOfType(spinnerList
						.get(position)));
				search_files.setText("");
			}

			@Override
			public void onNothingSelected(AdapterView<?> adv) {
			}
		});

	}

	/**
	 * Get all files in the sdcard
	 * 
	 * @param file
	 */
	private void listAllFiles(File file) {

		if (file.isFile() && !file.getName().startsWith(".")) {
			listOfFilesFromSdcard.add(new FilePojo(file.getName(), file
					.getPath()));
			return;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listAllFiles(files[i]);
			}
		}

	}

	public void getListOfFilesFromSdCard() {
		listOfFilesFromSdcard.clear();
		listAllFiles(sdCardFile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		if (adaptor.flag == true) {
			adaptor.flag = false;
			adaptor.updateListOfFiles(new ArrayList<FilePojo>());
		}
	}

	private List<FilePojo> getFileOfType(String extension) {
		List<FilePojo> listOfMatchedFiles = new ArrayList<FilePojo>();

		if (extension.equalsIgnoreCase("all files")) {
			return listOfFilesFromSdcard;
		}

		for (FilePojo file : listOfFilesFromSdcard) {
			if (file.getFileName().endsWith("." + extension.toLowerCase())) {
				listOfMatchedFiles.add(file);
			}
		}
		return listOfMatchedFiles;
	}

}
