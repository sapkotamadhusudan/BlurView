package com.eightbitlab.blurview_sample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.BlurViewFacade;
import eightbitlab.com.blurview.RenderScriptBlur;

public class AddRemoveViewBlurFragment extends BaseFragment {

    private View decorView;
    private ViewGroup rootView;
    private FrameLayout container;

    private Button addBlurViewBtn;

    @Override
    int getLayoutId() {
        return R.layout.fragment_add_remove_blur_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        decorView = requireActivity().getWindow().getDecorView();
        rootView = decorView.findViewById(android.R.id.content);

        container = requireView().findViewById(R.id.blurContainer);

        addBlurViewBtn = view.findViewById(R.id.addBlurViewBtn);

        addBlurViewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (container.getChildCount() > 0) {
                    container.removeAllViews();
                    addBlurViewBtn.setText("Add Blur View");
                } else {
                    //While added BlurView more than once, the issue appears
                    addBlurView();
                    addBlurView();
                    addBlurViewBtn.setText("Remove Blur View");
                }
            }
        });
    }

    void addBlurView() {
        View blurItem = LayoutInflater.from(requireContext()).inflate(R.layout.item_blur_view, container, false);
        container.addView(blurItem);

        final BlurView blurView = blurItem.findViewById(R.id.blurView);

        BlurViewFacade facade = blurView.setupWith(rootView)
                .setFrameClearDrawable(decorView.getBackground())
                .setBlurRadius(4f)
                .setOverlayColor(Color.parseColor("#1AFFFFFF"))
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            facade.setBlurAlgorithm(new RenderScriptBlur(requireContext()));
        } else {
            facade.setBlurAlgorithm(new SupportRenderScriptBlur(requireContext()));
        }
    }
}
