package com.iwan_b.chummersr5.fragments.MainStats;

import android.app.ActionBar.LayoutParams;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.PriorityCounters;
import com.iwan_b.chummersr5.data.Quality;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerXML;

import java.util.ArrayList;

public class AttributeFragment extends Fragment {
	// Whether the max attribute was used
	private boolean maxAttributeUsed = false;
	private View rootView;

	private void updateCounters() {
		TextView attrTextViewCounter = (TextView) rootView.findViewById(R.id.PriorityCounter);
		TextView attrTextViewSpecCounter = (TextView) rootView.findViewById(R.id.attrSpecCounter);

		attrTextViewCounter.setText(FreeCounters.getCounters().getFreeAttributes() + " Attr Counter");
		attrTextViewSpecCounter.setText(FreeCounters.getCounters().getFreeSpecAttributes() + " Spec Counter");

		MainContainer.updateKarma();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.attributefragment, container, false);

		// Set the initial display counters
		FreeCounters.getCounters().setFreeAttributes((int) PriorityCounters.getCounters().getAttr().getStats());
		FreeCounters.getCounters().setFreeSpecAttributes((int) PriorityCounters.getCounters().getMeta().getStats());

		// Display the counters
		updateCounters();

		ArrayList<String> attributes = ChummerXML.readStringXML(getActivity(), "data/AttributesFull.xml");

		TableLayout attrTableLayout = (TableLayout) rootView.findViewById(R.id.attributeTableLayout);

		// Loop through each attribute and create a row for them
		for (final String attrName : attributes) {
			TableRow newTableRow = new TableRow(rootView.getContext());
			newTableRow.setGravity(Gravity.CENTER_VERTICAL);

			TextView titleTxtView = new TextView(rootView.getContext());
			Button subButton = new Button(rootView.getContext());
			TextView attrDisplayTxtView = new TextView(rootView.getContext());
			Button addButton = new Button(rootView.getContext());
			TextView extraInfo = new TextView(rootView.getContext());

			// Default stats
			int baseStat = ChummerConstants.baseStat;
			int maxStat = ChummerConstants.maxStat;
			boolean spec = false;

			// Title of the attribute
			titleTxtView.setText(attrName);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 5, 0);
			titleTxtView.setLayoutParams(lp);
			newTableRow.addView(titleTxtView);

			// Subtract Button
			subButton.setText("-");
			TableRow.LayoutParams lp2 = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			subButton.setLayoutParams(lp2);
			newTableRow.addView(subButton);


			Resources res = getResources();

			switch(attrName.toLowerCase()){
				case "body":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseBody();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxBody();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "agility":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseAgi();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxAgi();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "reaction":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseRea();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxRea();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "strength":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseStr();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxStr();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "will":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseWil();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxWil();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "logic":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseLog();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxLog();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "intuition":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseInt();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxInt();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "charisma":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseCha();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxCha();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					break;
				case "edge":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseEdge();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxEdge();
					attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					spec = true;
					break;
				case "magic":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseMagic();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxMagic();
					if (baseStat > 0) {
						attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					} else {
						// Skip and not add to the table
						continue;
					}
					spec = true;
					break;
				case "technomancer":
					baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseRes();
					maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxRes();
					if (baseStat > 0) {
						attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
					} else {
						// Skip and not add to the table
						continue;
					}
					spec = true;
					break;
			}

			TableRow.LayoutParams lp3 = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp3.setMargins(20, 20, 20, 20);
			attrDisplayTxtView.setLayoutParams(lp3);
			attrDisplayTxtView.setGravity(1);
			attrDisplayTxtView.setMinWidth(50);
			newTableRow.addView(attrDisplayTxtView);

			// Addition Button
			addButton.setText("+");
			TableRow.LayoutParams lp4 = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			addButton.setLayoutParams(lp4);
			newTableRow.addView(addButton);

			// Extra Info
			extraInfo.setText("");
			newTableRow.addView(extraInfo);

			subButton.setOnClickListener(new AttributeOnClickListener(attrName, attrDisplayTxtView, extraInfo, baseStat, maxStat,
					false, spec));
			addButton.setOnClickListener(new AttributeOnClickListener(attrName, attrDisplayTxtView, extraInfo, baseStat, maxStat,
					true, spec));

