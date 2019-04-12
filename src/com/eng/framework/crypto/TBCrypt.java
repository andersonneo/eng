package com.eng.framework.crypto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * <pre>
 *
 * SEED�� �̿��� ��.��ȣȭ Ŭ�����̴�.
 *
 * TBVirtualAgent���� ����Ÿ�� ��.��Ž� ����Ÿ�� ��.��ȣȭ�� ����Ѵ�.
 *
 *
 * </pre>
 * @author Xagent
 *
 */

public class TBCrypt
{
    /**
     * 16 bytes�迭 �ʱ�ȭ�� '�� ���
     */
    private static final byte[] ZERO_16 = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static final int    BYTE_16 = 16;

    /**
     * ����� ��ȣŰ�� �̿��ؼ� ���� roundKey
     */
    private int[] m_roundKey = new int[32];

    /**
     * ����Ÿ ��.��ȣȭ�� ���Ǵ� 16����Ʈ �ӽ� �迭
     */
    private byte[] m_chunk1 = new byte[BYTE_16];;
    private byte[] m_chunk2 = new byte[BYTE_16];;

    /**
     * �⺻ ����
     */
    public TBCrypt() {
            setUserKey("tobe4ever");
    }

    /**
     * <pre>
     * �Ϻ�ȭ�� ����� ��ȣŰ�� ��d�Ѵ�.
     * </pre>
     * @param key ��ȣŰ
     */
    public void setUserKey(String key) {
            byte[] pbUserKey = key.getBytes();

            // ����� Ű�� �̿��ؼ� roundKey�� ���Ѵ�.
            byte[] tmpUserKey = new byte[16];

            System.arraycopy(ZERO_16,   0, tmpUserKey, 0, BYTE_16);
            System.arraycopy(pbUserKey, 0, tmpUserKey, 0, pbUserKey.length);
            TBSeed.SeedEncRoundKey(m_roundKey, tmpUserKey);
    }

    /**
     * <pre>
     * ���ڿ�; ��ȣȭ �Ѵ�.
     *
     * ���ڿ�; SEED�� ��ȣȭ�ϸ� N*16bit ũ���� ���̳ʸ� �迭�� ���� ���´�.
     * �̸� ASCII����� ���ڿ��� ��ȯ�ϱ� '�ؼ� BASE64�� ���ڵ�; �ؼ� ���ڿ�; ���Ѵ�.
     * </pre>
     * @param strNor �Ϲ� ���ڿ�
     * @return String ��ȣȭ�� ���ڿ�
     */
    public String encrypt(String strNor) {
            if( strNor == null || strNor.length() == 0 )
                    return null;


            /**
             * 16����Ʈ ��'�� ����Ÿ�� ��ȣȭ �ؼ� ���� ���ۿ� �����Ѵ�.
             */
            /*
             * 1) ��ȣȭ�� �����Ѵ�.
             *   - �Ϲݹ��ڿ�; ����Ʈ �迭�� ��ȯ�Ѵ�.
             *   - SEED�� ��ȣȭ ����: 128��Ʈ(16bytes) ��'�� �����Ѵ�.
             *   - �׷��Ƿ�, 128��Ʈ �? ��'�� �߶� ��ȣȭ�� �����Ѵ�.
             */
            byte[] inData  = strNor.getBytes();
            byte[] outData = new byte[(inData.length%BYTE_16==0) ? inData.length : (inData.length/BYTE_16+1)*BYTE_16];
            int left;
            for(int sp = 0; sp < inData.length; sp += BYTE_16 ) {
                    left = inData.length - sp;
                    if( left >= BYTE_16) {
                            System.arraycopy(inData, sp, m_chunk1, 0, BYTE_16);
                    } else {
                            zero16(m_chunk1);
                            System.arraycopy(inData, sp, m_chunk1, 0, left);
                    }
                    TBSeed.SeedEncrypt(m_chunk1, m_roundKey, m_chunk2);
                    System.arraycopy(m_chunk2, 0, outData, sp, BYTE_16);
            }

            /*
             * 2) ��ȣȭ�� ���̳ʸ� �迭; ASCII����� ���ڿ��� ��ȯ�Ѵ�.
             */
            byte[] base64 = encodeBase64(outData);
            return new String(base64).trim();
    }

