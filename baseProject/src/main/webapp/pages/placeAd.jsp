<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<%-- <script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script> --%>

<%-- <script src="/js/pictureUploadAd.js"></script> --%>


<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Place an ad..." />
	</c:import>

	<div class="container">

		<div class="row">

			<div class="span-half">
				<div class="form form-search form-place form-max-height">

					<form:form
						method="post"
						modelAttribute="placeAdForm"
						action="/profile/placeAd"
						id="placeAdForm"
						autocomplete="off"
						enctype="multipart/form-data">

						<div class="container-scroll">

							<div class="row checkboxes">
								<div class="tile tile-third">
									<form:radiobutton id="room" path="roomType" value="Room" checked="checked" />
									<label for="room">Room</label>
								</div>
								<div class="tile tile-third">
									<form:radiobutton id="studio" path="roomType" value="Studio" />
									<label for="studio">Studio</label>
								</div>
								<div class="tile tile-third">
									<form:radiobutton id="house" path="roomType" value="House" />
									<label for="house">House</label>
								</div>
							</div>


							<div class="row checkboxes">
								<div class="tile tile-half">
									<form:radiobutton id="rent" path="buyable" value="0" checked="checked" />
									<label for="rent">Rent</label>
								</div>
								<div class="tile tile-half">
									<form:radiobutton id="buy" path="buyable" value="1" />
									<label for="buy">Sell</label>
								</div>
							</div>


							<form:input
								type="text"
								id="field-street"
								path="street"
								placeholder="Address"
								tabindex="1" />


							<form:input
								type="text"
								id="field-city"
								path="city"
								placeholder="City"
								tabindex="2" />
							<form:errors path="city" cssClass="validationErrorText" />


							<form:input
								type="text"
								id="field-title"
								path="title"
								placeholder="Title"
								tabindex="3" />


								<%-- @Jerome: for some reason it autosets '0' as value. W/A: set
								to '' by js. --%>
							<form:input
								value=""
								id="field-Prize"
								type="number"
								path="prize"
								placeholder="Rent or Price in CHF"
								tabindex="4"
								min="1" />
							<form:errors path="prize" cssClass="validationErrorText" />

							<form:input
								value=""
								id="field-SquareFootage"
								type="number"
								path="squareFootage"
								placeholder="Space in ㎡"
								tabindex="5"
								min="5" />
							<form:errors path="squareFootage" cssClass="validationErrorText" />


							<div class="row dates">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Move-in</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="field-moveInDate"
	                      path="moveInDate"
	                      tabindex="5"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="earliestMoveInDate">

	                </div>
	              </div>
	            </div>

	            <div class="row dates fields-optional js-show">
	              <div class="tile tile-half">
	                <div class="row">
	                  <div class="tile tile-full">
	                    <label>Move-out (optional when selling)</label>
	                  </div>
	                  <div class="tile tile-full">
	                    <form:input
	                      class="js-has-label"
	                      type="text"
	                      id="moveOutDate"
	                      path="moveOutDate"
	                      tabindex="7"
	                      placeholder="Choose from datepicker..." />
	                  </div>
	                </div>
	              </div>
	              <div class="tile tile-half">
	                <div class="datepicker" id="earliestMoveOutDate">

	                </div>
	              </div>
	            </div>


							<div class="row checkboxes">
	              <div class="tile tile-half">
	                <form:checkbox id="field-smoker" path="smokers" value="1" />
	                <label for="field-smoker">Smoking allowed</label>
	              </div>
	              <div class="tile tile-half">
	                <form:checkbox id="field-animals" path="animals" value="1" />
	                <label for="field-animals">Animals allowed</label>
	              </div>
	            </div>

	            <div class="row checkboxes">
	              <div class="tile tile-half">
	                <form:checkbox id="field-balcony" path="balcony" value="1" />
	                <label for="field-balcony">Balcony or Patio</label>
	              </div>
	              <div class="tile tile-half">
	                <form:checkbox id="field-garden" path="garden" value="1" />
	                <label for="field-garden">Garden (co-use)</label>
	              </div>
	            </div>

	            <div class="row checkboxes">
	              <div class="tile tile-half">
	                <form:checkbox id="field-cellar" path="cellar" value="1" />
	                <label for="field-cellar">Cellar or Attic</label>
	              </div>
	              <div class="tile tile-half">
	                <form:checkbox id="field-furnished" path="furnished" value="1" />
	                <label for="field-furnished">Furnished</label>
	              </div>
	            </div>

	            <div class="row checkboxes">
	              <div class="tile tile-half">
	                <form:checkbox id="field-cable" path="cable" value="1" />
	                <label for="field-cable">Cable TV</label>
	              </div>
	              <div class="tile tile-half">
	                <form:checkbox id="field-garage" path="garage" value="1" />
	                <label for="field-garage">Garage</label>
	              </div>
	            </div>

	            <div class="row checkboxes">
	              <div class="tile tile-half">
	                <form:checkbox id="field-internet" path="internet" value="1" />
	                <label for="field-internet">WiFi</label>
	              </div>
	            </div>

							<form:textarea
								path="roomDescription"
								rows="10"
								placeholder="Room Description" />
							<form:errors path="roomDescription" cssClass="validationErrorText" />


							<form:textarea
								path="preferences"
								rows="5"
								placeholder="Preferences" />



							<fieldset>
								<label for="field-pictures">Pictures</label> <input
									type="file" id="field-pictures" accept="image/*" multiple="multiple" />
								<table id="uploaded-pictures" class="styledTable">
									<tr>
										<th id="name-column">Uploaded picture</th>
										<th>Size</th>
										<th>Delete</th>
									</tr>
								</table>
								<br>
							</fieldset>

							<fieldset>
								<legend>Visiting times (optional)</legend>

								<table>
									<tr>
										<td><input type="text" id="field-visitDay" /> <select
											id="startHour">
												<%
													for (int i = 0; i < 24; i++) {
															String hour = String.format("%02d", i);
															out.print("<option value=\"" + hour + "\">" + hour
																	+ "</option>");
														}
												%>
										</select> <select id="startMinutes">
												<%
													for (int i = 0; i < 60; i++) {
															String minute = String.format("%02d", i);
															out.print("<option value=\"" + minute + "\">" + minute
																	+ "</option>");
														}
												%>
										</select> <span>to&thinsp; </span> <select id="endHour">
												<%
													for (int i = 0; i < 24; i++) {
															String hour = String.format("%02d", i);
															out.print("<option value=\"" + hour + "\">" + hour
																	+ "</option>");
														}
												%>
										</select> <select id="endMinutes">
												<%
													for (int i = 0; i < 60; i++) {
															String minute = String.format("%02d", i);
															out.print("<option value=\"" + minute + "\">" + minute
																	+ "</option>");
														}
												%>
										</select>



											<div id="addVisitButton" class="smallPlusButton">+</div>

											<div id="addedVisits"></div></td>

									</tr>

								</table>
								<br>
							</fieldset>

						</div> <%-- .container-scroll END --%>

						<div>
							<button type="submit">Submit</button>
							<a href="/"><button type="button">Cancel</button></a>
						</div>

					</form:form>

				</div> <%-- .form.form-search END --%>
			</div> <%-- .span-half END --%>

			<div class="span-half">
				<div id="image-preview" class="row">

				</div>
			</div>

		</div> <%-- .row END --%>
	</div> <%-- .container END --%>

</main>

<c:import url="template/~bottom.jsp">
	<c:param name="js" value="placeAd" />
</c:import>
