package com.noqoush.adfalcon.adgallery;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.noqoush.adfalcon.adgallery.adapter.SiteIDsAdapter;
import com.noqoush.adfalcon.adgallery.helper.SiteIDHelper;
import com.noqoush.adfalcon.adgallery.model.SiteID;

public class ManageSiteIDsActivity extends Activity implements OnClickListener,
        OnItemClickListener, OnItemLongClickListener, TextWatcher {

    private static final int SITE_ID_LENGTH = 32;
    EditText mNameET, mSiteIdET;
    CheckBox mTestingCB;
    Button mSaveBtn, mCancelBtn;
    ListView mSitesLV;
    SiteIDHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_site_ids);
        if (getActionBar() != null)
        	getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameET = (EditText) findViewById(R.id.editTextName);
        mSiteIdET = (EditText) findViewById(R.id.editTextSiteID);
        mTestingCB = (CheckBox) findViewById(R.id.checkBoxTesting);
        mSaveBtn = (Button) findViewById(R.id.buttonSave);
        mCancelBtn = (Button) findViewById(R.id.buttonCancel);
        mSitesLV = (ListView) findViewById(R.id.listViewSiteIDs);

        mHelper = new SiteIDHelper(this);
        setListAdapter();

        mSaveBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mSitesLV.setOnItemClickListener(this);
        mSitesLV.setOnItemLongClickListener(this);

        mSaveBtn.setEnabled(false);
        mCancelBtn.setEnabled(false);

        mNameET.addTextChangedListener(this);
        mSiteIdET.addTextChangedListener(this);
    }

    private void setListAdapter() {
        SiteIDsAdapter adapter = new SiteIDsAdapter(this, mHelper);
        mSitesLV.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mCancelBtn) {
            clearData();
        } else {
            String name = mNameET.getText().toString();
            String id = mSiteIdET.getText().toString();
            if (name.length() < 4) {
                Toast.makeText(this, getString(R.string.invalid_name),
                        Toast.LENGTH_SHORT).show();
                return;

            }

            if (id.length() != SITE_ID_LENGTH) {
                Toast.makeText(this, getString(R.string.invalid_site_id),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            SiteID siteID = new SiteID();
            siteID.setName(name);
            siteID.setSiteID(id);
            siteID.setTesting(mTestingCB.isChecked());

            if (mHelper.saveSiteID(siteID)) {
                Toast.makeText(this,
                        getString(R.string.site_id_saved_sccuessfully),
                        Toast.LENGTH_SHORT).show();
                clearData();
                setListAdapter();
            } else {
                Toast.makeText(this, getString(R.string.site_id_fail_to_save),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearData() {
        mNameET.setText("");
        mSiteIdET.setText("");
        mTestingCB.setChecked(false);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        SiteID id = (SiteID) arg0.getItemAtPosition(position);
        if (!id.isDefaultSiteID()) {
            mNameET.setText(id.getName());
            mSiteIdET.setText(id.getSiteID());
            mTestingCB.setChecked(id.isTesting());
        } else {
            Toast.makeText(this,
                    getString(R.string.can_not_edit_default_site_id),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
        final SiteID id = (SiteID) arg0.getItemAtPosition(position);
        if (!id.isDefaultSiteID()) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            if (mHelper.removeSiteID(id)) {
                                Toast.makeText(
                                        ManageSiteIDsActivity.this,
                                        getString(R.string.site_id_saved_sccuessfully),
                                        Toast.LENGTH_SHORT).show();
                                clearData();
                                setListAdapter();
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.site_id_remove_msg))
                    .setPositiveButton(getString(R.string.yes),
                            dialogClickListener)
                    .setNegativeButton(getString(R.string.no),
                            dialogClickListener).show();

        } else {
            Toast.makeText(this,
                    getString(R.string.can_not_edit_default_site_id),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mSaveBtn.setEnabled(mNameET.length() >= 4 && mSiteIdET.length() == SITE_ID_LENGTH);
        mCancelBtn.setEnabled(mNameET.length() >= 4 && mSiteIdET.length() == SITE_ID_LENGTH);
    }
}
