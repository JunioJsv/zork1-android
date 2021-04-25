package com.zaxsoft.zax.zmachine;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class ZFileLoader {
    /**
     * Returns a {@link ZStory} containing the z-machine story loaded from
     * the file specified by storyFilePath.
     *
     * @param storyFilePath an absolute URL giving the base location of the image
     * @return {@link ZStory} the z-machine story
     * @throws RuntimeException with a cause of NoSuchFileException when file is not found
     * @throws RuntimeException when any other IOException occurs.
     * @see ZStory
     */
    ZStory load(String storyFilePath) {
        try {
            File story = new File(storyFilePath);
            byte[] bytes = new byte[(int) story.length()];
            int result = new FileInputStream(story).read(bytes, 0, bytes.length);
            if (result <= 0)
                throw new RuntimeException(String.format("could not read the file %s", storyFilePath));
            return new ZStory(bytes);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    ZStory loadFromAssets(String storyAssetName, Context context) {
        try {
            InputStream story = context.getAssets().open(storyAssetName);
            ByteArrayOutputStream _bytes = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];

            while (true) {
                int length = story.read(buffer);

                if (story.available() > 0) {
                    _bytes.write(buffer, 0, length);
                } else {
                    break;
                }
            }

            byte[] bytes = _bytes.toByteArray();

            return new ZStory(bytes);

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
