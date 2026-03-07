package com.ded.BTS.beans;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.mime.MediaType;
import org.apache.tika.io.TikaInputStream;

import java.io.IOException;
import java.io.InputStream;

public class FileTypeDetector {

    private static final DefaultDetector detector = new DefaultDetector();

    public static String detectMime(InputStream inputStream) throws IOException {

        Metadata metadata = new Metadata();

        try (TikaInputStream tikaStream = TikaInputStream.get(inputStream)) {
            MediaType mediaType = detector.detect(tikaStream, metadata);
            return mediaType.toString();
        }
    }
}
