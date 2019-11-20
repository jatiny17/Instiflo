package com.rohg007.android.instiflo.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohg007.android.instiflo.R;
import com.rohg007.android.instiflo.models.Issue;
import com.squareup.picasso.Picasso;

public class IssueDetails extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button resolveButton;
    Button approveButton;
    Button declineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);

        databaseReference = FirebaseDatabase.getInstance().getReference("issues");

        final LinearLayout contactLayout = findViewById(R.id.owner_contact_details);
        contactLayout.setVisibility(View.GONE);

        int callingActivity = getIntent().getIntExtra("calling-activity",0);

        final Issue issue = (Issue) getIntent().getExtras().get("ISSUE");
        TextView issueTitleTv = findViewById(R.id.issue_title);
        String issueTitle = issue.getLostFound()+" "+issue.getmObjectType();
        issueTitleTv.setText(issueTitle);

        TextView issueOwnerTv = findViewById(R.id.issue_owner);
        String issueOwner = "Posted By: "+issue.getmName();
        issueOwnerTv.setText(issueOwner);

        TextView datePostedTv = findViewById(R.id.issue_date);
        String issueDate = "Posted on: "+issue.getDatePosted();
        datePostedTv.setText(issueDate);

        TextView descriptionTv = findViewById(R.id.issue_description);
        descriptionTv.setText(issue.getmDescription());

        resolveButton = findViewById(R.id.resolve_button);
        approveButton = findViewById(R.id.approve_issue_button);
        declineButton = findViewById(R.id.decline_issue_button);
        LinearLayout approveDeclineLayout = findViewById(R.id.approve_decline_layout);

        if(callingActivity==1001||callingActivity==1002)
            resolveButton.setVisibility(View.VISIBLE);
        else
            approveDeclineLayout.setVisibility(View.VISIBLE);

        resolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveIssue(issue);
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveIssue(issue);
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineIssue(issue);
            }
        });

        ImageView issueImage = findViewById(R.id.expanded_image_issue);
        Picasso.get().load(issue.getmImageUrl()).into(issueImage);

        FloatingActionButton floatingActionButton = findViewById(R.id.share_issue);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareInfo = "Hey! I just ";
                shareInfo+= issue.getLostFound();
                shareInfo+=" a "+issue.getmObjectType();
                if(issue.getLostFound().equals("Lost"))
                    shareInfo+=". Please help me find it!";
                else
                    shareInfo+=". To whoever it belongs may contact me to grab it from me!";
                shareInfo+=System.lineSeparator();
                shareInfo+="You may check the image here: ";
                shareInfo+=issue.getmImageUrl();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareInfo);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        final Button contactDetailsButton = findViewById(R.id.contact_details_button);
        contactDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactLayout.getVisibility()==View.GONE){
                    contactDetailsButton.setText("Hide Contact Details");
                    contactLayout.setVisibility(View.VISIBLE);
                    TextView emailView= findViewById(R.id.issue_mail);
                    TextView phoneview= findViewById(R.id.issue_contact);
                    emailView.setText(issue.getmEmail());
                    phoneview.setText(issue.getmContactNumber());
                    emailView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderMail(issue);
                        }
                    });
                    phoneview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            makeCall(issue);
                        }
                    });
                } else {
                    contactLayout.setVisibility(View.GONE);
                    contactDetailsButton.setText("Contact Details");
                }
            }
        });

        ImageButton mail = findViewById(R.id.issue_mail_btn);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderMail(issue);
            }
        });

        ImageButton phone = findViewById(R.id.issue_call_btn);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(issue);
            }
        });

        ImageButton chat = findViewById(R.id.issue_chat_btn);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doChat(issue.getmContactNumber());
            }
        });
    }

    private void resolveIssue(final Issue issue){
        final AlertDialog.Builder builder = new AlertDialog.Builder(IssueDetails.this);
        builder
                .setTitle("Mark as Resolved?")
                .setMessage("Once resolved, Issue won't list on the app.\nTo list the issue again on the app, contact the administrator.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child(issue.getmId()).child("mResolved").setValue("YES");
                        databaseReference.child(issue.getmId()).child("mVisibililty").setValue("NO");
                        resolveButton.setFocusable(false);
                        resolveButton.setClickable(false);
                        Snackbar.make(resolveButton,"Congratulations! Your issue is resolved",Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void approveIssue(Issue issue){
        databaseReference.child(issue.getmId()).child("mApproved").setValue("YES");
        databaseReference.child(issue.getmId()).child("mVisibililty").setValue("YES");
        Intent intent = new Intent(IssueDetails.this,ApproveIssue.class);
        startActivity(intent);
        finish();
    }

    private void declineIssue(Issue issue){
        databaseReference.child(issue.getmId()).child("mApproved").setValue("YES");
        Intent intent = new Intent(IssueDetails.this,ApproveIssue.class);
        startActivity(intent);
        finish();
    }

    private void orderMail(Issue issue) {
        String body = "";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        if (issue.getLostFound().equals("Lost")) {
            body = "Sir,\n Please forward this to all UG & PG Groups.\n\nI've lost my " + issue.getmObjectType() + ". It is a " + issue.getmDescription() + "\n\n Anyone who finds it may contact the undersigned\n\n" + issue.getmName() + "\n" + issue.getmContactNumber();
        } else if (issue.getLostFound().equals("Found")) {
            body = "Sir,\n Please forward this to all UG & PG Groups.\n\nI've Found a " + issue.getmObjectType() + ". It is a " + issue.getmDescription() + "\n\n To collect it you may contact the undersigned\n\n" + issue.getmName() + "\n" + issue.getmContactNumber();
        }
        //Setting the subject of mail
        intent.putExtra(Intent.EXTRA_SUBJECT, issue.getmObjectType() + " " + issue.getLostFound());
        //Setting the body of mail
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    void makeCall(Issue issue)
    {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:91"+issue.getmContactNumber()));
        startActivity(i);
    }

    void doChat(String ownerContact)
    {
        try {
            ownerContact = ownerContact.replace(" ", "").replace("+", "");
            ownerContact="91"+ownerContact;
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(ownerContact)+"@s.whatsapp.net");
            // getApplication().startActivity(sendIntent);

            startActivity(Intent.createChooser(sendIntent, "Compartir en")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        } catch(Exception e) {
            Log.e("WS", "ERROR_OPEN_MESSANGER"+e.toString());
        }
    }
}
