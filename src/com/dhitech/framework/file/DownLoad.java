package com.dhitech.framework.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoad {
	private static final int BUFFER_SIZE = 8192; // 8kb
	private static final String CHARSET = "UTF-8";

	public static void download(HttpServletRequest request,HttpServletResponse response, File file) throws Exception {

		String mimetype = request.getSession().getServletContext().getMimeType(
				file.getName());

		if (file == null || !file.exists() || file.length() < 0
				|| file.isDirectory()) {
			throw new IOException(
					"파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리입니다..");
		}

		InputStream is = null;

		try {
			is = new FileInputStream(file);
			download(request, response, is, file.getName(), file.length(),
					mimetype);

			is.close();
		} finally {
			try {
				// is.close();
			} catch (Exception ex) {
			}
		}
	}

	public static void download(HttpServletRequest request,
			HttpServletResponse response, InputStream is, String filename,
			long filesize, String mimetype) throws Exception, IOException {
		String mime = mimetype;

		if (mimetype == null || mimetype.length() == 0) {
			mime = "application/octet-stream;";
		}

		byte[] buffer = new byte[BUFFER_SIZE];

		response.setContentType(mime + "; charset=" + CHARSET);

		// 아래 부분에서 euc-kr 을 utf-8 로 바꾸거나 URLEncoding을 안하거나 등의 테스트를
		// 해서 한글이 정상적으로 다운로드 되는 것으로 지정한다.
		String userAgent = request.getHeader("User-Agent");

		if (userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
			response.setHeader("Content-Disposition", "filename="
					+ new String(filename.getBytes("EUC-KR"), "8859_1") + ";");
		} else if (userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(filename.getBytes("EUC-KR"), "8859_1") + ";");
		} else { // 모질라나 오페라
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(filename.getBytes(CHARSET), "latin1") + ";");
		}

		// 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
		if (filesize > 0) {
			response.setHeader("Content-Length", "" + filesize);
		}

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(is);
			outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;

			while ((read = fin.read(buffer)) != -1) {
				outs.write(buffer, 0, read);
			}
			if (outs != null) {
				outs.flush();
			}
			outs.close();
			fin.close();

		} catch (Exception e) {
			// TODO: handle exception
			outs.close();
			fin.close();

		} finally {
			try {
				// outs.close();
			} catch (Exception ex1) {
			}

			try {
				// fin.close();
			} catch (Exception ex2) {

			}
		} // end of try/catch
	}
}
