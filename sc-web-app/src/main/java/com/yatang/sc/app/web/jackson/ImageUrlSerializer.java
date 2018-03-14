package com.yatang.sc.app.web.jackson;

import static org.apache.commons.lang3.StringUtils.startsWith;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Created by yipeng on 2017/8/14.
 */
public class ImageUrlSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		if (value instanceof String) {
			serializeContent((String) value, gen, serializers);
		} else if (value instanceof List) {
			serializeContents((List<String>) value, gen, serializers);
		}
	}



	private void serializeContents(List<String> value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		if (CollectionUtils.isEmpty(value)) {
			gen.writeNull();
			return;
		}

		gen.writeStartArray();

		for (String str : value) {
			serializeContent(str, gen, provider);
		}

		gen.writeEndArray();
	}



	private void serializeContent(String text, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (StringUtils.isBlank(text)) {
			gen.writeNull();
			return;
		}

		String imageDomain = String.valueOf(provider.getAttribute("imageViewDomain"));
		gen.writeString(startsWith(text, "http://") || startsWith(text, "https://") ? text
				: (imageDomain + (startsWith(text, "/") ? text : "/" + text)));
	}

}
