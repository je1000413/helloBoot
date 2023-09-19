package com.mice.backoffice.c2v_mice_backoffice_api.common;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Part;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.util.*;

public class RereadableRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] rawData;
    private final Map<String, String[]> parameterMap;
    private final List<String> inputStreamStrings = new ArrayList<>();
    private final List<MultipartFile> multipartFiles = new ArrayList<>();

    public RereadableRequestWrapper(HttpServletRequest request) throws IOException, ServletException {
        super(request);
        this.parameterMap = new HashMap<>(request.getParameterMap());
        this.rawData = readRawData(request);
        readInputStream(request);
        readMultipartFiles(request);
    }

    private byte[] readRawData(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            contentLength = 4096;
        }
        byte[] buffer = new byte[contentLength];
        int bytesRead;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while ((bytesRead = request.getInputStream().read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            return outputStream.toByteArray();
        }
    }

    private void readInputStream(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawData)));
        String line;
        while ((line = reader.readLine()) != null) {
            inputStreamStrings.add(line);
        }
    }


    private void readMultipartFiles(HttpServletRequest request) throws ServletException, IOException {
        String contentType = request.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            return;
        }
        for (Part part : request.getParts()) {
            if (part instanceof jakarta.servlet.http.Part) {
                jakarta.servlet.http.Part httpPart = (jakarta.servlet.http.Part) part;
                if (httpPart.getSubmittedFileName() != null) {
                    multipartFiles.add(new MultipartFile() {
                        @Override
                        public String getName() {
                            return httpPart.getName();
                        }

                        @Override
                        public String getOriginalFilename() {
                            return httpPart.getSubmittedFileName();
                        }

                        @Override
                        public String getContentType() {
                            return httpPart.getContentType();
                        }

                        @Override
                        public boolean isEmpty() {
                            return httpPart.getSize() == 0;
                        }

                        @Override
                        public long getSize() {
                            return httpPart.getSize();
                        }

                        @Override
                        public byte[] getBytes() throws IOException {
                            return httpPart.getInputStream().readAllBytes();
                        }

                        @Override
                        public InputStream getInputStream() throws IOException {
                            return httpPart.getInputStream();
                        }

                        @Override
                        public void transferTo(File file) throws IOException, IllegalStateException {
                            httpPart.write(file.getAbsolutePath());
                        }
                    });
                }
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawData);

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameterMap.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.parameterMap);
    }

    public List<String> getInputStreamStrings() {
        return inputStreamStrings;
    }

    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }
}