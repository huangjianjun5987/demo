package com.yatang.sc.operation.web;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yipeng on 2017/7/27.
 */
public abstract class PmPurchaseOrderPdfsGenerater {

	@Getter
	@Setter
	public static class GeneraterPdf {
		private String	fileName;
		private byte[]	bytes;
	}



	public static byte[] generate(List<GeneraterPdf> pdfs) throws IOException {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		generate(ous, pdfs);
		return ous.toByteArray();
	}



	public static void generate(OutputStream ous, List<GeneraterPdf> pdfs) throws IOException {

		@Cleanup
		ZipOutputStream out = new ZipOutputStream(ous);

		for (GeneraterPdf pdf : pdfs) {
			@Cleanup
			InputStream in = new ByteArrayInputStream(pdf.getBytes());

			out.putNextEntry(new ZipEntry(pdf.getFileName()));
			byte[] b = new byte[10000];
			while (true) {
				int r = in.read(b);
				if (r == -1)
					break;
				out.write(b, 0, r);
			}
			out.flush();
			out.closeEntry();
		}
	}

}