			attrTableLayout.addView(newTableRow);
		}

		return rootView;
	}

	private class AttributeOnClickListener implements OnClickListener {
		// Which textfield to modify
		private TextView attrDisplayTxtView;
		// Whether to add or subtract
		private boolean isAddition;

		// Limits on the attribute
		private int baseAttr;
		private int maxAttr;

		// How much karma was used
		private int karmaUsed;

		// Whether the attribute is special or not
		private boolean isSpec;

		// Name of the attribute being altered
		private String attrName;

		// Display all the modifiers that affect the attribute
		private TextView extraInfo;

		public AttributeOnClickListener(final String attrName, final TextView attrDisplayTxtView,
				final TextView extraInfo, final int baseAttr, final int maxAttr, final boolean isAddition,
				final boolean isSpec) {
			this.attrName = attrName;
			this.attrDisplayTxtView = attrDisplayTxtView;
			this.isAddition = isAddition;

			this.baseAttr = baseAttr;
			this.maxAttr = maxAttr;

			this.karmaUsed = 0;

			this.isSpec = isSpec;

			this.extraInfo = extraInfo;
		}

		@Override
		public void onClick(View v) {
			// Set the current extraInfo blank
			extraInfo.setText("");

			// Current value of the attribute
			// the format is 1/6 so the first part of the array is the current variable
			String value = attrDisplayTxtView.getText().toString();

			Integer currentRating = Integer.valueOf(value.split("/")[0]);

			// Current value left for attributes
			Integer attrCounter;

			if (isSpec) {
				attrCounter = FreeCounters.getCounters().getFreeAttributes();
			} else {
				attrCounter = FreeCounters.getCounters().getFreeSpecAttributes();
			}

			// Get the karma count for this attribute
			if (attrDisplayTxtView.getTag() != null) {
				karmaUsed = (Integer) attrDisplayTxtView.getTag();
			}

			// Current amount of karma left
			Integer karmaUnused = ShadowrunCharacter.getKarma();

			int max_attr_mod = 0;

			// TODO find a better way to apply modifiers
			if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("max_attr_" + attrName)) {
				for (Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("max_attr_" + attrName)) {
					max_attr_mod += m.getAmount();
				}
			}

			for (Quality q : ShadowrunCharacter.getCharacter().getPositiveQualities()) {
				for (Modifier m : q.getMods()) {
					if (m.getName().equalsIgnoreCase("max_attr_" + attrName)) {
						max_attr_mod += m.getAmount();
						extraInfo.setText(q.getName());
					}
				}
			}

			if (isAddition) {
				// TODO add a karma thing as well as modifiers to this rule so the user can go above the max
				if (currentRating < maxAttr + max_attr_mod) {
					if (isSpec) {
						// Use the attributes first, then karma
						if (attrCounter - 1 >= 0) {
							currentRating++;
							attrCounter--;
						} else {
							// See if they have enough karma to buy the next rating
							if ((currentRating + 1) * 5 <= karmaUnused) {
								// How much karma is spent
								karmaUsed += (currentRating + 1) * 5;
								karmaUnused -= (currentRating + 1) * 5;

								// Set the karma count for the subtraction button to know
								attrDisplayTxtView.setTag(karmaUsed);

								currentRating++;
							}
						}
					} else {

						if (!(maxAttributeUsed && currentRating + 1 == maxAttr + max_attr_mod)) {
							// Use the attributes first, then karma
							if (attrCounter - 1 >= 0) {
								if (karmaUsed > 0) {
									Toast toast = Toast.makeText(getActivity().getApplicationContext(),
											"You already used karma on this. Can't use points afterwards.",
											Toast.LENGTH_SHORT);
									toast.show();
								} else {
									currentRating++;
									attrCounter--;
								}
							} else {
								// See if they have enough karma to buy the next
								// rating
								if ((currentRating + 1) * 5 <= karmaUnused) {
									// How much karma is spent
									karmaUsed += (currentRating + 1) * 5;
									karmaUnused -= (currentRating + 1) * 5;

									// Set the karma count for the subtractionbutton to know
									attrDisplayTxtView.setTag(karmaUsed);
									currentRating++;
								}

							}
						}

						if (currentRating == maxAttr + max_attr_mod) {
							maxAttributeUsed = true;
						}
					}
				}
				// Subtraction
			} else {
				if (currentRating > baseAttr) {
					// We make it false because we are going to remove one now
					if (currentRating == maxAttr + max_attr_mod) {
						maxAttributeUsed = false;
					}

					// Get the karma count for this attribute
					if (attrDisplayTxtView.getTag() != null) {
						karmaUsed = (Integer) attrDisplayTxtView.getTag();
					} else {
						karmaUsed = 0;
					}

					// Test to see if karma was used to increase this attr.
					if (karmaUsed > 0) {
						karmaUsed -= currentRating * 5;
						karmaUnused += currentRating * 5;
						currentRating--;
					} else {
						// No karma was used
						currentRating--;
						attrCounter++;
					}
					attrDisplayTxtView.setTag(karmaUsed);
				}
			}
			// Loop and add the correct amount of asterisks to indicate how many times karma was used
			String karmaUsedDisplay = "";

			if (karmaUsed > 0) {
				int temp = karmaUsed;
				for (int i = currentRating; i > 1; i--) {
					temp -= i * 5;
					karmaUsedDisplay += "*";
					if (temp <= 0) {
						break;
					}

				}
			}

			// Update the displays with all the new stuff.
			attrDisplayTxtView.setText(getResources().getString(R.string.attrText, currentRating.toString(), (maxAttr + max_attr_mod)) + karmaUsedDisplay);

			if (isSpec) {
				FreeCounters.getCounters().setFreeSpecAttributes(attrCounter);
			} else {
				FreeCounters.getCounters().setFreeAttributes(attrCounter);
			}

			ShadowrunCharacter.setKarma(karmaUnused);
			updateCounters();
		}
	}

}