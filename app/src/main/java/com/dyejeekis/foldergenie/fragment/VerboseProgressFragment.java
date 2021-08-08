package com.dyejeekis.foldergenie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.databinding.FragmentOperationProgressBinding;

public class VerboseProgressFragment extends OperationProgressFragment {

    private FragmentOperationProgressBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOperationProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewOperationProgress.setMovementMethod(new ScrollingMovementMethod());

        binding.buttonStopOperation.setOnClickListener(v -> {
            stopOperation();
            getActivity().onBackPressed();
        });

        startOperation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void onOperationProgress(String message) {
        binding.textViewOperationProgress.append(message);
    }

    @Override
    protected void onOperationCompleted(boolean success) {
        binding.buttonStopOperation.setText(getString(R.string.action_back));
    }
}