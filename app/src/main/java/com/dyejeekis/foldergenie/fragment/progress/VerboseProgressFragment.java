package com.dyejeekis.foldergenie.fragment.progress;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.databinding.FragmentProgressVerboseBinding;

public class VerboseProgressFragment extends OperationProgressFragment {

    public static final String KEY_PROGRESS_STRING = "key.PROGRESS_STRING";

    private FragmentProgressVerboseBinding binding;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PROGRESS_STRING, binding.textViewOperationProgress.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProgressVerboseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewOperationProgress.setMovementMethod(new ScrollingMovementMethod());
        if (savedInstanceState != null) {
            String progressString = savedInstanceState.getString(KEY_PROGRESS_STRING);
            if (progressString != null) binding.textViewOperationProgress.setText(progressString);
        }

        binding.buttonStopOperation.setOnClickListener(v -> {
            stopOperation();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void onOperationProgress(int progressMax, int progressCurrent, String message) {
        if (message != null) {
            String s = message;
            if (progressMax > 0 && progressCurrent > 0)
                s = "\n" + progressCurrent + "/" + progressMax + s;
            binding.textViewOperationProgress.append(s);
        }
    }

    @Override
    protected void onOperationCompleted(boolean success) {
        binding.buttonStopOperation.setText(getString(R.string.action_back));
        binding.buttonStopOperation.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }
}