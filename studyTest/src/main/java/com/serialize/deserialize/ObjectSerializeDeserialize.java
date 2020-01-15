package com.serialize.deserialize;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectSerializeDeserialize {

    public static void main(String[] args) {
        HBaseMutationRecord record=new HBaseMutationRecord(Mutation.DELETE,"key".getBytes(),"cf","qualifier_test",1000);
        try {
            System.out.println(record.toBytes().length);
            System.out.println(bytesToLong(record.fromBytes(record.toBytes()).value));
            System.out.println(new String(record.fromBytes(record.toBytes()).qualifier));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static class HBaseMutationRecord {

        private static final Logger LOG = LoggerFactory.getLogger(HBaseMutationRecord.class);
        private Mutation mutation;
        private byte[] key;
        private byte[] cf;
        private byte[] qualifier;
        private byte[] value;

        public HBaseMutationRecord() {}

        public HBaseMutationRecord(Mutation mutation, byte[] key, byte[] cf, byte[] qualifier, byte[] value) {
            this.mutation = mutation;
            this.key = key;
            this.cf = cf;
            this.qualifier = qualifier;
            this.value = value;
        }

        public HBaseMutationRecord(Mutation mutation,byte[] key,String cf,String qualifier, long value) {
            this.mutation = mutation;
            this.key = key;
            this.cf = cf.getBytes();
            this.qualifier = qualifier.getBytes();
            this.value = Longs.toByteArray(value);
        }

        public static byte[] toBytes(HBaseMutationRecord record) throws IOException {
            return record.toBytes();
        }



        /**
         * 将当前对象序列化
         * @return 字节数据
         * @throws IOException 序列化失败
         *
         * total+
         */
        public byte[] toBytes() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            //计算对象字节总长度
            short total = Short.BYTES;//存放total 需要2个字节长度
            total+=Mutation.SIZE;//存放Mutation.SIZE
            total+=(1+key.length);//存放key的长度（数字1个字节）+key本身
            total+=(1+cf.length);
            total+=(1+qualifier.length);
            total+=(1+value.length);

            dos.writeShort(total);
            dos.writeByte(mutation.getType());
            dos.writeByte(key.length);
            dos.write(key);
            dos.writeByte(cf.length);
            dos.write(cf);
            dos.writeByte(qualifier.length);
            dos.write(qualifier);
            dos.writeByte(value.length);
            dos.write(value);
            dos.flush();
            dos.close();
            return bos.toByteArray();
        }

        /**
         * 将给定的byte数组反序列化为 #{@link HBaseMutationRecord} 对象
         * @param bytes 需要反序列化的数组
         * @return 反序列化之后的 #{@link HBaseMutationRecord} 对象
         * @throws IOException 反序列化失败
         */
        public  HBaseMutationRecord fromBytes(byte[] bytes) throws IOException {
            HBaseMutationRecord record = new HBaseMutationRecord();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dataInputStream = new DataInputStream(bis);
            Short total = dataInputStream.readShort();
            if (bytes.length != total) {
                dataInputStream.close();
                throw new IOException("wrong HBaseMutationRecord format!");
            }
            byte mutationType = dataInputStream.readByte();
            Mutation mutation = Mutation.fromType(mutationType);


            byte[] key = readField(dataInputStream);
            byte[] cf = readField(dataInputStream);
            byte[] qualifier = readField(dataInputStream);
            byte[] value = readField(dataInputStream);

            record.setMutation(mutation);
            record.setKey(key);
            record.setCf(cf);
            record.setQualifier(qualifier);
            record.setValue(value);
            dataInputStream.close();
            return record;
        }

        /**
         * 将给定的byte数组反序列化为 #{@link HBaseMutationRecord} 对象列表
         * @param bytes
         * @return 对象列表
         * @throws Exception 如果反序列化失败, 则抛出异常
         */
        public static List<HBaseMutationRecord> deserializeRecords(byte[] bytes) throws Exception {
            List<HBaseMutationRecord> records = new LinkedList<>();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dataInputStream = new DataInputStream(bis);
            try {
                int total = dataInputStream.readInt();
                if (total != bytes.length) {
                    throw new IOException("wrong HBaseMutationRecord stream format!");
                }
                total -= Integer.BYTES;
                while (total > 0) {
                    HBaseMutationRecord record = new HBaseMutationRecord();
                    AtomicInteger size = new AtomicInteger(dataInputStream.readShort());
                    total -= size.get();
                    size.addAndGet(-2);

                    byte mutation = dataInputStream.readByte();
                    record.setMutation(Mutation.fromType(mutation));
                    size.decrementAndGet();

                    byte[] row = readField(dataInputStream, size);
                    byte[] cf = readField(dataInputStream, size);
                    byte[] qualifier = readField(dataInputStream, size);
                    byte[] value = readField(dataInputStream, size);

                    if (size.get() != 0) {
                        throw new IOException("wrong HBaseMutationRecord stream format!");
                    }

                    record.setKey(row);
                    record.setCf(cf);
                    record.setQualifier(qualifier);
                    record.setValue(value);
                    records.add(record);
                }
            } catch (Exception e) {
                LOG.error("failed to deserialize HBaseMutationRecord stream!", e);
                throw e;
            } finally {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    LOG.error("error to close input stream", e);
                }
            }
            return records;
        }

        /**
         * 序列化 #{@link HBaseMutationRecord} 对象列表成数据组
         * @param records
         * @return 序列化之后的数组
         *          如果序列化失败, 则返回null
         */
        public static byte[] serializeRecords(List<HBaseMutationRecord> records) {
            int total = Integer.BYTES;
            List<byte[]> recordBytesList = new ArrayList<>(records.size());
            try {
                for (HBaseMutationRecord record : records) {
                    byte[] recordBytes = record.toBytes();
                    total += recordBytes.length;
                    recordBytesList.add(recordBytes);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeInt(total);
                for (byte[] b : recordBytesList) {
                    dos.write(b);
                }
                dos.close();
                return bos.toByteArray();
            } catch (Exception e) {
                LOG.error("failed to serialize HBaseMutationRecord list!",e);
                return new byte[0];
            }
        }

        private static byte[] readField(DataInputStream dataInputStream, AtomicInteger atomicInteger) throws IOException {
            byte targetReadLength = dataInputStream.readByte();
            byte[] data = new byte[targetReadLength];
            int realReadLength = dataInputStream.read(data);
            if (targetReadLength != realReadLength){
                //throw new MbException(ExcepFactor.E_BYTES_BAD_FORMAT);
            }
            atomicInteger.addAndGet(-(1 + targetReadLength));
            return data;
        }

        private static byte[] readField(DataInputStream dataInputStream) throws IOException {
            byte targetReadLength = dataInputStream.readByte();
            byte[] data = new byte[targetReadLength];
            int realReadLength = dataInputStream.read(data);
            if (realReadLength != targetReadLength){
                //throw new MbException(ExcepFactor.E_BYTES_BAD_FORMAT);
            }
            return data;
        }

        public Mutation getMutation() {
            return mutation;
        }

        public void setMutation(Mutation mutation) {
            this.mutation = mutation;
        }

        public byte[] getKey() {
            return key;
        }

        public void setKey(byte[] key) {
            this.key = key;
        }

        public byte[] getCf() {
            return cf;
        }

        public void setCf(byte[] cf) {
            this.cf = cf;
        }

        public byte[] getQualifier() {
            return qualifier;
        }

        public void setQualifier(byte[] qualifier) {
            this.qualifier = qualifier;
        }

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "HBaseMutationRecord{";
        }
    }

    public enum Mutation
    {
        PUT((byte) 1,"put"),INCREMENT((byte) 2,"increment"),DELETE((byte)3, "delete");

        Mutation(byte type,String name)
        {
            this.type = type;
            this.name = name;
        }

        private byte type;
        private String name;
        public static final int SIZE = 1;

        public byte getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public byte[] toBytes()
        {
            byte[] bytes = new byte[1];
            bytes[0] = type;
            return bytes;
        }

        public static Mutation fromType(byte type){
            try {
                Optional<Mutation> optional =  Arrays.stream(Mutation.values())
                        .filter(mutation -> mutation.type == type)
                        .findFirst();
                if (optional.isPresent()){
                    return optional.get();
                }else {
                    throw new RuntimeException("not supported type[" + type + "] of Mutation");
                }
            }catch (Exception e){
                throw new RuntimeException("not supported type[" + type + "] of Mutation");
            }
        }

    }
}
