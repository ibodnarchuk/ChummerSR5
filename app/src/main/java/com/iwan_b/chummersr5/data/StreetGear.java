package com.iwan_b.chummersr5.data;

import android.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.utility.ChummerMethods;

public class StreetGear extends GeneralInfo {
    private String type;

    private int avail = 0;
    private boolean is_avail_rating = false;
    private int avail_mult_by = 1;
    private int avail_add_by = 0;
    private String avail_type;

    private int cost = 0;
    private boolean is_cost_rating_mult = false;

    private int rating = 0;
    private int minRating = 0;
    private int maxRating = 0;

    public StreetGear() {
    }

    public StreetGear(StreetGear copy) {
        super(copy);
        this.type = copy.type;
        this.avail = copy.avail;
        this.is_avail_rating = copy.is_avail_rating;
        this.avail_mult_by = copy.avail_mult_by;
        this.avail_add_by = copy.avail_add_by;
        this.avail_type = copy.avail_type;
        this.cost = copy.cost;
        this.is_cost_rating_mult = copy.is_cost_rating_mult;
        this.rating = copy.rating;
        this.minRating = copy.minRating;
        this.maxRating = copy.maxRating;

    }

    public View displayView(View view) {
        super.displayView(view);
        final TextView typeTxtView = (TextView) view.findViewById(R.id.type_textview);

        final TextView availTitleTxtView = (TextView) view.findViewById(R.id.avail_title_textview);
        final TextView availTxtView = (TextView) view.findViewById(R.id.avail_textview);

        final TextView costTitleTxtView = (TextView) view.findViewById(R.id.cost_title_textview);
        final EditText costEditTxtView = (EditText) view.findViewById(R.id.cost_textview);

        if (typeTxtView != null) {
            typeTxtView.setText(type);
        }

        if (availTxtView != null) {
//            private boolean is_avail_rating = false;
//            private int avail_mult_by = 1;
//            private int avail_add_by = 0;
            availTxtView.setText(String.valueOf(avail) + avail_type);
        }

        if (costEditTxtView != null) {
            // private boolean is_cost_rating_mult = false;
            costEditTxtView.setText(String.valueOf(cost));

            costEditTxtView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    String temp = s.toString();
                    if (temp != null && !temp.isEmpty()) {
                        try {
                            cost = Integer.valueOf(s.toString());
                        } catch (NumberFormatException e) {
                            cost = Integer.MAX_VALUE;
                        }
                    } else {
                        cost = 0;
                    }
                }
            });
        }

        if (is_avail_rating || maxRating > 0) {
            final LinearLayout layout = (LinearLayout) view.findViewById(R.id.rating_linearview);

            if (layout != null) {
                final TextView labelTxtView = ChummerMethods.genTxtView(view.getContext(), "Rating: ");
                final Button subButton = ChummerMethods.genButton(view.getContext(), "-");
                final TextView ratingTxtView = ChummerMethods.genTxtView(view.getContext(), String.valueOf(rating));
                final Button addButton = ChummerMethods.genButton(view.getContext(), "+");

                TableRow.LayoutParams labelRatingLayoutParams = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                labelRatingLayoutParams.setMargins(0, 0, 5, 0);
                labelTxtView.setLayoutParams(labelRatingLayoutParams);

                TableRow.LayoutParams ratingLayoutParams = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                ratingLayoutParams.setMargins(20, 0, 20, 0);
                ratingTxtView.setLayoutParams(ratingLayoutParams);
                ratingTxtView.setGravity(1);
                ratingTxtView.setMinWidth(50);

                subButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rating - 1 >= minRating) {
                            rating--;
                        }

                        ratingTxtView.setText(String.valueOf(rating));
                    }
                });

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rating + 1 <= maxRating) {
                            rating++;
                        }

                        ratingTxtView.setText(String.valueOf(rating));
                    }
                });

                if (rating > 1) {
                    subButton.setEnabled(false);
                    addButton.setEnabled(false);
                }

                layout.addView(labelTxtView);
                layout.addView(subButton);
                layout.addView(ratingTxtView);
                layout.addView(addButton);
            }
        }


        return view;
    }

    public boolean parseXML(final String switchId, final String data) {
        // Attempt to parse the parent first
        if (super.parseXML(switchId, data)) {
            return true;
        }

        switch (switchId.toLowerCase()) {
            case "type":
                this.setType(data);
                break;
            case "avail":
                if (!data.isEmpty()) {
                    try {
                        this.setAvail(Integer.valueOf(data));
                    } catch (NumberFormatException e) {
//                        Log.i(ChummerConstants.TAG, "Number Format Exception at avail for: " + this.toString());
                    }
                }
                break;
            case "is_avail_rating":
                try {
                    this.setIs_avail_rating(Boolean.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at is_avail_rating for: " + this.toString());
                }
                break;
            case "avail_mult_by":
                try {
                    this.setAvail_mult_by(Integer.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at avail_mult_by for: " + this.toString());
                } catch (NumberFormatException e) {
//                    Log.i(ChummerConstants.TAG, "Number Format Exception at avail_mult_by for: " + this.toString());
                }
                break;
            case "avail_add_by":
                try {
                    this.setAvail_add_by(Integer.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at avail_add_by for: " + this.toString());
                } catch (NumberFormatException e) {
//                    Log.i(ChummerConstants.TAG, "Number Format Exception at avail_add_by for: " + this.toString());
                }
                break;
            case "avail_type":
                this.setAvail_type(data);
                break;
            case "is_cost_rating_mult":
                try {
                    this.setIs_cost_rating_mult(Boolean.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at ap_divide_by for: " + this.toString());
                }
                break;
            case "cost":
                try {
                    this.setCost(Integer.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at cost for: " + this.toString());
                } catch (NumberFormatException e) {
//                    Log.i(ChummerConstants.TAG, "Number Format Exception at cost for: " + this.toString());
                }
                break;
            case "rating":
                try {
                    this.setRating(Integer.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at rating for: " + this.toString());
                } catch (NumberFormatException e) {
//                    Log.i(ChummerConstants.TAG, "Number Format Exception at rating for: " + this.toString());
                }
                break;
            case "min_rating":
                try {
                    this.setMinRating(Integer.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at min_rating for: " + this.toString());
                } catch (NumberFormatException e) {
//                    Log.i(ChummerConstants.TAG, "Number Format Exception at min_rating for: " + this.toString());
                }
                break;
            case "max_rating":
                try {
                    this.setMaxRating(Integer.valueOf(data));
                } catch (NullPointerException e) {
//                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at max_rating for: " + this.toString());
                } catch (NumberFormatException e) {
//                    Log.i(ChummerConstants.TAG, "Number Format Exception at max_rating for: " + this.toString());
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreetGear)) return false;
        if (!super.equals(o)) return false;

        StreetGear that = (StreetGear) o;

        if (getAvail() != that.getAvail()) return false;
        if (is_avail_rating != that.is_avail_rating) return false;
        if (getAvail_mult_by() != that.getAvail_mult_by()) return false;
        if (getAvail_add_by() != that.getAvail_add_by()) return false;
        if (getCost() != that.getCost()) return false;
        if (is_cost_rating_mult != that.is_cost_rating_mult) return false;
        if (getRating() != that.getRating()) return false;
        if (getMinRating() != that.getMinRating()) return false;
        if (getMaxRating() != that.getMaxRating()) return false;
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null)
            return false;
        return !(getAvail_type() != null ? !getAvail_type().equals(that.getAvail_type()) : that.getAvail_type() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + getAvail();
        result = 31 * result + (is_avail_rating ? 1 : 0);
        result = 31 * result + getAvail_mult_by();
        result = 31 * result + getAvail_add_by();
        result = 31 * result + (getAvail_type() != null ? getAvail_type().hashCode() : 0);
        result = 31 * result + getCost();
        result = 31 * result + (is_cost_rating_mult ? 1 : 0);
        result = 31 * result + getRating();
        result = 31 * result + getMinRating();
        result = 31 * result + getMaxRating();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "StreetGear{" +
                "type='" + type + '\'' +
                ", avail=" + avail +
                ", is_avail_rating=" + is_avail_rating +
                ", avail_mult_by=" + avail_mult_by +
                ", avail_add_by=" + avail_add_by +
                ", avail_type='" + avail_type + '\'' +
                ", cost=" + cost +
                ", is_cost_rating_mult=" + is_cost_rating_mult +
                ", rating=" + rating +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                "} ";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public boolean is_avail_rating() {
        return is_avail_rating;
    }

    public void setIs_avail_rating(boolean is_avail_rating) {
        this.is_avail_rating = is_avail_rating;
    }

    public int getAvail_mult_by() {
        return avail_mult_by;
    }

    public void setAvail_mult_by(int avail_mult_by) {
        this.avail_mult_by = avail_mult_by;
    }

    public int getAvail_add_by() {
        return avail_add_by;
    }

    public void setAvail_add_by(int avail_add_by) {
        this.avail_add_by = avail_add_by;
    }

    public String getAvail_type() {
        return avail_type;
    }

    public void setAvail_type(String avail_type) {
        this.avail_type = avail_type;
    }

    public int getCost() {

        if (is_cost_rating_mult && rating > 0) {
            return cost * rating;
        }

        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean is_cost_rating_mult() {
        return is_cost_rating_mult;
    }

    public void setIs_cost_rating_mult(boolean is_cost_rating_mult) {
        this.is_cost_rating_mult = is_cost_rating_mult;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }
}