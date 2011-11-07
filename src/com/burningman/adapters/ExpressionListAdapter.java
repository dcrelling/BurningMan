package com.burningman.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.burningman.R;
import com.burningman.beans.Art;
import com.burningman.beans.Camp;
import com.burningman.beans.Event;

public class ExpressionListAdapter extends ArrayAdapter<Parcelable> {

  private ArrayList<Parcelable> items;

  public ExpressionListAdapter(Context context, int textViewResourceId, ArrayList<Parcelable> items) {
    super(context, textViewResourceId, items);
    this.items = items;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View v = convertView;
    if (v == null) {
      LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      v = vi.inflate(R.layout.listrow, null);
    }

    if (items.get(position) instanceof Art) {
      Art art = (Art) items.get(position);
      if (art != null) {
        ImageView artImage = (ImageView) v.findViewById(R.id.ImageView01);
        TextView artNameLbl = (TextView) v.findViewById(R.id.TVExpLstLbl1);
        TextView artDescLbl = (TextView) v.findViewById(R.id.TVExpLstLbl2);
        TextView artName = (TextView) v.findViewById(R.id.TVExpLstDta1);
        TextView artDescription = (TextView) v.findViewById(R.id.TVExpLstDta2);
        if (artImage != null) {
          artImage.setImageResource(R.drawable.art_lst_row);
        }

        if (artNameLbl != null) {
          artNameLbl.setText("Art Name: ");
        }

        if (artDescLbl != null) {
          artDescLbl.setText("Art Description: ");

        }
        if (artName != null) {
          artName.setText(art.getName());
        }

        if (artDescription != null) {
          if (art.getDescription().length() > 100) {
            artDescription.setText(art.getDescription().substring(0, 101) + "...");
          } else {
            artDescription.setText(art.getDescription());
          }
        }

      }
    } else if (items.get(position) instanceof Event) {
      Event event = (Event) items.get(position);
      if (event != null) {

        /*
         * TextView tt = (TextView) v.findViewById(R.id.TextView01); if (tt != null) { tt.setText(event.getTitle()); }
         */
      }
    } else if (items.get(position) instanceof Camp) {
      Camp camp = (Camp) items.get(position);
      if (camp != null) {
        ImageView campImage = (ImageView) v.findViewById(R.id.ImageView01);
        TextView campNameLbl = (TextView) v.findViewById(R.id.TVExpLstLbl1);
        TextView campDescLbl = (TextView) v.findViewById(R.id.TVExpLstLbl2);
        TextView campName = (TextView) v.findViewById(R.id.TVExpLstDta1);
        TextView campDescription = (TextView) v.findViewById(R.id.TVExpLstDta2);
        if (campNameLbl != null) {
          campNameLbl.setText("Camp Name: ");
        }

        if (campDescLbl != null) {
          campDescLbl.setText("Camp Description: ");

        }

        if (campImage != null) {
          campImage.setImageResource(R.drawable.camp_lst_row);
        }
        if (campName != null) {
          campName.setText(camp.getName());
        }
        if (campDescription != null) {
          if (camp.getDescription().length() > 100) {
            campDescription.setText(camp.getDescription().substring(0, 101) + "...");
          } else {
            campDescription.setText(camp.getDescription());
          }
        }
      }
    }
    return v;
  }

}
