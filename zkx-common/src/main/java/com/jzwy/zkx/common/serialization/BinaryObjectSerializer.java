package com.jzwy.zkx.common.serialization;

import com.jzwy.zkx.core.exception.Guard;

import java.io.*;

/**
 * 二进制Byte对象序列化器
 */
public class BinaryObjectSerializer implements ObjectSerializer {

    private static BinaryObjectSerializer binaryObjectSerializer;

    public static BinaryObjectSerializer create() {
        if (binaryObjectSerializer == null) {
            binaryObjectSerializer = new BinaryObjectSerializer();
        }
        return binaryObjectSerializer;
    }

    @Override
    public <T> byte[] serialize(T object) {
        Guard.notNull(object, "object");
        byte[] output;
        ByteArrayOutputStream byteOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            output = byteOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("无法执行二进制序列化操作", e);
        } finally {
            this.close(objectOutputStream);
            this.close(byteOutputStream);
        }
        return output;
    }

    @Override
    public <T> T deserialize(byte[] input, Class<T> clazz) {
        T objectValue = null;
        ByteArrayInputStream byteOutputStream = null;
        ObjectInputStream objectOutputStream = null;
        try {
            if (input != null) {
                byteOutputStream = new ByteArrayInputStream(input);
                objectOutputStream = new ObjectInputStream(byteOutputStream);
                objectValue = (T) objectOutputStream.readObject();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("执行二进制反序列化操作失败", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("执行二进制反序列化操作失败", e);
        } finally {
            this.close(objectOutputStream);
            this.close(byteOutputStream);
        }
        return objectValue;
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