    /**
     * <pre>
     * ���ڿ�; ��ȣȭ�Ѵ�.
     *
     * </pre>
     * @param strEnc ��ȣȭ�� ���ڿ�
     * @return ��ȣȭ�� ���ڿ�
     */
    public String decrypt(String strEnc) {
            if( strEnc == null || strEnc.length() == 0 )
                    return null;

            /*
             * 1) BASE64Encoder�� ���ڵ�� ���ڿ�; BASE64Decoder�� ���ڵ��Ѵ�.
             */
            byte[] inData = decodeBase64(strEnc.getBytes());

            /*
             * 2) ��ȣȭ�� ���̳ʸ� �迭; ��ȣȭ�Ѵ�.
             *    128��Ʈ �? ��'�� ��ȣȭ�� �����Ѵ�.
             */
            byte[] outData = new byte[(inData.length%BYTE_16==0) ? inData.length : (inData.length/BYTE_16+1)*BYTE_16];
            int left;
            for(int sp = 0; sp < inData.length; sp += BYTE_16 ) {
                    left = inData.length - sp;
                    if( left >= BYTE_16) {
                            System.arraycopy(inData, sp, m_chunk1, 0, BYTE_16);
                    } else {
                            zero16(m_chunk1);
                            System.arraycopy(inData, sp, m_chunk1, 0, left);
                    }
                    TBSeed.SeedDecrypt(m_chunk1, m_roundKey, m_chunk2);
                    System.arraycopy(m_chunk2, 0, outData, sp, BYTE_16);
            }

            /*
             * 3) ��ȣȭ�� ����Ʈ �迭���� ��f ����Ÿ �κи�; �����Ѵ�.
             */
            byte[] zeroOutData = outData;
            for(int i = 0; i < outData.length; i++) {
                    if(outData[i] == 0x00) {
                            zeroOutData = new byte[i];
                            System.arraycopy(outData, 0, zeroOutData, 0, i);
                            break;
                    }
            }

            return new String(zeroOutData);
    }

    /**
     * bytes�� 0x008�� �ʱ�ȭ�Ѵ�.
     *
     * @param data ����Ʈ �迭
     * @return String
     */
    private void zero16(byte[] data) {
            System.arraycopy(ZERO_16, 0, data, 0, BYTE_16);
    }



    /**
    * Base64Encoding ���8�� ����Ʈ �迭; �ƽ�Ű ���ڿ��� ���ڵ��Ѵ�.
    *
    * @param encodeBytes ���ڵ��� ����Ʈ �迭(byte[])
    * @return ���ڵ�� �ƽ�Ű ���ڿ�(String)
    */
    public byte[] encodeBase64(byte[] encodeBytes) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            ByteArrayInputStream bin = new ByteArrayInputStream(encodeBytes);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                    base64Encoder.encodeBuffer(bin, bout);
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return bout.toByteArray();
    }

    /**
    * Base64Decoding ���8�� �ƽ�Ű ���ڿ�; ����Ʈ �迭�� ���ڵ��Ѵ�.
    *
    * @param decodeBytes ���ڵ��� ����Ʈ �迭(byte[])
    * @return ���ڵ�� ����Ʈ �迭(byte[])
    */
    public byte[] decodeBase64(byte[] decodeBytes) {
            byte[] buf = null;
            BASE64Decoder base64Decoder = new BASE64Decoder();
            ByteArrayInputStream bin = new ByteArrayInputStream(decodeBytes);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                    base64Decoder.decodeBuffer(bin, bout);
            } catch (Exception e) {
                    e.printStackTrace();
            }
            buf = bout.toByteArray();
            return buf;
    }
    public static void main(String args[])
    {
            TBCrypt test=new TBCrypt();

            System.out.println(test.encrypt("1111"));

            System.out.println(test.decrypt("AYflZ6K9Ncjnv77JdITRgg=="));
    }
}
