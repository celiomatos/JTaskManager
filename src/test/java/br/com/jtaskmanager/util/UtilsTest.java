package br.com.jtaskmanager.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testIsStrDateValidReturnFalse() {
		var strDate = "31/02/2024";
		var result = Utils.isStrDateValid(strDate);
		assertEquals(false, result);
	}

	@Test
	public void testIsStrDateValidReturnTrue() {
		var strDate = "17/11/1978";
		var result = Utils.isStrDateValid(strDate);
		assertEquals(true, result);
	}

	@Test
	public void testConversaoEntreDateToLongAndRetornandoToDate() {
		var dateAntesDaConversao = new Date();
		var dtLong = Utils.dateToLong(dateAntesDaConversao);
		var dateAposDaConversao = Utils.longToDate(dtLong);

		assertEquals(dateAntesDaConversao.getTime(), dateAposDaConversao.getTime());
	}

}
