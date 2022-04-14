package com.banvien.fc.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class GzCompress {

    public static void main(String[] args) {
        String data = "eNrtkE1OwzAQha9SeR2l/qGtyqrQIhagqBJLxGLaWI1FbFdjexEh7s5Mmkosyg3wyv7evJnxe/8Sbzmi3cbWinvRyMtRorrwBjzz8T5juo3+DGH4bVJsUFppkh8d5q6FgbBRc6XnWmo52krIOEyONZfuuxhsU/zBIiFpqIk2d4vlirQnD64nmnjM5sSv+hg9KQ9tizYl0l4A4di5araHT5cyBB5TyOEtPmMsZ6oJpe/5K9Db5CFM4680Qy7USVXiFbLLhUUja7NaGEIxnK5wua5pM6rb0SB0h0JrTb12Y1rf1e0c9c0c9X+Of+X48QMCE8dL";
        System.out.println(uncompress(data));
    }

    public static String uncompress(String data) {
        return new String(uncompress(Base64.getDecoder().decode(data)));
    }

    public static String compress(String data) {
        return new String(Base64.getEncoder().encode(compress(data.getBytes())));
    }

    public static byte[] uncompress(byte[] data) {

        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decompresser.end();
        return output;
    }

    public static byte[] compress(byte[] bytes) {

        byte[] output = new byte[1024];
        Deflater compresser = new Deflater();
        compresser.setInput(bytes);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output);
        return Arrays.copyOf(output, compressedDataLength);
    }
}
