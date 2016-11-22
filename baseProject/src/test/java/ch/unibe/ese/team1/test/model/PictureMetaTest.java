package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.model.PictureMeta;

public class PictureMetaTest {

	PictureMeta pictureMeta = new PictureMeta();
	
	@Test
	public void testPictureMeta() {
		String text = "Test text";
		pictureMeta.setName(text);
		pictureMeta.setSize(text);
		pictureMeta.setType(text);
		pictureMeta.setUrl(text);
		
		assertEquals(text, pictureMeta.getName());
		assertEquals(text, pictureMeta.getSize());
		assertEquals(text, pictureMeta.getType());
		assertEquals(text, pictureMeta.getUrl());
	}
	
}
