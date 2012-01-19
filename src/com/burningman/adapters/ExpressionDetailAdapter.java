package com.burningman.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.burningman.R;
import com.burningman.beans.Art;
import com.burningman.beans.Camp;
import com.burningman.beans.Event;

public class ExpressionDetailAdapter {

  private Parcelable expression;
  private Context context;

  public ExpressionDetailAdapter(Context ctx, Parcelable exp) {
    this.expression = exp;
    this.context = ctx;

  }

  public View mapToDetailView() {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = null;
    v = inflater.inflate(R.layout.expressiondetail, null);
    if (expression instanceof Art) {
      Art artItem = (Art) expression;
      TextView nameLbl = (TextView) v.findViewById(R.id.EDTextViewLbl1);
      nameLbl.setText("Art Name: ");
      TextView name = (TextView) v.findViewById(R.id.EDTextViewDta1);
      name.setText(artItem.getName());

      TextView descriptionLbl = (TextView) v.findViewById(R.id.EDTextViewLbl2);
      descriptionLbl.setText("Art Description: ");
      TextView description = (TextView) v.findViewById(R.id.EDTextViewDta2);
      description.setText(artItem.getDescription());

      TextView artistLbl = (TextView) v.findViewById(R.id.EDTextViewLbl3);
      artistLbl.setText("Artist: ");
      TextView artist = (TextView) v.findViewById(R.id.EDTextViewDta3);
      artist.setText(artItem.getArtist());

      TextView contactEmailLbl = (TextView) v.findViewById(R.id.EDTextViewLbl4);
      contactEmailLbl.setText("Contact Email: ");
      TextView contactEmail = (TextView) v.findViewById(R.id.EDTextViewDta4);
      contactEmail.setText(artItem.getContact_email());
      Linkify.addLinks(contactEmail, Linkify.ALL);

      TextView URLLbl = (TextView) v.findViewById(R.id.EDTextViewLbl5);
      URLLbl.setText("URL: ");
      TextView URL = (TextView) v.findViewById(R.id.EDTextViewDta5);
      URL.setText(artItem.getUrl());
      Linkify.addLinks(URL, Linkify.ALL);
    } else if (expression instanceof Camp) {

      Camp campItem = (Camp) expression;
      TextView nameLbl = (TextView) v.findViewById(R.id.EDTextViewLbl1);
      nameLbl.setText("Camp Name: ");
      TextView name = (TextView) v.findViewById(R.id.EDTextViewDta1);
      name.setText(campItem.getName());

      TextView descriptionLbl = (TextView) v.findViewById(R.id.EDTextViewLbl2);
      descriptionLbl.setText("Camp Description: ");
      TextView description = (TextView) v.findViewById(R.id.EDTextViewDta2);
      description.setText(campItem.getDescription());

      TextView contactEmailLbl = (TextView) v.findViewById(R.id.EDTextViewLbl3);
      contactEmailLbl.setText("Contact Email: ");
      TextView contactEmail = (TextView) v.findViewById(R.id.EDTextViewDta3);
      contactEmail.setText(campItem.getContact_email());
      Linkify.addLinks(contactEmail, Linkify.ALL);

      TextView URLLbl = (TextView) v.findViewById(R.id.EDTextViewLbl4);
      URLLbl.setText("Camp URL: ");
      TextView URL = (TextView) v.findViewById(R.id.EDTextViewDta4);
      URL.setText(campItem.getUrl());
      Linkify.addLinks(URL, Linkify.ALL);

      v.findViewById(R.id.EDTextViewLbl5).setVisibility(View.INVISIBLE);
      v.findViewById(R.id.EDTextViewDta5).setVisibility(View.INVISIBLE);
      
    } else if (expression instanceof Event) {
      Event eventItem = (Event) expression;
      TextView nameLbl = (TextView) v.findViewById(R.id.EDTextViewLbl1);
      nameLbl.setText("Event Name: ");
      TextView name = (TextView) v.findViewById(R.id.EDTextViewDta1);
      name.setText(eventItem.getTitle());

      TextView descriptionLbl = (TextView) v.findViewById(R.id.EDTextViewLbl2);
      descriptionLbl.setText("Event Description: ");
      TextView description = (TextView) v.findViewById(R.id.EDTextViewDta2);
      description.setText(eventItem.getDescription());

      TextView contactEmailLbl = (TextView) v.findViewById(R.id.EDTextViewLbl3);
      contactEmailLbl.setText("Contact Email: ");
      TextView contactEmail = (TextView) v.findViewById(R.id.EDTextViewDta3);
      contactEmail.setText(eventItem.getContact_email());
      Linkify.addLinks(contactEmail, Linkify.ALL);

      TextView URLLbl = (TextView) v.findViewById(R.id.EDTextViewLbl4);
      URLLbl.setText("Event URL: ");
      TextView URL = (TextView) v.findViewById(R.id.EDTextViewDta4);
      URL.setText(eventItem.getUrl());
      Linkify.addLinks(URL, Linkify.ALL);

      v.findViewById(R.id.EDTextViewLbl5).setVisibility(View.INVISIBLE);
      v.findViewById(R.id.EDTextViewDta5).setVisibility(View.INVISIBLE);
    }
    return v;

  }

}
