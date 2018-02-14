package com.erick.tarea2;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.erick.tarea2.databinding.ActivityMainBinding;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = ActivityMain.class.getSimpleName() + " Debug";
    private static String SELECTED_CART = "selected_cart";
    private static String SELECTED_SIZES = "selected_sizes";
    private static String SELECTED_COLOR = "selected_color";

    private ActivityMainBinding mBinding;
    private Toast mToast;
    private boolean mSelectedAddCart;
    private boolean[] mSelectedSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setListeners();
        if (savedInstanceState != null) {
            mSelectedAddCart = savedInstanceState.getBoolean(SELECTED_CART);
            mSelectedSize = savedInstanceState.getBooleanArray(SELECTED_SIZES);
            int selectedId = savedInstanceState.getInt(SELECTED_COLOR);
            RadioButton radioButton = mBinding.rdColors.findViewById(selectedId);
            radioButton.toggle();
            if (mSelectedAddCart) {
                mBinding.btnAddCart.setSelected(true);
                mBinding.btnAddCart.setText(getString(R.string.activity_main_button_selected_text));
            }
            setSelectedSize();
            return;
        }
        mSelectedAddCart = false;
        mSelectedSize = new boolean[]{false, false, false, false};
        mBinding.rbColor2.toggle();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.ib_like:
                if (mToast != null) {
                    if (!mToast.getView().isShown())
                        mToast.show();
                } else {
                    mToast = Toast.makeText(this, getString(R.string.activity_main_toast_like), Toast.LENGTH_SHORT);
                    mToast.show();
                }
                break;
            case R.id.btn_add_cart:
                if (mSelectedAddCart) {
                    mToast = Toast.makeText(this, getString(R.string.activity_main_item_already_added), Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    mSelectedAddCart = true;
                    mBinding.btnAddCart.setSelected(true);
                    mBinding.btnAddCart.setText(getString(R.string.activity_main_button_selected_text));
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator), getString(R.string.activity_main_snackbar_confirmation), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.activity_main_snackbar_undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSelectedAddCart = false;
                            mBinding.btnAddCart.setSelected(false);
                            mBinding.btnAddCart.setText(getText(R.string.activity_main_button_text));
                        }
                    });
                    snackbar.show();
                }
                break;
            case R.id.btn_size1:
                if (mSelectedSize[0])
                    mSelectedSize = new boolean[]{false, false, false, false};
                else
                    mSelectedSize = new boolean[]{true, false, false, false};
                setSelectedSize();
                break;
            case R.id.btn_size2:
                if (mSelectedSize[1])
                    mSelectedSize = new boolean[]{false, false, false, false};
                else
                    mSelectedSize = new boolean[]{false, true, false, false};
                setSelectedSize();
                break;
            case R.id.btn_size3:
                if (mSelectedSize[2])
                    mSelectedSize = new boolean[]{false, false, false, false};
                else
                    mSelectedSize = new boolean[]{false, false, true, false};
                setSelectedSize();
                break;
            case R.id.btn_size4:
                if (mSelectedSize[3])
                    mSelectedSize = new boolean[]{false, false, false, false};
                else
                    mSelectedSize = new boolean[]{false, false, false, true};
                setSelectedSize();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putBoolean(SELECTED_CART, mSelectedAddCart);
        outState.putBooleanArray(SELECTED_SIZES, mSelectedSize);
        outState.putInt(SELECTED_COLOR, mBinding.rdColors.getCheckedRadioButtonId());
    }

    private void setListeners() {
        Log.d(TAG, "setClickListeners");
        mBinding.ibLike.setOnClickListener(this);
        mBinding.btnAddCart.setOnClickListener(this);
        mBinding.btnSize1.setOnClickListener(this);
        mBinding.btnSize2.setOnClickListener(this);
        mBinding.btnSize3.setOnClickListener(this);
        mBinding.btnSize4.setOnClickListener(this);
    }

    private void setSelectedSize() {
        Log.d(TAG, "setSelectedSize");
        mBinding.btnSize1.setSelected(mSelectedSize[0]);
        mBinding.btnSize2.setSelected(mSelectedSize[1]);
        mBinding.btnSize3.setSelected(mSelectedSize[2]);
        mBinding.btnSize4.setSelected(mSelectedSize[3]);
    }
}
