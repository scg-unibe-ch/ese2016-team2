<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />

<pre><a href="/">Home</a>   &gt;   Search</pre>

<script>
function validateType(form)
{
	var room = document.getElementById('room');
	var studio = document.getElementById('studio');
	var house = document.getElementById('house');
	var neither = document.getElementById('neither');

	neither.checked = false;
	
	if(!room.checked && !studio.checked && !house.checked) {
		neither.checked = true;
	}
}
</script>

<script>
	$(document).ready(function() {
		$("#city").autocomplete({
			minLength : 2
		});
		$("#city").autocomplete({
			source : <c:import url="getzipcodes.jsp" />
		});
		$("#city").autocomplete("option", {
			enabled : true,
			autoFocus : true
		});

		var price = document.getElementById('prizeInput');
		var radius = document.getElementById('radiusInput');
		
		/**************************/
		// Noetig?
		/**************************/
		//if(price.value == null || price.value == "" || price.value == "0")
		//	price.value = "500";
		//if(radius.value == null || radius.value == "" || radius.value == "0")
		//	radius.value = "5";
		
		$("#field-earliestMoveInDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-latestMoveInDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-earliestMoveOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-latestMoveOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
</script>

<h1>Search for an ad</h1>
<hr />

<form:form method="post" modelAttribute="searchForm" action="/results"
	id="searchForm" autocomplete="off">
	<fieldset>
		<h2>Mandatory search criteria:</h2>
		
		<form:radiobutton name="buyable" id="buyable" path="buyable" value="0" /><label>Rent</label> 
		<form:radiobutton name="buyable" id="buyable" path="buyable" value="1" /><label>Buy</label> <br />
		
		<form:checkbox name="room" id="room" path="room" /><label>Room</label>
		<form:checkbox name="studio" id="studio" path="studio" /><label>Studio</label>
		<form:checkbox name="house" id="house" path="house" /><label>House</label>
		
		<form:checkbox style="display:none" name="neither" id="neither" path="neither" />
		<form:errors path="neither" cssClass="validationErrorText" /><br />

		<label for="city">City / zip code:</label>
		<form:input type="text" name="city" id="city" path="city" placeholder="e.g. Bern" tabindex="3" />
		<form:errors path="city" cssClass="validationErrorText" /><br/>

		<label for="radius">Within radius of (max.):</label>
		<form:input id="radiusInput" type="number" path="radius" placeholder="e.g. 5" step="5" /> km
		<form:errors path="radius" cssClass="validationErrorText" /><br/> 
		
		<label for="prize">Price (max.):</label>
		<form:input id="prizeInput" type="number" path="prize" placeholder="e.g. 5" step="50" /> CHF
		<form:errors path="prize" cssClass="validationErrorText" /><br/>

		<hr class="slim">
		<h2>Optional search criteria:</h2>
		<table style="width: 80%">
			<tr>
				<td><label for="earliestMoveInDate">Earliest move-in date</label></td>
				<td><label for="earliestMoveOutDate">Earliest move-out date</label></td>
			</tr>
			<tr>
				<td><form:input type="text" id="field-earliestMoveInDate" path="earliestMoveInDate" /></td>
				<td><form:input type="text" id="field-earliestMoveOutDate" path="earliestMoveOutDate" /></td>
			</tr>
			<tr>
				<td><label for="latestMoveInDate">Latest move-in date</label></td>
				<td><label for="latestMoveOutDate">Latest move-out date</label></td>
			</tr>
			<tr>
				<td><form:input type="text" id="field-latestMoveInDate" path="latestMoveInDate" /></td>
				<td><form:input type="text" id="field-latestMoveOutDate" path="latestMoveOutDate" /></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-smoker" path="smokers" value="1" /><label>Smoking inside allowed</label></td>
				<td><form:checkbox id="field-animals" path="animals" value="1" /><label>Animals inside allowed</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-garden" path="garden" value="1" /><label>Garden (co-use)</label></td>
				<td><form:checkbox id="field-balcony" path="balcony" value="1" /><label>Balcony or Patio</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-cellar" path="cellar" value="1" /><label>Cellar or Attic</label></td>
				<td><form:checkbox id="field-furnished" path="furnished" value="1" /><label>Furnished</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-cable" path="cable" value="1" /><label>Cable TV</label></td>
				<td><form:checkbox id="field-garage" path="garage" value="1" /><label>Garage</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-internet" path="internet" value="1" /><label>WiFi</label></td>
			</tr>
		</table>

		<button type="submit" tabindex="7" onClick="validateType(this.form)">Search</button>
		<button form="searchForm" type="reset" tabindex="8">Cancel</button>
	</fieldset>
</form:form>

<c:import url="template/footer.jsp" />
