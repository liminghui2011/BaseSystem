package com.ocean.lmh.base.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.ocean.lmh.base.util.StringUtils;

public class InfoLogServletResponse extends HttpServletResponseWrapper
		implements ResponseInfo {

	private int						httpStatus	= SC_OK;
	private String					errorMsg;

	public static final int			OT_NONE		= 0, OT_WRITER = 1, OT_STREAM = 2;
	private int						outputType	= OT_NONE;
	private ServletOutputStream		output		= null;
	private PrintWriter				writer		= null;
	private ByteArrayOutputStream	buffer		= null;

	public InfoLogServletResponse(HttpServletResponse response) {
		super(response);
		buffer = new ByteArrayOutputStream();
	}

	@Override
	public void sendError(int sc) throws IOException {
		httpStatus = sc;
		super.sendError(sc);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		super.sendError(sc, msg);
		httpStatus = sc;
		errorMsg = msg;
	}

	@Override
	public void setStatus(int sc) {
		super.setStatus(sc);
		httpStatus = sc;
	}

	public int getStatus() {
		return httpStatus;
	}

	public String getMsg() {
		return errorMsg;
	}

	// ----------------

	public PrintWriter getWriter() throws IOException {
		if (outputType == OT_STREAM)
			throw new IllegalStateException();
		else if (outputType == OT_WRITER)
			return writer;
		else {
			outputType = OT_WRITER;
			writer = new PrintWriter(new OutputStreamWriter(buffer,
					getCharacterEncoding()));
			return writer;
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (outputType == OT_WRITER)
			throw new IllegalStateException();
		else if (outputType == OT_STREAM)
			return output;
		else {
			outputType = OT_STREAM;
			output = new WrappedOutputStream(buffer);
			return output;
		}
	}

	public void flushBuffer() throws IOException {
		if (outputType == OT_WRITER)
			writer.flush();
		if (outputType == OT_STREAM)
			output.flush();
	}

	public void reset() {
		outputType = OT_NONE;
		buffer.reset();
	}

	public byte[] getResponseData() throws IOException {
		flushBuffer();
		return buffer.toByteArray();

	}

	public String getResponseDatas() {
		try {
			flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return StringUtils.toString(buffer.toByteArray());
	}

	class WrappedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream	buffer;

		public WrappedOutputStream(ByteArrayOutputStream buffer) {
			this.buffer = buffer;
		}

		public void write(int b) throws IOException {
			buffer.write(b);
		}

		public byte[] toByteArray() {
			return buffer.toByteArray();
		}
	}

}
