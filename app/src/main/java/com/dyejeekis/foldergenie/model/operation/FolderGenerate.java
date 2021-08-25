package com.dyejeekis.foldergenie.model.operation;

import android.content.Context;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.filegroup.FileGroupAudio;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupDocument;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupImage;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupVideo;
import com.dyejeekis.foldergenie.model.AlphanumRange;
import com.dyejeekis.foldergenie.model.DateRange;
import com.dyejeekis.foldergenie.service.GenieService;
import com.dyejeekis.foldergenie.util.GeneralUtil;
import com.dyejeekis.foldergenie.model.SizeRange;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FolderGenerate extends FolderOperation {

    public static final String TAG = FolderGenerate.class.getSimpleName();

    public static final int DEFAULT_FILE_COUNT = 500;
    public static final SizeRange DEFAULT_SIZE_RANGE = null;
    public static final AlphanumRange DEFAULT_ALPHANUM_RANGE = null;
    public static final DateRange DEFAULT_DATE_RANGE = null;
    public static final List<String> DEFAULT_EXTENSIONS = getCompleteExtensionsList();

    public static List<String> getCompleteExtensionsList() {
        List<String> extensions = new ArrayList<>();
        extensions.addAll(Arrays.asList(FileGroupImage.IMAGE_EXTENSIONS));
        extensions.addAll(Arrays.asList(FileGroupVideo.VIDEO_EXTENSIONS));
        extensions.addAll(Arrays.asList(FileGroupAudio.AUDIO_EXTENSIONS));
        extensions.addAll(Arrays.asList(FileGroupDocument.DOCUMENT_EXTENSIONS));
        return extensions;
    }

    private final int fileCount;
    private final SizeRange sizeRange; // TODO: 8/3/2021 size should have an upper limit
    private final AlphanumRange alphanumRange;
    private final DateRange dateRange;
    private final List<String> extensions;

    public FolderGenerate(File rootDir) {
        super(rootDir);
        this.fileCount = DEFAULT_FILE_COUNT;
        this.sizeRange = DEFAULT_SIZE_RANGE;
        this.alphanumRange = DEFAULT_ALPHANUM_RANGE;
        this.dateRange = DEFAULT_DATE_RANGE;
        this.extensions = DEFAULT_EXTENSIONS;
    }

    public FolderGenerate(Builder builder) {
        super(builder.rootDir);
        this.fileCount = builder.fileCount;
        this.sizeRange = builder.sizeRange;
        this.alphanumRange = builder.alphanumRange;
        this.dateRange = builder.dateRange;
        this.extensions = builder.extensions;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean startOperation(Context context, ResultReceiver resultReceiver, Handler handler) {
        String message = "Folder generate options:\n" + this.toString();
        Log.d(TAG, message);
        onOperationProgress(resultReceiver, message);

        try {
            for (int i = 0; i < fileCount; i++) {
                if (GenieService.folderOperationStopped()) {
                    message = "Folder generate operation cancelled";
                    Log.d(TAG, message);
                    onOperationProgress(resultReceiver, message);
                    return false;
                }

                String filename = getRandomName() + "." + getRandomExtension();
                File file = new File(rootDir.getAbsolutePath() + File.separator + filename);
                boolean success = file.createNewFile();

                if (success) {
                    file.setLastModified(getRandomTimestamp());
                    // TODO: 8/4/2021 other file properties
                    message = "Generated file " + file.getAbsolutePath();
                } else {
                    message = "Failed to create file " + file.getAbsolutePath();
                }

                Log.d(TAG, message);
                onOperationProgress(resultReceiver, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Failed to generate test files in " + rootDir.getAbsolutePath();
            if (resultReceiver == null) onOperationProgress(context, handler, message);
            message = message.concat("\n\n" + e.toString());
            Log.d(TAG, message);
            onOperationProgress(resultReceiver, message);
            return false;
        }
        message = fileCount + " test files generated successfully in " + rootDir.getAbsolutePath();
        Log.d(TAG, message);
        onOperationProgress(resultReceiver, message);
        if (resultReceiver == null) onOperationProgress(context, handler, message);
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: 8/3/2021
        return super.toString();
    }

    private String getRandomName() {
        // TODO: 8/3/2021
        return UUID.randomUUID().toString();
    }

    private String getRandomExtension() {
        Random random = new Random();
        return extensions.get(random.nextInt(extensions.size()));
    }

    private long getRandomTimestamp() {
        // TODO: 8/3/2021
        return GeneralUtil.getRandomTimestamp();
    }

    private long getRandomByteSize() {
        // TODO: 8/3/2021
        return 0;
    }

    public static class Builder {

        private File rootDir;
        private int fileCount;
        private SizeRange sizeRange;
        private AlphanumRange alphanumRange;
        private DateRange dateRange;
        private List<String> extensions;

        public Builder(File rootDir) {
            this.rootDir = rootDir;
            this.fileCount = DEFAULT_FILE_COUNT;
            this.sizeRange = DEFAULT_SIZE_RANGE;
            this.alphanumRange = DEFAULT_ALPHANUM_RANGE;
            this.dateRange = DEFAULT_DATE_RANGE;
            this.extensions = DEFAULT_EXTENSIONS;
        }

        public Builder rootDir(File rootDir) {
            this.rootDir = rootDir;
            return this;
        }

        public Builder fileCount(int fileCount) {
            this.fileCount = fileCount;
            return this;
        }

        public Builder sizeRange(SizeRange sizeRange) {
            this.sizeRange = sizeRange;
            return this;
        }

        public Builder alphanumRange(AlphanumRange alphanumRange) {
            this.alphanumRange = alphanumRange;
            return this;
        }

        public Builder dateRange(DateRange dateRange) {
            this.dateRange = dateRange;
            return this;
        }

        public Builder setExtensions(List<String> extensions) {
            this.extensions = extensions;
            return this;
        }

        public Builder addExtensions(List<String> extensions) {
            this.extensions.addAll(extensions);
            return this;
        }

        public Builder addExtension(String extension) {
            this.extensions.add(extension);
            return this;
        }

        public FolderGenerate build() {
            FolderGenerate folderGenerate = new FolderGenerate(this);
            validateFolderGen(folderGenerate);
            return folderGenerate;
        }

        private void validateFolderGen(FolderGenerate folderGenerate) {
            // TODO: 8/3/2021
        }
    }
}
