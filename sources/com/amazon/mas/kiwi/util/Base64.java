package com.amazon.mas.kiwi.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _ORDERED_ALPHABET = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] _ORDERED_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, NEW_LINE, -9, -9, -9, EQUALS_SIGN_ENC, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _STANDARD_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] _STANDARD_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, EQUALS_SIGN_ENC, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NEW_LINE, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _URL_SAFE_ALPHABET = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] _URL_SAFE_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, EQUALS_SIGN_ENC, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NEW_LINE, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};

    static {
        boolean z;
        if (!Base64.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = $assertionsDisabled;
        }
        $assertionsDisabled = z;
    }

    private static final byte[] getAlphabet(int options) {
        if ((options & 16) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((options & 32) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    /* access modifiers changed from: private */
    public static final byte[] getDecodabet(int options) {
        if ((options & 16) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((options & 32) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    private Base64() {
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
        return b4;
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options) {
        int i;
        byte[] ALPHABET = getAlphabet(options);
        if (numSigBytes > 0) {
            i = (source[srcOffset] << 24) >>> 8;
        } else {
            i = 0;
        }
        int inBuff = i | (numSigBytes > 1 ? (source[srcOffset + 1] << 24) >>> 16 : 0) | (numSigBytes > 2 ? (source[srcOffset + 2] << 24) >>> 24 : 0);
        switch (numSigBytes) {
            case ENCODE /*1*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                break;
            case GZIP /*2*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = EQUALS_SIGN;
                break;
            case 3:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = ALPHABET[inBuff & 63];
                break;
        }
        return destination;
    }

    public static void encode(ByteBuffer raw, ByteBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[4];
        while (raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, 0, rem);
            encode3to4(enc4, raw3, rem, 0);
            encoded.put(enc4);
        }
    }

    public static void encode(ByteBuffer raw, CharBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[4];
        while (raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, 0, rem);
            encode3to4(enc4, raw3, rem, 0);
            for (int i = 0; i < 4; i++) {
                encoded.put((char) (enc4[i] & EQUALS_SIGN_ENC));
            }
        }
    }

    public static String encodeObject(Serializable serializableObject) throws IOException {
        return encodeObject(serializableObject, 0);
    }

    public static String encodeObject(Serializable serializableObject, int options) throws IOException {
        IOException e;
        GZIPOutputStream gzos;
        if (serializableObject == null) {
            throw new NullPointerException("Cannot serialize a null object.");
        }
        ByteArrayOutputStream baos = null;
        java.io.OutputStream b64os = null;
        GZIPOutputStream gzos2 = null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                java.io.OutputStream b64os2 = new OutputStream(baos2, options | 1);
                if ((options & 2) != 0) {
                    try {
                        gzos = new GZIPOutputStream(b64os2);
                    } catch (IOException e2) {
                        e = e2;
                        b64os = b64os2;
                        baos = baos2;
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        b64os = b64os2;
                        baos = baos2;
                        try {
                            oos.close();
                        } catch (Exception e3) {
                        }
                        try {
                            gzos2.close();
                        } catch (Exception e4) {
                        }
                        try {
                            b64os.close();
                        } catch (Exception e5) {
                        }
                        try {
                            baos.close();
                        } catch (Exception e6) {
                        }
                        throw th;
                    }
                    try {
                        oos = new ObjectOutputStream(gzos);
                        gzos2 = gzos;
                    } catch (IOException e7) {
                        e = e7;
                        gzos2 = gzos;
                        b64os = b64os2;
                        baos = baos2;
                        throw e;
                    } catch (Throwable th3) {
                        th = th3;
                        gzos2 = gzos;
                        b64os = b64os2;
                        baos = baos2;
                        oos.close();
                        gzos2.close();
                        b64os.close();
                        baos.close();
                        throw th;
                    }
                } else {
                    oos = new ObjectOutputStream(b64os2);
                }
                oos.writeObject(serializableObject);
                try {
                    oos.close();
                } catch (Exception e8) {
                }
                try {
                    gzos2.close();
                } catch (Exception e9) {
                }
                try {
                    b64os2.close();
                } catch (Exception e10) {
                }
                try {
                    baos2.close();
                } catch (Exception e11) {
                }
                try {
                    return new String(baos2.toByteArray(), PREFERRED_ENCODING);
                } catch (UnsupportedEncodingException e12) {
                    UnsupportedEncodingException unsupportedEncodingException = e12;
                    return new String(baos2.toByteArray());
                }
            } catch (IOException e13) {
                e = e13;
                baos = baos2;
                throw e;
            } catch (Throwable th4) {
                th = th4;
                baos = baos2;
                oos.close();
                gzos2.close();
                b64os.close();
                baos.close();
                throw th;
            }
        } catch (IOException e14) {
            e = e14;
            throw e;
        }
    }

    public static String encodeBytes(byte[] source) {
        String encoded = null;
        try {
            encoded = encodeBytes(source, 0, source.length, 0);
        } catch (IOException e) {
            IOException ex = e;
            if (!$assertionsDisabled) {
                throw new AssertionError(ex.getMessage());
            }
        }
        if ($assertionsDisabled || encoded != null) {
            return encoded;
        }
        throw new AssertionError();
    }

    public static String encodeBytes(byte[] source, int options) throws IOException {
        return encodeBytes(source, 0, source.length, options);
    }

    public static String encodeBytes(byte[] source, int off, int len) {
        String encoded = null;
        try {
            encoded = encodeBytes(source, off, len, 0);
        } catch (IOException ex) {
            if (!$assertionsDisabled) {
                throw new AssertionError(ex.getMessage());
            }
        }
        if ($assertionsDisabled || encoded != null) {
            return encoded;
        }
        throw new AssertionError();
    }

    public static String encodeBytes(byte[] source, int off, int len, int options) throws IOException {
        byte[] encoded = encodeBytesToBytes(source, off, len, options);
        try {
            return new String(encoded, PREFERRED_ENCODING);
        } catch (UnsupportedEncodingException e) {
            UnsupportedEncodingException unsupportedEncodingException = e;
            return new String(encoded);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] source) {
        try {
            return encodeBytesToBytes(source, 0, source.length, 0);
        } catch (IOException e) {
            IOException ex = e;
            if ($assertionsDisabled) {
                return null;
            }
            throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + ex.getMessage());
        }
    }

    public static byte[] encodeBytesToBytes(byte[] source, int off, int len, int options) throws IOException {
        Throwable th;
        GZIPOutputStream gzos;
        ByteArrayOutputStream baos;
        OutputStream b64os;
        IOException e;
        OutputStream b64os2;
        GZIPOutputStream gzos2;
        if (source == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if (off < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + off);
        } else if (len < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + len);
        } else if (off + len > source.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(off), Integer.valueOf(len), Integer.valueOf(source.length)}));
        } else if ((options & 2) != 0) {
            try {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                try {
                    b64os2 = new OutputStream(baos2, options | 1);
                    try {
                        gzos2 = new GZIPOutputStream(b64os2);
                    } catch (IOException e2) {
                        e = e2;
                        gzos = null;
                        baos = baos2;
                        b64os = b64os2;
                        try {
                            throw e;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        gzos = null;
                        baos = baos2;
                        b64os = b64os2;
                        try {
                            gzos.close();
                        } catch (Exception e3) {
                        }
                        try {
                            b64os.close();
                        } catch (Exception e4) {
                        }
                        try {
                            baos.close();
                        } catch (Exception e5) {
                        }
                        throw th;
                    }
                } catch (IOException e6) {
                    e = e6;
                    gzos = null;
                    baos = baos2;
                    b64os = null;
                    throw e;
                } catch (Throwable th4) {
                    th = th4;
                    gzos = null;
                    baos = baos2;
                    b64os = null;
                    gzos.close();
                    b64os.close();
                    baos.close();
                    throw th;
                }
                try {
                    gzos2.write(source, off, len);
                    gzos2.close();
                    try {
                        gzos2.close();
                    } catch (Exception e7) {
                    }
                    try {
                        b64os2.close();
                    } catch (Exception e8) {
                    }
                    try {
                        baos2.close();
                    } catch (Exception e9) {
                    }
                    return baos2.toByteArray();
                } catch (IOException e10) {
                    e = e10;
                    gzos = gzos2;
                    baos = baos2;
                    b64os = b64os2;
                    throw e;
                } catch (Throwable th5) {
                    gzos = gzos2;
                    baos = baos2;
                    th = th5;
                    b64os = b64os2;
                    gzos.close();
                    b64os.close();
                    baos.close();
                    throw th;
                }
            } catch (IOException e11) {
                e = e11;
                gzos = null;
                baos = null;
                b64os = null;
                throw e;
            } catch (Throwable th6) {
                th = th6;
                gzos = null;
                baos = null;
                b64os = null;
                gzos.close();
                b64os.close();
                baos.close();
                throw th;
            }
        } else {
            boolean breakLines = (options & 8) != 0;
            int encLen = ((len / 3) * 4) + (len % 3 > 0 ? 4 : 0);
            if (breakLines) {
                encLen += encLen / MAX_LINE_LENGTH;
            }
            byte[] outBuff = new byte[encLen];
            int e12 = 0;
            int len2 = len - 2;
            int lineLength = 0;
            int d = 0;
            while (d < len2) {
                encode3to4(source, d + off, 3, outBuff, e12, options);
                int lineLength2 = lineLength + 4;
                if (breakLines && lineLength2 >= MAX_LINE_LENGTH) {
                    outBuff[e12 + 4] = NEW_LINE;
                    e12++;
                    lineLength2 = 0;
                }
                e12 += 4;
                lineLength = lineLength2;
                d += 3;
            }
            if (d < len) {
                encode3to4(source, d + off, len - d, outBuff, e12, options);
                e12 += 4;
            }
            if (e12 > outBuff.length - 1) {
                return outBuff;
            }
            byte[] finalOut = new byte[e12];
            System.arraycopy(outBuff, 0, finalOut, 0, e12);
            return finalOut;
        }
    }

    /* access modifiers changed from: private */
    public static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options) {
        if (source == null) {
            throw new NullPointerException("Source array was null.");
        } else if (destination == null) {
            throw new NullPointerException("Destination array was null.");
        } else if (srcOffset < 0 || srcOffset + 3 >= source.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[]{Integer.valueOf(source.length), Integer.valueOf(srcOffset)}));
        } else if (destOffset < 0 || destOffset + 2 >= destination.length) {
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[]{Integer.valueOf(destination.length), Integer.valueOf(destOffset)}));
        } else {
            byte[] DECODABET = getDecodabet(options);
            if (source[srcOffset + 2] == 61) {
                destination[destOffset] = (byte) ((((DECODABET[source[srcOffset]] & EQUALS_SIGN_ENC) << 18) | ((DECODABET[source[srcOffset + 1]] & EQUALS_SIGN_ENC) << 12)) >>> 16);
                return 1;
            } else if (source[srcOffset + 3] == 61) {
                int outBuff = ((DECODABET[source[srcOffset]] & EQUALS_SIGN_ENC) << 18) | ((DECODABET[source[srcOffset + 1]] & EQUALS_SIGN_ENC) << 12) | ((DECODABET[source[srcOffset + 2]] & EQUALS_SIGN_ENC) << 6);
                destination[destOffset] = (byte) (outBuff >>> 16);
                destination[destOffset + 1] = (byte) (outBuff >>> 8);
                return 2;
            } else {
                int outBuff2 = ((DECODABET[source[srcOffset]] & EQUALS_SIGN_ENC) << 18) | ((DECODABET[source[srcOffset + 1]] & EQUALS_SIGN_ENC) << 12) | ((DECODABET[source[srcOffset + 2]] & EQUALS_SIGN_ENC) << 6) | (DECODABET[source[srcOffset + 3]] & 255);
                destination[destOffset] = (byte) (outBuff2 >> 16);
                destination[destOffset + 1] = (byte) (outBuff2 >> 8);
                destination[destOffset + 2] = (byte) outBuff2;
                return 3;
            }
        }
    }

    public static byte[] decode(byte[] source) throws IOException {
        return decode(source, 0, source.length, 0);
    }

    public static byte[] decode(byte[] source, int off, int len, int options) throws IOException {
        int off2;
        int b4Posn;
        if (source == null) {
            throw new NullPointerException("Cannot decode null source array.");
        } else if (off < 0 || off + len > source.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[]{Integer.valueOf(source.length), Integer.valueOf(off), Integer.valueOf(len)}));
        } else if (len == 0) {
            return new byte[0];
        } else {
            if (len < 4) {
                throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + len);
            }
            byte[] DECODABET = getDecodabet(options);
            byte[] outBuff = new byte[((len * 3) / 4)];
            byte[] b4 = new byte[4];
            int outBuffPosn = 0;
            int i = off;
            int i2 = 0;
            int b4Posn2 = 0;
            while (true) {
                if (i >= off + len) {
                    int len2 = b4Posn2;
                    int i3 = i2;
                    off2 = outBuffPosn;
                    break;
                }
                byte sbiDecode = DECODABET[source[i] & EQUALS_SIGN_ENC];
                if (sbiDecode >= -5) {
                    if (sbiDecode >= -1) {
                        b4Posn = i2 + 1;
                        b4[i2] = source[i];
                        if (b4Posn > 3) {
                            int outBuffPosn2 = outBuffPosn + decode4to3(b4, 0, outBuff, outBuffPosn, options);
                            b4Posn = 0;
                            if (source[i] == 61) {
                                int len3 = sbiDecode;
                                off2 = outBuffPosn2;
                                break;
                            }
                            outBuffPosn = outBuffPosn2;
                        } else {
                            continue;
                        }
                    } else {
                        b4Posn = i2;
                    }
                    i++;
                    i2 = b4Posn;
                    b4Posn2 = sbiDecode;
                } else {
                    throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[]{Integer.valueOf(source[i] & EQUALS_SIGN_ENC), Integer.valueOf(i)}));
                }
            }
            byte[] source2 = new byte[off2];
            System.arraycopy(outBuff, 0, source2, 0, off2);
            return source2;
        }
    }

    public static byte[] decode(String s) throws IOException {
        return decode(s, 0);
    }

    public static byte[] decode(String s, int options) throws IOException {
        byte[] bytes;
        GZIPInputStream gzis;
        int length;
        IOException iOException;
        GZIPInputStream gzis2;
        if (s == null) {
            throw new NullPointerException("Input string was null.");
        }
        try {
            bytes = s.getBytes(PREFERRED_ENCODING);
        } catch (UnsupportedEncodingException e) {
            bytes = s.getBytes();
        }
        byte[] bytes2 = decode(bytes, 0, bytes.length, options);
        boolean dontGunzip = (options & 4) != 0 ? true : $assertionsDisabled;
        if (bytes2 == null || bytes2.length < 4 || dontGunzip || 35615 != ((bytes2[0] & 255) | ((bytes2[1] << 8) & 65280))) {
            return bytes2;
        }
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        byte[] buffer = new byte[2048];
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes2);
                try {
                    GZIPInputStream gzis3 = new GZIPInputStream(bais2);
                    int length2 = 0;
                    while (true) {
                        try {
                            length2 = gzis3.read(buffer);
                            if (length2 < 0) {
                                break;
                            }
                            baos2.write(buffer, 0, length2);
                        } catch (IOException e2) {
                            length = length2;
                            bais = bais2;
                            baos = baos2;
                            e = e2;
                            gzis = gzis3;
                        } catch (Throwable th) {
                            iOException = th;
                            int i = length2;
                            bais = bais2;
                            baos = baos2;
                            gzis2 = gzis3;
                            try {
                                baos.close();
                            } catch (Exception e3) {
                            }
                            try {
                                gzis2.close();
                            } catch (Exception e4) {
                            }
                            try {
                                bais.close();
                            } catch (Exception e5) {
                            }
                            throw iOException;
                        }
                    }
                    byte[] bytes3 = baos2.toByteArray();
                    try {
                        baos2.close();
                    } catch (Exception e6) {
                    }
                    try {
                        gzis3.close();
                    } catch (Exception e7) {
                    }
                    try {
                        bais2.close();
                        return bytes3;
                    } catch (Exception e8) {
                        return bytes3;
                    }
                } catch (IOException e9) {
                    gzis = null;
                    length = 0;
                    ByteArrayOutputStream baos3 = baos2;
                    e = e9;
                    bais = bais2;
                    baos = baos3;
                    try {
                        e.printStackTrace();
                        try {
                            baos.close();
                        } catch (Exception e10) {
                        }
                        try {
                            gzis.close();
                        } catch (Exception e11) {
                        }
                        try {
                            bais.close();
                            return bytes2;
                        } catch (Exception e12) {
                            return bytes2;
                        }
                    } catch (Throwable e13) {
                        iOException = e13;
                        gzis2 = gzis;
                        int i2 = length;
                        baos.close();
                        gzis2.close();
                        bais.close();
                        throw iOException;
                    }
                } catch (Throwable th2) {
                    iOException = th2;
                    bais = bais2;
                    baos = baos2;
                    gzis2 = null;
                    baos.close();
                    gzis2.close();
                    bais.close();
                    throw iOException;
                }
            } catch (IOException e14) {
                gzis = null;
                length = 0;
                ByteArrayOutputStream baos4 = baos2;
                e = e14;
                baos = baos4;
                e.printStackTrace();
                baos.close();
                gzis.close();
                bais.close();
                return bytes2;
            } catch (Throwable th3) {
                iOException = th3;
                baos = baos2;
                gzis2 = null;
                baos.close();
                gzis2.close();
                bais.close();
                throw iOException;
            }
        } catch (IOException e15) {
            e = e15;
            gzis = null;
            length = 0;
            e.printStackTrace();
            baos.close();
            gzis.close();
            bais.close();
            return bytes2;
        } catch (Throwable e16) {
            iOException = e16;
            gzis2 = null;
            baos.close();
            gzis2.close();
            bais.close();
            throw iOException;
        }
    }

    public static void encodeToFile(byte[] dataToEncode, String filename) throws IOException {
        IOException e;
        if (dataToEncode == null) {
            throw new NullPointerException("Data to encode was null.");
        }
        OutputStream bos = null;
        try {
            OutputStream bos2 = new OutputStream(new FileOutputStream(filename), 1);
            try {
                bos2.write(dataToEncode);
                try {
                    bos2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                bos = bos2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                bos = bos2;
                try {
                    bos.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static void decodeToFile(String dataToDecode, String filename) throws IOException {
        IOException e;
        OutputStream bos = null;
        try {
            OutputStream bos2 = new OutputStream(new FileOutputStream(filename), 0);
            try {
                bos2.write(dataToDecode.getBytes(PREFERRED_ENCODING));
                try {
                    bos2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                bos = bos2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                bos = bos2;
                try {
                    bos.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static byte[] decodeFromFile(String filename) throws IOException {
        IOException e;
        InputStream bis = null;
        try {
            File file = new File(filename);
            int length = 0;
            if (file.length() > 2147483647L) {
                throw new IOException("File is too big for this convenience method (" + file.length() + " bytes).");
            }
            byte[] buffer = new byte[((int) file.length())];
            InputStream bis2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
            while (true) {
                try {
                    int numBytes = bis2.read(buffer, length, 4096);
                    if (numBytes < 0) {
                        break;
                    }
                    length += numBytes;
                } catch (IOException e2) {
                    e = e2;
                    bis = bis2;
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bis = bis2;
                    try {
                        bis.close();
                    } catch (Exception e3) {
                    }
                    throw th;
                }
            }
            byte[] decodedData = new byte[length];
            System.arraycopy(buffer, 0, decodedData, 0, length);
            try {
                bis2.close();
            } catch (Exception e4) {
            }
            return decodedData;
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static String encodeFromFile(String filename) throws IOException {
        IOException e;
        InputStream bis = null;
        try {
            File file = new File(filename);
            byte[] buffer = new byte[Math.max((int) ((((double) file.length()) * 1.4d) + 1.0d), 40)];
            int length = 0;
            InputStream bis2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
            while (true) {
                try {
                    int numBytes = bis2.read(buffer, length, 4096);
                    if (numBytes < 0) {
                        break;
                    }
                    length += numBytes;
                } catch (IOException e2) {
                    e = e2;
                    bis = bis2;
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bis = bis2;
                    try {
                        bis.close();
                    } catch (Exception e3) {
                    }
                    throw th;
                }
            }
            String encodedData = new String(buffer, 0, length, PREFERRED_ENCODING);
            try {
                bis2.close();
            } catch (Exception e4) {
            }
            return encodedData;
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static void encodeFileToFile(String infile, String outfile) throws IOException {
        IOException e;
        String encoded = encodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            java.io.OutputStream out2 = new BufferedOutputStream(new FileOutputStream(outfile));
            try {
                out2.write(encoded.getBytes(PREFERRED_ENCODING));
                try {
                    out2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                out = out2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                out = out2;
                try {
                    out.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static void decodeFileToFile(String infile, String outfile) throws IOException {
        IOException e;
        byte[] decoded = decodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            java.io.OutputStream out2 = new BufferedOutputStream(new FileOutputStream(outfile));
            try {
                out2.write(decoded);
                try {
                    out2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                out = out2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                out = out2;
                try {
                    out.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static class InputStream extends FilterInputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream in) {
            this(in, 0);
        }

        public InputStream(java.io.InputStream in, int options2) {
            super(in);
            boolean z;
            this.options = options2;
            this.breakLines = (options2 & 8) > 0;
            if ((options2 & 1) > 0) {
                z = true;
            } else {
                z = false;
            }
            this.encode = z;
            this.bufferLength = this.encode ? 4 : 3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
            this.decodabet = Base64.getDecodabet(options2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:27:0x0067 A[LOOP:1: B:18:0x0042->B:27:0x0067, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0058 A[EDGE_INSN: B:48:0x0058->B:25:0x0058 ?: BREAK  , SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int read() throws java.io.IOException {
            /*
                r11 = this;
                r4 = 3
                r10 = 4
                r9 = -1
                r1 = 0
                int r3 = r11.position
                if (r3 >= 0) goto L_0x0030
                boolean r3 = r11.encode
                if (r3 == 0) goto L_0x003e
                byte[] r0 = new byte[r4]
                r2 = 0
                r8 = 0
            L_0x0010:
                if (r8 >= r4) goto L_0x0022
                java.io.InputStream r3 = r11.in
                int r6 = r3.read()
                if (r6 < 0) goto L_0x0022
                byte r3 = (byte) r6
                r0[r8] = r3
                int r2 = r2 + 1
                int r8 = r8 + 1
                goto L_0x0010
            L_0x0022:
                if (r2 <= 0) goto L_0x003c
                byte[] r3 = r11.buffer
                int r5 = r11.options
                r4 = r1
                byte[] unused = com.amazon.mas.kiwi.util.Base64.encode3to4(r0, r1, r2, r3, r4, r5)
                r11.position = r1
                r11.numSigBytes = r10
            L_0x0030:
                int r3 = r11.position
                if (r3 < 0) goto L_0x00a7
                int r3 = r11.position
                int r4 = r11.numSigBytes
                if (r3 < r4) goto L_0x0079
                r1 = r9
            L_0x003b:
                return r1
            L_0x003c:
                r1 = r9
                goto L_0x003b
            L_0x003e:
                byte[] r7 = new byte[r10]
                r8 = 0
                r8 = 0
            L_0x0042:
                if (r8 >= r10) goto L_0x0058
                r6 = 0
            L_0x0045:
                java.io.InputStream r3 = r11.in
                int r6 = r3.read()
                if (r6 < 0) goto L_0x0056
                byte[] r3 = r11.decodabet
                r4 = r6 & 127(0x7f, float:1.78E-43)
                byte r3 = r3[r4]
                r4 = -5
                if (r3 <= r4) goto L_0x0045
            L_0x0056:
                if (r6 >= 0) goto L_0x0067
            L_0x0058:
                if (r8 != r10) goto L_0x006d
                byte[] r3 = r11.buffer
                int r4 = r11.options
                int r3 = com.amazon.mas.kiwi.util.Base64.decode4to3(r7, r1, r3, r1, r4)
                r11.numSigBytes = r3
                r11.position = r1
                goto L_0x0030
            L_0x0067:
                byte r3 = (byte) r6
                r7[r8] = r3
                int r8 = r8 + 1
                goto L_0x0042
            L_0x006d:
                if (r8 != 0) goto L_0x0071
                r1 = r9
                goto L_0x003b
            L_0x0071:
                java.io.IOException r1 = new java.io.IOException
                java.lang.String r3 = "Improperly padded Base64 input."
                r1.<init>(r3)
                throw r1
            L_0x0079:
                boolean r3 = r11.encode
                if (r3 == 0) goto L_0x008c
                boolean r3 = r11.breakLines
                if (r3 == 0) goto L_0x008c
                int r3 = r11.lineLength
                r4 = 76
                if (r3 < r4) goto L_0x008c
                r11.lineLength = r1
                r1 = 10
                goto L_0x003b
            L_0x008c:
                int r1 = r11.lineLength
                int r1 = r1 + 1
                r11.lineLength = r1
                byte[] r1 = r11.buffer
                int r3 = r11.position
                int r4 = r3 + 1
                r11.position = r4
                byte r6 = r1[r3]
                int r1 = r11.position
                int r3 = r11.bufferLength
                if (r1 < r3) goto L_0x00a4
                r11.position = r9
            L_0x00a4:
                r1 = r6 & 255(0xff, float:3.57E-43)
                goto L_0x003b
            L_0x00a7:
                java.io.IOException r1 = new java.io.IOException
                java.lang.String r3 = "Error in Base64 code reading stream."
                r1.<init>(r3)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amazon.mas.kiwi.util.Base64.InputStream.read():int");
        }

        public int read(byte[] dest, int off, int len) throws IOException {
            int i = 0;
            while (true) {
                if (i >= len) {
                    break;
                }
                int b = read();
                if (b >= 0) {
                    dest[off + i] = (byte) b;
                    i++;
                } else if (i == 0) {
                    return -1;
                }
            }
            return i;
        }
    }

    public static class OutputStream extends FilterOutputStream {

        /* renamed from: b4 */
        private byte[] f1170b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream out) {
            this(out, 1);
        }

        public OutputStream(java.io.OutputStream out, int options2) {
            super(out);
            boolean z;
            int i;
            this.breakLines = (options2 & 8) != 0;
            if ((options2 & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            this.encode = z;
            if (this.encode) {
                i = 3;
            } else {
                i = 4;
            }
            this.bufferLength = i;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = Base64.$assertionsDisabled;
            this.f1170b4 = new byte[4];
            this.options = options2;
            this.decodabet = Base64.getDecodabet(options2);
        }

        public void write(int theByte) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theByte);
            } else if (this.encode) {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64.encode3to4(this.f1170b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= Base64.MAX_LINE_LENGTH) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                }
            } else if (this.decodabet[theByte & 127] > -5) {
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(this.f1170b4, 0, Base64.decode4to3(this.buffer, 0, this.f1170b4, 0, this.options));
                    this.position = 0;
                }
            } else if (this.decodabet[theByte & 127] != -5) {
                throw new IOException("Invalid character in Base64 data.");
            }
        }

        public void write(byte[] theBytes, int off, int len) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theBytes, off, len);
                return;
            }
            for (int i = 0; i < len; i++) {
                write(theBytes[off + i]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.position <= 0) {
                return;
            }
            if (this.encode) {
                this.out.write(Base64.encode3to4(this.f1170b4, this.buffer, this.position, this.options));
                this.position = 0;
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void close() throws IOException {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = Base64.$assertionsDisabled;
        }
    }
}
