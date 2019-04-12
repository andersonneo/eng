package com.eng.framework.crypto;

import java.net.URLDecoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.BCodec;
import org.apache.commons.codec.net.QCodec;
import org.apache.commons.codec.net.URLCodec;

public class Jcodec {
	
	public Jcodec(){}
	
	//----base64 ���ڵ�
	public String b64Encoding(String sdata){
		String result = null;
		try{
			result = new String(Base64.encodeBase64(sdata.getBytes()));
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----base64 ���ڵ�
	public String b64Decoding(String edata){
		String result = null;
		try{
			result = new String(Base64.decodeBase64(edata.getBytes()));
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----URL���ڵ�
	public String URLencoding(String sdata){
		String result = null;
		try{
			URLCodec urlCodec = new URLCodec();
			result = urlCodec.encode(sdata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----URL���ڵ�
	public String URLdecoding(String edata){
		String result = null;
		try{
			URLCodec urlCodec = new URLCodec();
			result = urlCodec.decode(edata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----MD5���ڵ�
	public String MD5encoding(String sdata){
		String result = null;
		try{
			result = DigestUtils.md5Hex(sdata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----SHA���ڵ�
	public String SHAencoding(String sdata){
		String result = null;
		try{
			result = DigestUtils.shaHex(sdata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----HEX���ڵ�
	public String HEXencoding(String sdata){
		String result = null;
		try{
			result = new String(Hex.encodeHex(sdata.getBytes()));
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----HEX���ڵ�
	public String HEXdecoding(String edata){
		String result = null;
		try{
			result = new String(Hex.decodeHex(edata.toCharArray()));
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	//----q codec
	public String QCodecEncoding(String sdata){
		String result = null;
		try{
			QCodec qcodec = new QCodec();
		    result = qcodec.encode(sdata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	
	public String QCodecDecoding(String edata){
		String result = null;
		try{
			QCodec qcodec = new QCodec();
		    result = qcodec.decode(edata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	
	//---b codec
	public String BCodecEncoding(String sdata){
		String result = null;
		try{
			BCodec bcodec = new BCodec();
		    result = bcodec.encode(sdata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end
	
	public String BCodecDecoding(String edata){
		String result = null;
		try{
			BCodec bcodec = new BCodec();
		    result = bcodec.decode(edata);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return result;
	}//end

}//end class
