package com.iwan_b.chummersr5.fragments.MainStats;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.Quality;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerMethods;
import com.iwan_b.chummersr5.utility.ChummerXML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class QualitiesFragment extends Fragment {

	private View viewFragment;

	// All the qualities that can be manipulated.
	private ArrayList<Quality> allQualities;

	// The Adapter that displays the qualities
	private ArrayAdapter<String> qualitiesAdapter;

	// The qualities to display in the list
	private ArrayList<String> displayQuals;

	// How much karma has been used so far
	private int currentKarmaUsed = 0;

	// Whether this is a display of positive or negative
	private Boolean isPositive;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		viewFragment = inflater.inflate(R.layout.fragment_mainstats_qualities, container, false);

		final Bundle bundleData = getArguments();

		String xmlFileLocation = bundleData.getString("FileLocation", "");
		isPositive = bundleData.getBoolean("isPositive");

		allQualities = readQualitiesXML(xmlFileLocation);

		final ListView listViewOfQualities = (ListView) viewFragment.findViewById(R.id.fragment_mainstats_qualities_quality_ListView);

		// Get and set all the current qualities
		// Test to see if the array is empty or not.
		if (displayQuals == null) {
			displayQuals = new ArrayList<>();

			// Qualities the character is considered to start with
			ArrayList<Modifier> qualities;
			if (isPositive) {
				// TODO change "positive_quality" to assets
				qualities = ShadowrunCharacter.getCharacter().getModifiers().get("positive_quality");
			} else {
				qualities = ShadowrunCharacter.getCharacter().getModifiers().get("negative_quality");
			}

			// if not empty look through all the modifiers for positive qualities
			if (qualities != null) {
				for (final Modifier m : qualities) {
					displayQuals.add(m.getDisplayText());
				}
			}

			// Add the current existing qualities
			ArrayList<Quality> existingQualities;
			if (isPositive) {
				existingQualities = ShadowrunCharacter.getCharacter().getPositiveQualities();
			} else {
				existingQualities = ShadowrunCharacter.getCharacter().getNegativeQualities();
			}

			if (existingQualities != null && !existingQualities.isEmpty()) {
				for (final Quality q : existingQualities) {
					displayQuals.add(q.getName());
				}
			}
		}

		qualitiesAdapter = new ArrayAdapter<>(getActivity().getBaseContext(),
				android.R.layout.simple_list_item_1, displayQuals);

		// Set the footerview to be the add qualities button
		listViewOfQualities.setFooterDividersEnabled(true);

		final TextView footerView = new TextView(viewFragment.getContext());

		if (isPositive) {
			// TODO get the assets
			footerView.setText(R.string.AddNewPositiveQuality);
		} else {
			footerView.setText(R.string.AddNewNegativeQuality);
		}

		footerView.setTextSize(24);
		footerView.setGravity(Gravity.CENTER_HORIZONTAL);

		listViewOfQualities.addFooterView(footerView);

		listViewOfQualities.setAdapter(qualitiesAdapter);

		// When the user clicks on the add quality button display a list of available qualities
		footerView.setOnClickListener(new QualitiesListListener());

		// What to do when each quality is clicked.
		listViewOfQualities.setOnItemClickListener(new QualitiesInfoDialog(null, false));

		return viewFragment;
	}

	/**
	 * Add a quality to the display list
	 * 
	 * @param qualitySelected
	 *            The quality to add.
	 */
	private void addQuality(final Quality qualitySelected) {
		Log.i(ChummerConstants.TAG, "-----------------------------------------");
		Log.i(ChummerConstants.TAG, "Add Quality Start");
		if (qualitySelected != null) {
			Log.i(ChummerConstants.TAG, "qualitySelected is: " + qualitySelected.toString());

			for (final Modifier mod : qualitySelected.getMods()) {
				// Split by ( or )
				final String[] name = mod.getName().split("\\(|\\)");

				String fullname = name[0];
				// Change the name here
				for (int i = 1; i < name.length; i++) {
					String sub = name[i];
					if (sub.equalsIgnoreCase("list")) {
						fullname += qualitySelected.getSpinnerItem().toLowerCase();
					}
					if (sub.equalsIgnoreCase("textinput")) {
						fullname += qualitySelected.getUserTextInputString().toLowerCase();
					}
				}

				mod.setName(fullname);

				if (mod.getAmount() == 0) {
					mod.setAmount(qualitySelected.getRating());
				}

				Log.i(ChummerConstants.TAG, "Full name: " + fullname);

				if (ShadowrunCharacter.getCharacter().getModifiers().containsKey(fullname)) {
					ShadowrunCharacter.getCharacter().getModifiers().get(fullname).add(mod);
				} else {
					ArrayList<Modifier> temp = new ArrayList<>();
					temp.add(mod);
					ShadowrunCharacter.getCharacter().getModifiers().put(mod.getName(), temp);
				}

			}

			if (isPositive) {
				ShadowrunCharacter.getCharacter().getPositiveQualities().add(qualitySelected);
			} else {
				ShadowrunCharacter.getCharacter().getNegativeQualities().add(qualitySelected);
			}

			displayQuals.add(qualitySelected.getName());
			qualitiesAdapter.notifyDataSetChanged();
			Log.i(ChummerConstants.TAG, "Add Quality end");
			Log.i(ChummerConstants.TAG, "-----------------------------------------");
		}
	}

	private void removeQualitiy(final Quality qualitySelected) {
		Log.i(ChummerConstants.TAG, "-----------------------------------------");
		Log.i(ChummerConstants.TAG, "removeQualitiy Quality Start");
		if (qualitySelected != null) {
			Log.i(ChummerConstants.TAG, "Name of Quality: " + qualitySelected.getName());

			Boolean found = false;
			for (int i = 0; i < displayQuals.size(); i++) {
				// get the quality/info that has the same name as the item
				// selected
				final ArrayList<String> qualityDataFromDisplay = splitQualityNameString(displayQuals.get(i));

				if (qualityDataFromDisplay.get(0).equalsIgnoreCase(qualitySelected.getName())) {
					displayQuals.remove(i);
					found = true;
				}
			}

			if (found) {

				ArrayList<Quality> qualities;

				if (isPositive) {
					qualities = ShadowrunCharacter.getCharacter().getPositiveQualities();
				} else {
					qualities = ShadowrunCharacter.getCharacter().getNegativeQualities();
				}

				for (int i = 0; i < qualities.size(); i++) {
					Quality q = qualities.get(i);

					if (q.getName().equalsIgnoreCase(qualitySelected.getName())) {
						if (isPositive) {
							ShadowrunCharacter.getCharacter().getPositiveQualities().remove(i);
						} else {
							ShadowrunCharacter.getCharacter().getNegativeQualities().remove(i);
						}
						break;
					}
				}

				for (Modifier m : qualitySelected.getMods()) {
					Log.i(ChummerConstants.TAG, "Mod name: " + m.getName());

					if (ShadowrunCharacter.getCharacter().getModifiers().containsKey(m.getName())) {
						ArrayList<Modifier> mods = ShadowrunCharacter.getCharacter().getModifiers().get(m.getName());

						for (int i = 0; i < mods.size(); i++) {
							Modifier modsinplay = mods.get(i);
							if (modsinplay.equals(m)) {
								ShadowrunCharacter.getCharacter().getModifiers().get(m.getName()).remove(modsinplay);
							}
						}

					}
				}

				Log.i(ChummerConstants.TAG, ShadowrunCharacter.getCharacter().getModifiers().toString());
				// ((NewCharacterInput)
				// getActivity()).getSRChar().getModifiers()
			}

			qualitiesAdapter.notifyDataSetChanged();
			Log.i(ChummerConstants.TAG, "removeQualitiy Quality Start");
			Log.i(ChummerConstants.TAG, "-----------------------------------------");
		}
	}

	/**
	 * Get a new copy of the quality that has the same name
	 * 
	 * @param qualityName
	 *            Name of the quality
	 * @return Returns quality data
	 */
	private Quality getNewQuality(final String qualityName) {
		// Log.i(ChummerConstants.TAG, "S is:[" + s + "]");
		for (final Quality q : allQualities) {
			// Log.i(ChummerConstants.TAG, "Q is:[" + q.getName() + "]");
			if (q.getName().equalsIgnoreCase(qualityName)) {
				return new Quality(q);
			}
		}
		return null;
	}

	/**
	 * Get the quality that is currently being used
	 * 
	 * @param qualityName
	 *            Name of the quality
	 * @return Returns quality data
	 */
	private Quality getCurrentQuality(final String qualityName) {
		ArrayList<Quality> qualities;

		if (isPositive) {
			qualities = ShadowrunCharacter.getCharacter().getPositiveQualities();
		} else {
			qualities = ShadowrunCharacter.getCharacter().getNegativeQualities();
		}

		// Log.i(ChummerConstants.TAG, "S is:[" + s + "]");
		for (final Quality q : qualities) {
			// Log.i(ChummerConstants.TAG, "Q is:[" + q.getName() + "]");
			if (q.getName().equalsIgnoreCase(qualityName)) {
				return new Quality(q);
			}
		}
		return null;
	}

	/**
	 * Split the string into correct substrings. E.g. Bilingual(Rating 6) should
	 * turn into [Bilingual, Rating 6]
	 * 
	 * @param item
	 *            Name of the quality
	 * @return an ArrayList of Strings that contain the data
	 */
	private ArrayList<String> splitQualityNameString(final String item) {
		// get the quality/info that has the same name as the item selected
		final String[] split = item.split("\\(|\\)");

		ArrayList<String> stringInfo = new ArrayList<>();
		for (final String temp : split) {
			if (!temp.isEmpty()) {
				stringInfo.add(temp);
			}
		}
		return stringInfo;
	}

	private class QualitiesListListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			// Need to make this final so the onItemClick can call the dismiss
			// button
			final Dialog dialogVar = new Dialog(getActivity());

			final ListView listOfQualities = new ListView(getActivity().getBaseContext());

			final ArrayList<String> listData = new ArrayList<>();

			// Remove duplicates from the list of qualities they can take.
			for (final Quality q : allQualities) {
				// Remove it from the list
				// TODO make this more dynamic
				if (!displayQuals.contains(q.getName())) {
					listData.add(q.getName());
				}
			}

			final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(),
					android.R.layout.simple_list_item_1, listData);

			listOfQualities.setAdapter(adapter);

			// When they click on the quality display another dialog...
			listOfQualities.setOnItemClickListener(new QualitiesInfoDialog(dialogVar, true));

			dialogVar.setCancelable(true);

			dialogVar.setContentView(listOfQualities);

			if (isPositive) {
				dialogVar.setTitle("Positive Qualities");
			} else {
				dialogVar.setTitle("Negative Qualities");
			}
			dialogVar.show();
		}
	}

	/**
	 * Display the info for the quality in a dialog
	 */
	private class QualitiesInfoDialog implements OnItemClickListener {

		// Parent Dialog to dismiss
		private Dialog dialogVar;
		// Whether to add or delete the quality
		private boolean add;

		private Quality qualitySelected;
		private TextView ratingTextView;
		private Spinner spinnerListOfData;
		private EditText userInputEditText;
		private RadioGroup radioGroupForCostListLayout;
		private ArrayList<String> extraInfo;

		public QualitiesInfoDialog(final Dialog dialogVar, final boolean add) {
			this.dialogVar = dialogVar;
			this.add = add;
		}

		private void loadSpinner(final Context context) {
			spinnerListOfData = new Spinner(context);
			// getcount returns 0 if there is nothing
			ArrayList<String> testList = new ArrayList<>();

			// Try to get the list first
			if (qualitySelected.getList() != null && !(qualitySelected.getList().isEmpty())) {
				// TODO change the data/list part
				testList = ChummerXML.readStringXML(getActivity(), "data/" + qualitySelected.getList());
			} else {
				if (qualitySelected.getSpinnerListDataDisplay() != null
						&& !(qualitySelected.getSpinnerListDataDisplay().isEmpty())) {
					Collections.addAll(testList,qualitySelected.getSpinnerListDataDisplay().split(","));
				}
			}

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
					testList);

			spinnerListOfData.setAdapter(dataAdapter);

			spinnerListOfData.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					qualitySelected.setSpinnerItem(parent.getItemAtPosition(pos).toString());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});

			if (!add) {
				// the value you want the position for
				String myString = qualitySelected.getSpinnerItem();

				int spinnerPosition = dataAdapter.getPosition(myString);

				// set the default according to value
				spinnerListOfData.setSelection(spinnerPosition);
				spinnerListOfData.setEnabled(false);
			}
		}

		@Override
		public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
			final String item = (String) parent.getItemAtPosition(position);

			Log.i(ChummerConstants.TAG, "onItemClick was: " + item);

			// get the quality/info that has the same name as the item selected
			extraInfo = splitQualityNameString(item);

			qualitySelected = null;
			if (add) {
				// get(0) should always return the name of the quality
				qualitySelected = getNewQuality(extraInfo.get(0));
			} else {
				// get(0) should always return the name of the quality
				qualitySelected = getCurrentQuality(extraInfo.get(0));
			}

			if (qualitySelected == null) {
				Log.i(ChummerConstants.TAG, "The quality can't be found: " + item);
			}

			// See if we have anything to display
			if (qualitySelected != null) {
				// Build the dialog to display the quality
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				LayoutInflater inflater = getActivity().getLayoutInflater();

				View dialogView = inflater.inflate(R.layout.fragment_mainstats_qualities_display, parent, false);

				final TextView infoname = (TextView) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_infoname);
				final TextView infocost = (TextView) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_infocost_txtview);
				final TextView infosummary = (TextView) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_info_summary);
				final TextView infobook = (TextView) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_infobook);
				final TextView infopage = (TextView) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_infopage);

				radioGroupForCostListLayout = (RadioGroup) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_radiocostgroup);

				// Extra info goes here
				LinearLayout extraInfoLayout = (LinearLayout) dialogView.findViewById(R.id.fragment_mainstats_qualities_display_info_extrabuttons);

				// ----------------------------------------------------------------------------------
				// Extra info goes here

				// If the user has a cost list display it
				ArrayList<Integer> costList = new ArrayList<>();
				for (final String costy : qualitySelected.getCostList().split(",")) {
					// try to add the integer. The user could have entered poor
					// xml.
					try {
						costList.add(Integer.valueOf(costy));
					} catch (NumberFormatException e) {
						Log.i(ChummerConstants.TAG, "Integer XML data was corrupted: " + e.getMessage());
					}
				}

				// Load the spinner
				loadSpinner(dialogView.getContext());

				// Default rating is always one
				ratingTextView = new TextView(viewFragment.getContext());
				ratingTextView.setText("1");

				// If the quality has a different max rating
				if (qualitySelected.getMaxRating() > 1) {
					// TODO change the hardcode
					infocost.setText(costList.get(0) + " Karma Per Rating (Max Rating "
							+ qualitySelected.getMaxRating() + ")");

					qualitySelected.setCost(costList.get(0));

					// Container to hold the child views
					final LinearLayout linley = new LinearLayout(viewFragment.getContext());

					final TextView title = new TextView(viewFragment.getContext());
					title.setText("Rating: ");

					final Button subButton = new Button(viewFragment.getContext());
					subButton.setText("-");

					final Button addButton = new Button(viewFragment.getContext());
					addButton.setText("+");

					subButton.setOnClickListener(new RatingOnClickListener(ratingTextView, 1, qualitySelected
							.getMaxRating(), false));
					addButton.setOnClickListener(new RatingOnClickListener(ratingTextView, 1, qualitySelected
							.getMaxRating(), true));

					// If we aren't adding it disable everything.
					if (!add) {
						ratingTextView.setText("" + qualitySelected.getRating());
						title.setEnabled(false);
						subButton.setEnabled(false);
						ratingTextView.setEnabled(false);
						addButton.setEnabled(false);
					}

					linley.addView(title);
					linley.addView(subButton);
					linley.addView(ratingTextView);
					linley.addView(addButton);

					extraInfoLayout.addView(linley);
				} else {
					if (costList.size() == 1) {
						infocost.setText(costList.get(0) + " karma");
						qualitySelected.setCost(costList.get(0));
					} else {
						// If there is more than one cost associated with the
						// quality then display a list
						infocost.setText("");

						ArrayList<String> costNameList = new ArrayList<>();

						if (qualitySelected.getCostListData() != null && !qualitySelected.getCostListData().isEmpty()) {
							Collections.addAll(costNameList, qualitySelected.getCostListData().split(","));
						}

						for (int i = 0; i < costList.size(); i++) {
							final int individualCost = costList.get(i);

							String name = "";

							if (!costNameList.isEmpty()) {
								if (i < costNameList.size()) {
									name = costNameList.get(i);
								} else {
									name = costNameList.get(costNameList.size() - 1);
								}
							}

							RadioButton rb = new RadioButton(viewFragment.getContext());
							rb.setText(name + " (" + individualCost + " karma)");

							rb.setTag(individualCost);

							rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
								@Override
								public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
									// Log.i(ChummerConstants.TAG,"buttonView.getText().toString(): "+buttonView.getText().toString());
									int cost = (Integer) buttonView.getTag();
									qualitySelected.setCost(cost);
								}
							});

							if (!add) {
								if (individualCost == qualitySelected.getCost()) {
									rb.setChecked(true);
								}
								rb.setEnabled(false);
							}

							radioGroupForCostListLayout.addView(rb);
						}
					}
				}

				// If the spinnerList is not null then add to the view
				if (spinnerListOfData.getCount() != 0) {
					extraInfoLayout.addView(spinnerListOfData);
				}

				// If the quality is only for magicians
				if (qualitySelected.getMagicOnly()) {
					final TextView magicOnlyTextView = new TextView(viewFragment.getContext());
					// TODO change the hardcode text
					magicOnlyTextView.setText("Magic Users only");
					extraInfoLayout.addView(magicOnlyTextView);
				}

				// If the quality is only for technomancers
				if (qualitySelected.getTechnomancerOnly()) {
					final TextView technoOnlyTextView = new TextView(viewFragment.getContext());
					// TODO change the hardcode text
					technoOnlyTextView.setText("Technomancer Users only");
					extraInfoLayout.addView(technoOnlyTextView);
				}

				// If the quality is only for Mundanes
				if (qualitySelected.getMundaneOnly()) {
					final TextView mundaneTextView = new TextView(viewFragment.getContext());
					// TODO change the hardcode text
					mundaneTextView.setText("Mundane Users only");
					extraInfoLayout.addView(mundaneTextView);
				}

				// If the quality allows for user input
				if (qualitySelected.isUserTextInput()) {
					userInputEditText = new EditText(viewFragment.getContext());
					if (!add) {
						userInputEditText.setText(qualitySelected.getUserTextInputString());
						userInputEditText.setEnabled(false);
					}
					extraInfoLayout.addView(userInputEditText);
				}

				// End of extra info
				// ------------------------------------------------------------------------------

				infoname.setText(qualitySelected.getName());
				infosummary.setText(qualitySelected.getSummary());
				infobook.setText(qualitySelected.getBook());
				infopage.setText(qualitySelected.getPage());

				builder.setView(dialogView);

				if (add) {
					if (qualitySelected.getMagicOnly() || qualitySelected.getTechnomancerOnly()
							|| qualitySelected.getMundaneOnly()) {
						// Test to see if magic or technomancer or mundane
						if ((qualitySelected.getMagicOnly() && ShadowrunCharacter.getCharacter().getAttributes()
								.getBaseMagic() > 0)
								|| (qualitySelected.getTechnomancerOnly() && ShadowrunCharacter.getCharacter()
										.getAttributes().getBaseRes() > 0)
								|| (qualitySelected.getMundaneOnly()
										&& ShadowrunCharacter.getCharacter().getAttributes().getBaseRes() == 0 && ShadowrunCharacter
										.getCharacter().getAttributes().getBaseMagic() == 0)) {
							// Add the add button
							addButtonBuilder(builder);
						}
					} else {
						// Add the add button regardless
						addButtonBuilder(builder);
					}

				} else {
					removeButtonBuilder(builder);
				}

				// Always add a cancel button
				builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});

				builder.create();
				builder.show();
			}
		}

		private void addButtonBuilder(final AlertDialog.Builder builder) {

			builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					boolean valid = true;

					// Get the most currentKarmaCounter
					int currentKarmaCounter = ShadowrunCharacter.getKarma();

					int cost = qualitySelected.getCost();

					if (radioGroupForCostListLayout.getChildCount() > 0) {
						RadioButton rb = (RadioButton) radioGroupForCostListLayout
								.findViewById(radioGroupForCostListLayout.getCheckedRadioButtonId());
						// Test to see if the user selected something from the
						// radio group
						if (rb == null) {
							ChummerMethods.displayToast(getActivity(), "You haven't selected anything!");
							valid = false;
						}
					}

					// The cost of the quality is the base cost * the rating. Rating is defaulted to 1
					int costOfQuality;

					costOfQuality = (cost * qualitySelected.getRating());

					// Check to see if we have enough karma to spend, and that
					// we haven't reached the max cap
					if (valid && (currentKarmaCounter - costOfQuality) >= 0
							&& Math.abs((currentKarmaUsed + costOfQuality)) <= ChummerConstants.maxKarmaUsed) {

						currentKarmaCounter -= costOfQuality;
						currentKarmaUsed += costOfQuality;

						// Only checked here so that android doesn't keep resetting the variable when the user is still inputing data.
						if (qualitySelected.isUserTextInput()) {
							// Make sure the user actually entered something
							if (!(userInputEditText.getText().toString().isEmpty())) {
								qualitySelected.setUserTextInputString(userInputEditText.getText().toString());
							} else {
								qualitySelected.setUserTextInputString("");
							}
						}

						addQuality(qualitySelected);
					} else {
						if (valid) {
							ChummerMethods.displayToast(getActivity(),
									"You don't have enough karma to buy this.");
						}
					}

					ShadowrunCharacter.setKarma(currentKarmaCounter);
					MainContainer.updateKarma();
					// Only dismiss it if the user enters something valid
					if (dialogVar != null && valid) {
						dialogVar.dismiss();
					}

				}
			});
		}

		private void removeButtonBuilder(final AlertDialog.Builder builder) {
			builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					int currentKarmaCounter = ShadowrunCharacter.getKarma();

					int cost = qualitySelected.getCost();

					int costOfQuality = (cost * qualitySelected.getRating());

					currentKarmaCounter += costOfQuality;
					currentKarmaUsed -= costOfQuality;

					removeQualitiy(qualitySelected);

					ShadowrunCharacter.setKarma(currentKarmaCounter);
					MainContainer.updateKarma();
					if (dialogVar != null) {
						dialogVar.dismiss();
					}

				}
			});
		}

		/**
		 * Used when the quality requires the user to select a rating.
		 */
		private class RatingOnClickListener implements OnClickListener {
			// Which textfield to modify
			private TextView ratingDisplayText;
			// Whether to add or subtract
			private boolean addition;

			// Limits on the attributes
			private int baseRating;
			private int maxRating;

			public RatingOnClickListener(final TextView ratingDisplayText, final int baseRating, final int maxRating,
					final boolean addition) {
				this.ratingDisplayText = ratingDisplayText;
				this.addition = addition;

				this.baseRating = baseRating;
				this.maxRating = maxRating;

			}

			@Override
			public void onClick(View v) {
				Integer currentRating = qualitySelected.getRating();

				if (addition) {
					if (currentRating + 1 <= maxRating) {
						currentRating++;
					}
				} else {
					if (currentRating - 1 >= baseRating) {
						currentRating--;
					}
				}
				qualitySelected.setRating(currentRating);
				ratingDisplayText.setText(currentRating.toString());
			}
		}

		// --------------------------------- end of QualitiesInfoDialog
	}

	private ArrayList<Quality> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<Quality> qualities = new ArrayList<>();
		Modifier m = null;
		Quality q = null;

		boolean mod = false;

		int eventType = parser.getEventType();
		// TODO change all the hardcoded xml properties used further down
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name;
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				name = parser.getName();
				// Log.i(ChummerConstants.TAG, "START_DOCUMENT " + name);
				break;
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if (name.equalsIgnoreCase("quality")) {
					q = new Quality();
				} else if (q != null) {
					if (name.equalsIgnoreCase("mod")) {
						m = new Modifier();
						mod = true;
					}

					if (mod) {
						switch(name.toLowerCase()){
							case "name":
								m.setName(parser.nextText());
								break;
							case "amount":
								Integer i;
								try {
									i = Integer.valueOf(parser.nextText());
								} catch (NumberFormatException e) {
									i = 0;
								}
								m.setAmount(i);
								break;
							case "displaytext":
								m.setDisplayText(parser.nextText());
								break;
							case "summary":
								m.setSummary(parser.nextText());
								break;
							case "book":
								m.setBook(parser.nextText());
								break;
							case "page":
								m.setPage(parser.nextText());
						}
					} else {
						String s = parser.nextText();
						Integer i;

						switch(name.toLowerCase()){
							case "name":
								q.setName(s);
								break;
							case "costlist":
								q.setCostList(s);
								break;
							case "book":
								q.setBook(s);
								break;
							case "page":
								q.setPage(s);
								break;
							case "maxrating":
								// try to add the integer.
								try {
									q.setMaxRating(Integer.valueOf(s));
								} catch (NumberFormatException e) {
									Log.i(ChummerConstants.TAG, "Integer XML data was corrupted: " + e.getMessage());
								}
								break;
							case "summary":
								q.setSummary(s);
								break;
							case "list":
								q.setList(s);
								break;
							case "magic":
								if (s.equalsIgnoreCase("true")) {
									q.setMagicOnly(true);
								}
								break;
							case "technomancer":
								if (s.equalsIgnoreCase("true")) {
									q.setTechnomancerOnly(true);
								}
								break;
							case "textinput":
								if (s.equalsIgnoreCase("true")) {
									q.setUserTextInput(true);
								}
								break;
							case "mundane":
								if (s.equalsIgnoreCase("true")) {
									q.setMundaneOnly(true);
								}
								break;
							case "costlistdata":
								q.setCostListData(s);
								break;
							case "spinnerlistdata":
								q.setSpinnerListDataDisplay(s);
								break;
						}

					}
				}
				// Log.i(ChummerConstants.TAG, "START_TAG " + name);
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();

				switch(name.toLowerCase()){
					case "mod":
						if (q != null) {
							if (q.getMods() == null) {
								ArrayList<Modifier> temp = new ArrayList<>();
								temp.add(m);
								q.setMods(temp);
								m = null;
							} else {
								q.getMods().add(m);
							}
						}
						mod = false;
						break;
					case "quality":
						qualities.add(q);
						q = null;
						break;
				}

				// Log.i(ChummerConstants.TAG, "END_TAG " + name);
			}

			eventType = parser.next();
		}

		return qualities;
	}

	/**
	 * @param fileLocation
	 *            of the file to parse
	 * @return an Array of PriorityTable data
	 */
	private ArrayList<Quality> readQualitiesXML(final String fileLocation) {
		ArrayList<Quality> quals = new ArrayList<>();
		try {
			XmlPullParserFactory pullParserFactory;

			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			InputStream in_s = getActivity().getApplicationContext().getAssets().open(fileLocation);

			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in_s, null);

			quals = parseXML(parser);

		} catch (XmlPullParserException e) {
			Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
		} catch (IOException e) {
			Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
		}

		return quals;
	}
}