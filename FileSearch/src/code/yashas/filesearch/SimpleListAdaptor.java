/**
 * 
 */
package code.yashas.filesearch;

import java.util.ArrayList;
import java.util.List;

import com.example.filesearch.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author hogwarts (Thats my computer name, my name is Yashas)
 * 
 */
public class SimpleListAdaptor extends BaseAdapter {

	private List<FilePojo> listOfDocs;
	private Context context;
	private static LayoutInflater inflater = null;
	private FilePojo tempValues = null;
	boolean[] checked;
	private List<FilePojo> listOfChecked;
	public boolean flag = false;

	public SimpleListAdaptor(List<FilePojo> listOfDocs, Context context) {
		super();

		this.listOfDocs = listOfDocs;
		this.context = context;
		checked = new boolean[listOfDocs.size()];
		listOfChecked = new ArrayList<FilePojo>();

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return listOfDocs.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public FilePojo getItem(int position) {
		return listOfDocs.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {
		public LinearLayout layout;
		public TextView fileNameText;
		public CheckBox box;
		public View colorView;
		public String filePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.simple_list_view, null);

			holder = new ViewHolder();
			holder.fileNameText = (TextView) vi.findViewById(R.id.textView1);
			holder.box = (CheckBox) vi.findViewById(R.id.checkBox1);
			holder.layout = (LinearLayout) vi.findViewById(R.id.parent_layout);
			holder.colorView = (View) vi.findViewById(R.id.color_view);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		if (flag == false) {
			holder.box.setVisibility(View.GONE);
		} else {
			holder.box.setVisibility(View.VISIBLE);
		}
		if (listOfDocs.size() <= 0) {
			holder.fileNameText.setText("No Data");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (FilePojo) listOfDocs.get(position);

			/************ Set Model values in Holder elements ***********/
			if (flag == false) {
				holder.colorView.setBackgroundColor(generateColors());
			}
			holder.fileNameText.setText(tempValues.getFileName());
			holder.filePath = tempValues.getFilePath();

			holder.layout.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					if (flag == false) {
						flag = true;
						notifyDataSetChanged();
					}
					return false;
				}
			});

			holder.layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (flag == false) {
						Intent intent = new Intent(context, FileDetails.class);
						intent.putExtra("FILE", listOfDocs.get(position));
						context.startActivity(intent);
					}

				}
			});

			// setting check boxs
			if (checked[position]) {
				holder.box.setChecked(true);
			} else {
				holder.box.setChecked(false);
			}

			holder.box.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (holder.box.isChecked()) {
						checked[position] = true;
						listOfChecked.add(new FilePojo(holder.fileNameText
								.getText().toString(), holder.filePath));
					} else {
						checked[position] = false;
						listOfChecked.remove(new FilePojo(holder.fileNameText
								.getText().toString(), holder.filePath));
					}
				}
			});

		}

		return vi;
	}

	public List<FilePojo> getAllCheckedItems() {
		return listOfChecked;
	}

	public void updateListOfFiles(List<FilePojo> list) {

		listOfDocs.removeAll(list);
		listOfChecked.removeAll(listOfChecked);
		checked = new boolean[listOfDocs.size()];
		flag = false;
		notifyDataSetChanged();
	}

	private int generateColors() {
		int array[] = context.getResources().getIntArray(R.array.androidcolors);
		int index = (int) (Math.random() * array.length);
		return array[index];
	}

	public void updateListOfFilesForSearch(List<FilePojo> listOfValuesMatching) {

		listOfDocs.removeAll(listOfDocs);
		listOfDocs.addAll(listOfValuesMatching);
		listOfChecked.clear();
		checked = new boolean[listOfDocs.size()];
		flag = false;
		notifyDataSetChanged();

	}

}
