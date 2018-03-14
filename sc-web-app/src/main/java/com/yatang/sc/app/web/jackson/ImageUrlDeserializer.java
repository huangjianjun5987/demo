package com.yatang.sc.app.web.jackson;

import static org.apache.commons.lang3.StringUtils.startsWith;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.util.ObjectBuffer;

/**
 * Created by yipeng on 2017/8/14.
 */
public class ImageUrlDeserializer extends JsonDeserializer<Object> {

	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		if (!p.isExpectedStartArrayToken()) {
			return deserializeContent(p.getText(), ctxt);
		}

		// 数组处理
		final ObjectBuffer buffer = ctxt.leaseObjectBuffer();
		Object[] chunk = buffer.resetAndStart();

		int ix = 0;

		try {
			while (true) {
				String value = p.nextTextValue();
				if (value == null) {
					JsonToken t = p.getCurrentToken();
					if (t == JsonToken.END_ARRAY) {
						break;
					}
					if (t != JsonToken.VALUE_NULL) {
						value = deserializeContent(p.getText(), ctxt);
					}
				} else {
					value = deserializeContent(p.getText(), ctxt);
				}
				if (ix >= chunk.length) {
					chunk = buffer.appendCompletedChunk(chunk);
					ix = 0;
				}
				chunk[ix++] = value;
			}
		} catch (Exception e) {
			throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
		}
		String[] result = buffer.completeAndClearBuffer(chunk, ix, String.class);
		ctxt.returnObjectBuffer(buffer);
		return Arrays.asList(result);
	}


	private String deserializeContent(String text, DeserializationContext ctxt) throws IOException {
		if (text == null) {
			return text;
		}

		String imageDomain = String.valueOf(ctxt.getAttribute("imageViewDomain"));
		return startsWith(text, "http://") || startsWith(text, "https://") ? text.replace(imageDomain, "") : text;
	}

}
