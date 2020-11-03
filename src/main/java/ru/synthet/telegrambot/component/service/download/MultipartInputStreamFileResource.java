package ru.synthet.telegrambot.component.service.download;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class MultipartInputStreamFileResource extends InputStreamResource implements Closeable {

    private final InputStream inputStream;
    private final String fileName;
    private final Long fileSize;

    public MultipartInputStreamFileResource(InputStream inputStream, String fileName, Long fileSize) {
        super(inputStream);
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    public String getFilename() {
        return this.fileName;
    }

    @Override
    public long contentLength() throws IOException {
        return fileSize;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(inputStream);
    }
}
