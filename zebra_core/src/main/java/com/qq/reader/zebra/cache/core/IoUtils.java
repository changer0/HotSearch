package com.qq.reader.zebra.cache.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;

public final class IoUtils {
    /** {@value} */
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 KB
    /** {@value} */
    public static final int CONTINUE_LOADING_PERCENTAGE = 75;
    private IoUtils() {
    }

    /**
     * Closes 'closeable', ignoring any checked exceptions. Does nothing if 'closeable' is null.
     */
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Closes 'socket', ignoring any exceptions. Does nothing if 'socket' is null.
     */
    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception ignored) {
            }
        }
    }


    /**
     * Recursively delete everything in {@code dir}.
     */
    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("listFiles returned null: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }


    public static void throwInterruptedIoException() throws InterruptedIOException {
        // This is typically thrown in response to an
        // InterruptedException which does not leave the thread in an
        // interrupted state, so explicitly interrupt here.
        Thread.currentThread().interrupt();
        throw new InterruptedIOException();
    }

    /**
     * Copies stream, fires progress events by listener, can be interrupted by listener. Uses buffer size =
     * {@value #DEFAULT_BUFFER_SIZE} bytes.
     *
     * @param is       Input stream
     * @param os       Output stream
     * @param listener null-ok; Listener of copying progress and controller of copying interrupting
     * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
     * @throws IOException
     */
    public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener) throws IOException {
        return copyStream(is, os, listener, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copies stream, fires progress events by listener, can be interrupted by listener.
     *
     * @param is         Input stream
     * @param os         Output stream
     * @param listener   null-ok; Listener of copying progress and controller of copying interrupting
     * @param bufferSize Buffer size for copying, also represents a step for firing progress listener callback, i.e.
     *                   progress event will be fired after every copied <b>bufferSize</b> bytes
     * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
     * @throws IOException
     */
    public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener, int bufferSize)
            throws IOException {
        int current = 0;
        final int total = is.available();

        final byte[] bytes = new byte[bufferSize];
        int count;
        if (shouldStopLoading(listener, current, total)) return false;
        while ((count = is.read(bytes, 0, bufferSize)) != -1) {
            os.write(bytes, 0, count);
            current += count;
            if (shouldStopLoading(listener, current, total)) return false;
        }
        os.flush();
        return true;
    }

    private static boolean shouldStopLoading(CopyListener listener, int current, int total) {
        if (listener != null) {
            boolean shouldContinue = listener.onBytesCopied(current, total);
            if (!shouldContinue) {
                if (100 * current / total < CONTINUE_LOADING_PERCENTAGE) {
                    return true; // if loaded more than 75% then continue loading anyway
                }
            }
        }
        return false;
    }

    /**
     * Reads all data from stream and close it silently
     *
     * @param is Input stream
     */
    public static void readAndCloseStream(InputStream is) {
        final byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
        try {
            while (is.read(bytes, 0, DEFAULT_BUFFER_SIZE) != -1) {
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Do nothing
        } finally {
            closeQuietly(is);
        }
    }

    /** Listener and controller for copy process */
    public static interface CopyListener {
        /**
         * @param current Loaded bytes
         * @param total   Total bytes for loading
         * @return <b>true</b> - if copying should be continued; <b>false</b> - if copying should be interrupted
         */
        boolean onBytesCopied(int current, int total);
    }

    /**
     * Stream -> String
     * @param is
     * @return
     * @throws IOException
     */
    public static String getString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return sb.toString();
    }
}