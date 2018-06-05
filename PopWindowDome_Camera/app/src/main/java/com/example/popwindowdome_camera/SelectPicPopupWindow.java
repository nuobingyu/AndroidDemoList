package com.example.popwindowdome_camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

class SelectPicPopupWindow extends PopupWindow {

    private View mMenuView;

    @SuppressLint("InflateParams")
    SelectPicPopupWindow(final Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mMenuView = inflater.inflate(R.layout.popwindow, null  );

        Button btn_take_photo = mMenuView.findViewById(R.id.btn_take_photo);
        Button btn_pick_photo = mMenuView.findViewById(R.id.btn_from_xc);
        Button btn_cancel = mMenuView.findViewById(R.id.btn_cancel);

        init();

        btn_cancel.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了cancel按钮", Toast.LENGTH_SHORT).show( );
                dismiss( );
            }
        });
        btn_take_photo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了拍照按钮", Toast.LENGTH_SHORT).show( );
            }
        });
        btn_pick_photo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点击了从相册选择", Toast.LENGTH_SHORT).show( );
            }
        });
    }

    private void init(){
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0x50000000));
        setAnimationStyle(R.style.PopupAnimation );
        setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mMenuView.setOnTouchListener(new View.OnTouchListener( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction( ) == MotionEvent.ACTION_UP
                        && event.getY( ) < mMenuView.findViewById(R.id.rl_popwindow_camerapic).getTop( )) {
                    dismiss( );
                }
                return true;
            }
        });
        setContentView(mMenuView);
    }
}
